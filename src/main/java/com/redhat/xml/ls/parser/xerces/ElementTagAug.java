package com.redhat.xml.ls.parser.xerces;


import java.util.Enumeration;

import org.apache.xerces.xni.Augmentations;
import org.eclipse.lsp4j.Position;

/**
 * ElementTagAug
 */
public class ElementTagAug implements Augmentations{

  public Position startPos;

  public ElementTagAug(Position p){
    this.startPos = p;
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