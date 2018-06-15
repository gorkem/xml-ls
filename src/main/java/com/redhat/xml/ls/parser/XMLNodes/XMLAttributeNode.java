package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.services.visitors.VisitableNode;
import com.redhat.xml.ls.services.visitors.XMLNodeVisitor;

/**
 * XMLNode
 */
public class XMLAttributeNode extends XMLNode{

  public XMLAttributeNode() {
    super(XMLNode.ATTRIBUTE_NODE);
  }

@Override
public void accept(XMLNodeVisitor visitor) {
	visitor.visit((XMLAttributeNode)this);
}

}