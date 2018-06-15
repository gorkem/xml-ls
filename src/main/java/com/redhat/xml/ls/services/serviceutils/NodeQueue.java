package com.redhat.xml.ls.services.serviceutils;

import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * NodeStack
 */
public class NodeQueue {

  private ArrayList<XMLNode> queue = new ArrayList<XMLNode>();

  public void push(XMLNode node) {
    queue.add(node);
  }

  public void push(XMLNode[] node) {
    queue.addAll(new ArrayList<XMLNode>(Arrays.asList(node)));
  }

  public XMLNode pop() {
    return queue.remove(0);
  }

  public int size() {
    return queue.size();
  }

  public ArrayList<XMLNode> getQueue() {
    return this.queue;
  }

}