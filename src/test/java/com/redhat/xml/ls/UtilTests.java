package com.redhat.xml.ls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.redhat.xml.ls.parser.XMLParser;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.services.serviceutils.Util;
import com.redhat.xml.ls.services.visitors.DocumentHighlightingVisitor;

import org.eclipse.lsp4j.Position;
import org.junit.jupiter.api.Test;

/**
 * UtilTests
 */
public class UtilTests {

  private XMLNode runParser(String uri, String content) {
    XMLParser parser = new XMLParser(null);
    parser.parse(uri, content);
    return parser.getRoot();
  }

  @Test
  public void testWithin() {
  
    XMLNode a = runParser("test", 
    "<project> \n" +
    "    <inside> \n" +
    "      CONTENT\n" +
    "    </inside> \n" +
    "  <project2 att1=\"123\"> \n" +  
    "    Content\n" +
    "  </project2>" +
    "</project> \n" 
    );
    Position aStart = new Position(1, 1);
    Position aEnd = new Position(8, 11);
    a.start = aStart;
    a.end = aEnd;

    Position b = new Position(5, 13);
    assertEquals("att1", Util.FindInnerMostNode(a, b).name);
  }

  @Test
  public void testSearch(){
    XMLNode root = runParser("test", 
    "<project att1=\"hellur\"> \n" +
    "    <inside> \n" +
    "      CONTENT\n" +
    "    </inside> \n" +
    "  <project2 att1=\"123\"> \n" +  
    "    Content\n" +
    "  </project2>" +
    "</project> \n" 
    );
    Position rootStart = new Position(1, 1);
    Position rootEnd = new Position(8, 11);
    root.start = rootStart;
    root.end = rootEnd;

    Position b = new Position(5, 13);
    XMLNode node = Util.FindInnerMostNode(root, b);
    assertEquals("att1", node.name);
    DocumentHighlightingVisitor visitor = new DocumentHighlightingVisitor(node, b);
    List<?> x = Util.createServiceObjectListBFS(root, visitor);
    System.out.println("ASD");
  }

  @Test
  public void testSearchEdge(){
    XMLNode root = runParser("test", 
    "<project att1=\"hellur\"> \n" +
    "    <inside> \n" +
    "      CONTENT\n" +
    "    </inside> \n" +
    "  <project att1=\"123\"> \n" +  
    "    Content\n" +
    "  </project> \n" +
    "</project> \n" 
    );
    assertNotNull(root);
    // Position rootStart = new Position(1, 1);
    // Position rootEnd = new Position(8, 11);
    // root.start = rootStart;
    // root.end = rootEnd;

    Position b = new Position(8, 11);
    XMLNode node = Util.FindInnerMostNode(root, b);
    assertEquals("project", node.name);
    DocumentHighlightingVisitor visitor = new DocumentHighlightingVisitor(node, b);
    List<?> x = Util.createServiceObjectListBFS(root, visitor);
    
  }


}
