package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.services.visitors.VisitableNode;
import com.redhat.xml.ls.services.visitors.XMLNodeVisitor;

/**
 * XMLContentNode
 */
public class XMLContentNode extends XMLNode{

  public XMLContentNode() {
    super(TEXT_NODE);
  }

  @Override
  public void accept(XMLNodeVisitor visitor) {
    visitor.visit((XMLContentNode) this);
  }

}