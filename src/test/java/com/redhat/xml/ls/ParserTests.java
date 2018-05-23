package com.redhat.xml.ls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.redhat.xml.ls.parser.XMLNode;
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
    XMLNode node = runParser("test", "<project><atag /></project>");
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
