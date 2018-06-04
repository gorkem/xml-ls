package com.redhat.xml.ls.parser.XMLNodes;

import com.redhat.xml.ls.parser.XMLNodes.XMLNode;

/**
 * XMLNode
 */
public class XMLAttributeNode extends XMLNode {

  

  public XMLAttributeNode(short nodeType, String value) {
    super(nodeType);
    this.value = value;
    
  }
}