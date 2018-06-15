package com.redhat.xml.ls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.redhat.xml.ls.parser.XMLParser;
import com.redhat.xml.ls.parser.XMLNodes.XMLAttributeNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLDocumentNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;

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
    XMLDocumentNode node = (XMLDocumentNode) runParser("test", "<project><atag/></project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertNotNull(node.start);
    assertNotNull(node.end);
    assertEquals(1, node.start.getLine());
    assertEquals(1, node.end.getLine());
    assertEquals(1, node.start.getCharacter());
    assertEquals(27, node.end.getCharacter());
    assertEquals(1, node.children[0].start.getCharacter());
  }

  @Test
  public void testSingleElementTag() {
    XMLDocumentNode node = (XMLDocumentNode) runParser("test", "<project />");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals("project", node.children[0].name);
    assertNotNull(node.start);
    assertNotNull(node.end);
    assertEquals(1, node.start.getLine());
    assertEquals(1, node.end.getLine());
    assertEquals(1, node.start.getCharacter());
    assertEquals(12, node.end.getCharacter());
  }

  @Test
  public void testNestedElementTags() {
    XMLDocumentNode node = (XMLDocumentNode) runParser("test", "<project1><project2></project2></project1>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals("project1", node.children[0].name);
    assertEquals("project2", node.children[0].children[0].name);
    
  }

  @Test
  public void testElementTagPositions() {
    XMLDocumentNode node = (XMLDocumentNode) runParser("test", "<project1><project2></project2></project1>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    
    assertEquals(1, node.children[0].start.getCharacter());
    assertEquals(1, node.children[0].start.getLine());
    assertEquals(43, node.children[0].end.getCharacter());

    assertEquals(11, node.children[0].children[0].start.getCharacter());
    assertEquals(1, node.children[0].children[0].start.getLine());
    assertEquals(32, node.children[0].children[0].end.getCharacter());
    
  }

  @Test
  public void testAttributeRecognized() {
    String content = "<project attribute=\"hello world\"></project>";
    XMLNode node = runParser("test", content);

    
    
    assertEquals(1, node.children.length);
    assertEquals(1, node.children[0].children.length);
    assertEquals(1, node.start.getLine());
    assertEquals(1, node.end.getLine());
    assertEquals(1, node.children[0].start.getCharacter());
    assertEquals(44, node.children[0].end.getCharacter());
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
    assertEquals(1, node.start.getLine());
    assertEquals(1, node.end.getLine());
    assertEquals(1, node.start.getCharacter());

  }

  @Test
  public void testAttributeProperties() {
    XMLNode node = runParser("test", "<project a1=\"world\"></project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals(1, node.children[0].children.length);
    assertEquals("a1", node.children[0].children[0].name);
    assertEquals("project", node.children[0].children[0].parent.name);
    assertEquals("world", node.children[0].children[0].value);
    assertEquals(10, ((XMLAttributeNode) node.children[0].children[0]).start.getCharacter());
    assertEquals(20, ((XMLAttributeNode) node.children[0].children[0]).end.getCharacter());
    assertNotNull(node.start);
    assertNotNull(node.end);
    assertNotNull(node.children[0].children[0].parent);
    assertNull(node.children[0].children[0].children);
  }

  @Test
  public void testAttributePositionSpaces() {
    XMLNode node = runParser("test", "<project a1 = \"world\"></project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals(1, node.children[0].children.length);
    assertEquals("a1", node.children[0].children[0].name);
    assertEquals("project", node.children[0].children[0].parent.name);
    assertEquals("world", node.children[0].children[0].value);
    assertEquals(10, ((XMLAttributeNode) node.children[0].children[0]).start.getCharacter());
    assertEquals(22, ((XMLAttributeNode) node.children[0].children[0]).end.getCharacter());
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
    assertEquals(1, node.start.getLine());
    assertEquals(3, node.end.getLine());
    assertEquals(11, node.end.getCharacter());
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

  @Test
  public void testElementText() {
   /* @formatter:off */
   XMLNode node = runParser("test", "<project att=\"k\">Hey Dude</project>");
   /* @formatter:on */

  }

  /**
  *
  */
  @Test
  public void testMultipleAttributes() {
    XMLNode node = runParser("test", "<project att=\"k\" att2=\"yo\" att3=\"hi\">Hey Dude</project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    
    //Should be 4 after contentNodes are implemented
    assertEquals(3, node.children[0].children.length);

    assertEquals("att", node.children[0].children[0].name);
    assertEquals("k", node.children[0].children[0].value);

    assertEquals("att2", node.children[0].children[1].name);
    assertEquals("yo", node.children[0].children[1].value);

    assertEquals("att3", node.children[0].children[2].name);
    assertEquals("hi", node.children[0].children[2].value);

  }

  /**
  *
  */
  @Test
  public void testMultipleAttributesPositions() {
    XMLNode node = runParser("test", "<project att=\"k\" att2=\"yo\" att3=\"hi\">Hey Dude</project>");
    assertNotNull(node);
    assertEquals(XMLNode.DOCUMENT_NODE, node.nodeType);
    assertNotNull(node.children);
    assertEquals(1, node.children.length);
    assertEquals(3, node.children[0].children.length);

    assertEquals(10, node.children[0].children[0].start.getCharacter());
    assertEquals(17, node.children[0].children[0].end.getCharacter());

    assertEquals(18, node.children[0].children[1].start.getCharacter());
    assertEquals(27, node.children[0].children[1].end.getCharacter());

    assertEquals(28, node.children[0].children[2].start.getCharacter());
    assertEquals(37, node.children[0].children[2].end.getCharacter());

  }
}
