package com.redhat.xml.ls.parser.xerces;

import java.util.Enumeration;

import com.redhat.xml.ls.parser.XMLNodes.XMLContentNode;

import org.apache.xerces.xni.Augmentations;

/**
 * ContentAug
 */
public class ContentAug implements Augmentations {

  public XMLContentNode node;

  public ContentAug() {
    node = new XMLContentNode();
  }

  @Override
  public Object putItem(String key, Object item) {
    return null;
  }

  @Override
  public Object getItem(String key) {
    return null;
  }

  @Override
  public Object removeItem(String key) {
    return null;
  }

  @Override
  public Enumeration keys() {
    return null;
  }

  @Override
  public void removeAllItems() {

  }

}