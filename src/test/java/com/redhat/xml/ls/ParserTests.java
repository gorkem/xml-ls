package com.redhat.xml.ls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.redhat.xml.ls.parser.XMLNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLDocumentNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLElementNode;
import com.redhat.xml.ls.parser.XMLParser;

import org.junit.jupiter.api.Test;

/**
 */
public class ParserTests {

  private XMLNode runParser(String uri, String content) {
    XMLParser parser = new XMLParser(null);
    parser.parse(uri, content);
    return parser.getRoot();
  }

  @Test
  public void testDocument() {
    XMLDocumentNode node = (XMLDocumentNode) runParser("test", "<project><atag /></project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertNotNull(node.start);
    assertNotNull(node.end);
    assertEquals(1, node.start.line);
    assertEquals(1, node.end.line);
    assertEquals(1, node.start.column);
    assertEquals(28, node.end.column);
  }

  @Test
  public void testAttributeRecognized() {
    XMLNode node = runParser("test", "<project attribute=\"hello world\"></project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals(1, node.children[0].children.length);
    assertNotNull(node.start);
    assertNotNull(node.end);
    assertEquals(1, node.start.line);
    assertEquals(1, node.end.line);
    assertEquals(1, node.start.column);
    assertEquals(44, node.end.column);
  }

  @Test
  public void testMultipleAttributesRecognized() {
    XMLNode node = runParser("test", "<project a1=\"world\" a2=\"world\" a3=\"!\"></project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals(3, node.children[0].children.length);
    assertNotNull(node.start);
    assertNotNull(node.end);
    assertEquals(1, node.start.line);
    assertEquals(1, node.end.line);
    assertEquals(1, node.start.column);
    
  }

  @Test
  public void testAttributeValues() {
    XMLNode node = runParser("test", "<project a1=\"world\"></project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals(1, node.children[0].children.length);
    assertEquals("a1", node.children[0].children[0].name);
    assertEquals("project", node.children[0].children[0].parent.name);
    assertNotNull(node.start);
    assertNotNull(node.end);
    assertNotNull(node.children[0].children[0].parent);
    assertNull(node.children[0].children[0].children);
  }

  @Test
  public void testElement() {
    /* @formatter:off*/
    XMLNode node = runParser("test", "<project> \n" +
     "  someText \n" +
     "</project> \n");
    /* @formatter:on */
    assertNotNull(node);
    node = node.children[0];
    assertNotNull(node);
    assertEquals(XMLNode.ELEMENT_NODE, node.nodeType);
    assertEquals("project", node.name);
    assertEquals(1, node.start.line);
    assertEquals(3, node.end.line);
    assertEquals(11, node.end.column);
  }

  @Test
  public void testXmlDecl() {
    /* @formatter:off */
    XMLNode node = runParser("test", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    "<project> \n" +
     "  someText \n" +
     "</project> \n");
    /* @formatter:on */
    assertNotNull(node);
    node = node.children[0];
    assertNotNull(node);
    assertEquals(XMLNode.XML_DECL, node.nodeType);

  }

}
