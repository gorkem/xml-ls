package com.redhat.xml.ls.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.redhat.xml.ls.parser.XMLNodes.XMLAttributeNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLDocumentNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLElementNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.parser.xerces.IntegratedParserConfigurationMod;
import com.redhat.xml.ls.services.ClientServices;
import com.redhat.xml.ls.util.DiagnosticsHelper;

import org.apache.xerces.impl.XMLDocumentScannerImpl;
import org.apache.xerces.parsers.IntegratedParserConfiguration;
import org.apache.xerces.parsers.XMLDocumentParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.eclipse.lsp4j.Diagnostic;

/**
 * XMLParser
 */
public class XMLParser extends XMLDocumentParser implements XMLErrorHandler {

  private final static Logger LOG = Logger.getLogger(XMLParser.class.getName());
  private XMLLocator locator;
  private String uri;
  private XMLNode root;
  private XMLNode current;

  private final List<Diagnostic> diagnostics = new ArrayList<>();
  private ClientServices client;

  public XMLParser(ClientServices services) {
    super(new IntegratedParserConfigurationMod());
    this.client = services;
  }

  @Override
  public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs)
      throws XNIException {
    this.locator = locator;
    this.root = new XMLDocumentNode(XMLNode.DOCUMENT_NODE);
    this.root.start = new Position(this.locator.getLineNumber(), this.locator.getColumnNumber());
    pushCurrent(this.root);
  }

  @Override
  public void xmlDecl(String version, String encoding, String standalone, Augmentations augs) throws XNIException {

    XMLNode decl = new XMLNode(XMLNode.XML_DECL);
    decl.start = new Position(this.locator.getLineNumber(), this.locator.getColumnNumber());
    addToCurrent(decl);
    // Do not push to current as xmldecl can not have child
    // TODO: we need to determine the end position
  }

  @Override
  public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {

    XMLNode e = new XMLElementNode(XMLNode.ELEMENT_NODE);
    e.start = new Position(this.locator.getLineNumber(), this.locator.getColumnNumber());
    e.name = element.localpart;

    addToCurrent(e);
    pushCurrent(e);

    XMLNode[] attrList = new XMLNode[attributes.getLength()];

    // char[] xx = this.fDocumentSource.fTempString.ch;

    // System.out.println(xx[2]);
    for (int attrIndex = 0; attrIndex < attributes.getLength(); attrIndex++) {
      String attrName = attributes.getQName(attrIndex);
      String attrValue = attributes.getValue(attrIndex);

      XMLAttributeNode attrNode = new XMLAttributeNode(XMLNode.ATTRIBUTE_NODE, attrValue);
      attrNode.name = attrName;
      attrNode.start = new Position(this.locator.getLineNumber(), this.locator.getColumnNumber());
      System.out.println("THIS IS THE THING: " + this.locator.getCharacterOffset());
      addToCurrent(attrNode);
      // TODO: we need to determine the end position

      attrList[attrIndex] = attrNode;
    }

    this.current.children = attrList;
    System.out.println("hi");
  }

  @Override
  public void comment(XMLString text, Augmentations augs) throws XNIException {
    super.comment(text, augs);
  }

  @Override
  public void characters(XMLString text, Augmentations augs) throws XNIException {
    XMLString t = text;
  }
  /*
   * To be implemented after DTD's are considered
   */
  // @Override
  // public void attributeDecl(String elementName, String attributeName, String
  // type, String[] enumeration,
  // String defaultType, XMLString defaultValue, XMLString
  // nonNormalizedDefaultValue, Augmentations augs)
  // throws XNIException {

  // XMLAttributeNode att = new XMLAttributeNode(XMLNode.ATTRIBUTE_NODE, new
  // String(defaultValue.toString()));
  // att.parent = this.current;
  // att.name = new String(attributeName);
  // att.start = new Position(this.locator.getLineNumber(),
  // this.locator.getColumnNumber());
  // addToCurrent(att);
  // // Do not push to current as xmldecl can not have child
  // // TODO: we need to determine the end position
  // }

  @Override
  public void endElement(QName element, Augmentations augs) throws XNIException {
    this.current.end = new Position(this.locator.getLineNumber(), this.locator.getColumnNumber());
    popCurrent();
  }

  @Override
  public void endDocument(Augmentations augs) throws XNIException {
    this.current.end = new Position(this.locator.getLineNumber(), this.locator.getColumnNumber());
    popCurrent();
    // assert this.current == null
  }

  private void publishDiagnostics() {
    if (this.client != null) {
      client.publishDiagnostic(this.uri, this.diagnostics);
    }
  }

  private void pushCurrent(XMLNode e) {
    this.current = e;
  }

  private void popCurrent() {
    if (this.current != null) {
      this.current = this.current.parent;
    }
  }

  // Adds e as child to current Node
  private void addToCurrent(XMLNode e) {
    if (this.current.children == null) {
      this.current.children = new XMLNode[1];
    } else {
      XMLNode[] newChilds = Arrays.copyOf(this.current.children, this.current.children.length + 1);
      this.current.children = newChilds;
    }
    this.current.children[this.current.children.length - 1] = e;
    e.parent = this.current;
  }

  @Override
  public void warning(String domain, String key, XMLParseException exception) throws XNIException {
    diagnostics.add(DiagnosticsHelper.warningToDiagnostic(exception));
  }

  @Override
  public void error(String domain, String key, XMLParseException exception) throws XNIException {
    diagnostics.add(DiagnosticsHelper.errorToDiagnostic(exception));
  }

  @Override
  public void fatalError(String domain, String key, XMLParseException exception) throws XNIException {
    diagnostics.add(DiagnosticsHelper.fatalErrorToDiagnostic(exception));
  }

  public void parse(String uri, String content) {
    this.uri = uri;
    XMLInputSource source = new XMLInputSource(null, null, null);
    source.setCharacterStream(new StringReader(content));
    try {
      // String x[] = new
      // String[]{"http://apache.org/xml/properties/dom/current-element-node"};
      // super.fConfiguration.addRecognizedProperties(x);
      super.parse(source);
    } catch (XMLParseException e) {
      diagnostics.add(DiagnosticsHelper.fatalErrorToDiagnostic(e));
    } catch (IOException | XNIException e) {
      LOG.warning("Parsing " + this.uri + " failed : " + e.toString());
    } finally {
      // Publish diagnostics here so that even the parser fails to
      // parse the whole document we have something
      publishDiagnostics();
    }
  }

  public XMLNode getRoot() {
    return this.root;
  }

}
