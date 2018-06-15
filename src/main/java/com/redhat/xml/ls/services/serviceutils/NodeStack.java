package com.redhat.xml.ls.services.serviceutils;

import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * NodeStack
 */
public class NodeStack {

  private ArrayList<XMLNode> stack = new ArrayList<XMLNode>();

  public void push(XMLNode node) {
    stack.add(node);
  }

  public void push(XMLNode[] node) {
    stack.addAll(new ArrayList<XMLNode>(Arrays.asList(node)));
  }

  public XMLNode pop() {
    return stack.remove(stack.size() - 1);
  }

  public int size() {
    return stack.size();
  }

}