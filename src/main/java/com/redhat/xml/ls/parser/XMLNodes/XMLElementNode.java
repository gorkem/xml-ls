package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.services.visitors.VisitableNode;
import com.redhat.xml.ls.services.visitors.XMLNodeVisitor;

public class XMLElementNode extends XMLDocumentNode{
  
  public XMLElementNode(short nodeType) {
    super(nodeType);
  }


  @Override
  public void accept(XMLNodeVisitor visitor) {
    visitor.visit((XMLElementNode)this);
  }
}
