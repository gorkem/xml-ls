package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.services.visitors.XMLNodeVisitor;

import org.eclipse.lsp4j.Position;

/**
 * XMLNode
 */
public class XMLNode {
  // NodeType
  /**
   * The node is an <code>Element</code>.
   */
  public static final short ELEMENT_NODE = 1;
  /**
   * The node is an <code>Attr</code>.
   */
  public static final short ATTRIBUTE_NODE = 2;
  /**
   * The node is a <code>Text</code> node.
   */
  public static final short TEXT_NODE = 3;
  /**
   * The node is a <code>CDATASection</code>.
   */
  public static final short CDATA_SECTION_NODE = 4;
  /**
   * The node is an <code>EntityReference</code>.
   */
  public static final short ENTITY_REFERENCE_NODE = 5;
  /**
   * The node is an <code>Entity</code>.
   */
  public static final short ENTITY_NODE = 6;
  /**
   * The node is a <code>ProcessingInstruction</code>.
   */
  public static final short PROCESSING_INSTRUCTION_NODE = 7;
  /**
   * The node is a <code>Comment</code>.
   */
  public static final short COMMENT_NODE = 8;
  /**
   * The node is a <code>Document</code>.
   */
  public static final short DOCUMENT_NODE = 9;
  /**
   * The node is a <code>DocumentType</code>.
   */
  public static final short DOCUMENT_TYPE_NODE = 10;
  /**
   * The node is a <code>DocumentFragment</code>.
   */
  public static final short DOCUMENT_FRAGMENT_NODE = 11;
  /**
   * The node is a <code>Notation</code>.
   */
  public static final short NOTATION_NODE = 12;

  public static final short XML_DECL = 13;

  public final short nodeType;
  public Position start;
  public Position end;
  public XMLNode parent;
  public String name;
  public XMLNode[] children;
  public String value;

  public XMLNode(short nodeType) {
    this.nodeType = nodeType;
  }

  public void accept(XMLNodeVisitor visitor){
    visitor.visit(this);
  }

  // Doesnt work for XMLDocumentNode and First Child if child == (1,1)
  public boolean equals(XMLNode node) {
    return start.equals(node.start);
  }

}
