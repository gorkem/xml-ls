package com.redhat.xml.ls.services.visitors;

/**
 * VisitableNode
 */
public interface VisitableNode {

  public void accept(XMLNodeVisitor visitor);
  
}