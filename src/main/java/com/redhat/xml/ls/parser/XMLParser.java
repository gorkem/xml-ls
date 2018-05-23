package com.redhat.xml.ls.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.redhat.xml.ls.services.ClientServices;
import com.redhat.xml.ls.util.DiagnosticsHelper;

import org.apache.xerces.parsers.XMLDocumentParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
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
    this.client = services;
  }

  @Override
  public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs)
      throws XNIException {
    this.locator = locator;
    this.root = new XMLNode(XMLNode.DOCUMENT_NODE);
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
    XMLNode e = new XMLNode(XMLNode.ELEMENT_NODE);
    e.start = new Position(this.locator.getLineNumber(), this.locator.getColumnNumber());
    e.name = element.localpart;
    addToCurrent(e);
    pushCurrent(e);
  }

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
