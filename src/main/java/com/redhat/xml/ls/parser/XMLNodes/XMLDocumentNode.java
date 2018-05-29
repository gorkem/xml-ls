package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.parser.XMLNode;


public class XMLDocumentNode extends XMLNode {

  public XMLNode[] elementChildren;
  
  public XMLDocumentNode(short nodeType) {
    super(nodeType);
    
  }
}