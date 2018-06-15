package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.services.visitors.VisitableNode;
import com.redhat.xml.ls.services.visitors.XMLNodeVisitor;

public class XMLDeclarationNode extends XMLDocumentNode{

  public XMLDeclarationNode(short nodeType) {
    super(nodeType);
  }

  @Override
  public void accept(XMLNodeVisitor visitor) {
    visitor.visit((XMLDeclarationNode)this);
  }
}
