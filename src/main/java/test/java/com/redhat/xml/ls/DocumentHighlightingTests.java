package test.java.com.redhat.xml.ls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.services.DocumentHighlightingService;
import com.redhat.xml.ls.services.DocumentHighlightingVisitor;
import com.redhat.xml.ls.parser.XMLNodes.Visitable;
import com.redhat.xml.ls.parser.XMLNodes.XMLDocumentNode;
import com.redhat.xml.ls.parser.XMLParser;

import org.junit.jupiter.api.Test;

/**
 */
public class DocumentHighlightingTests {

  private XMLNode runParser(String uri, String content) {
    XMLParser parser = new XMLParser(null);
    parser.parse(uri, content);
    return parser.getRoot();
  }

  @Test
  public void testDocument() {
    XMLDocumentNode node = (XMLDocumentNode) runParser("test", "<project><atag/></project>");
    Visitable c = new DocumentHighlightingService(node);
    c.accept(new DocumentHighlightingVisitor());
    System.out.println("?");
  }

}
