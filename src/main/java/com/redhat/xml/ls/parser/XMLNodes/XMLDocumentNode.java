package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.services.visitors.VisitableNode;
import com.redhat.xml.ls.services.visitors.XMLNodeVisitor;

public class XMLDocumentNode extends XMLNode{

  public XMLDocumentNode(short nodeType) {
    super(nodeType);
  }


  @Override
  public void accept(XMLNodeVisitor visitor) {
    visitor.visit((XMLDocumentNode)this);
  }
}