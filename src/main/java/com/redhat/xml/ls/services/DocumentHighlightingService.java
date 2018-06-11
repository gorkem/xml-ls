package com.redhat.xml.ls.services;

import com.redhat.xml.ls.parser.XMLNodes.Visitable;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLNodeVisitor;

public class DocumentHighlightingService implements Visitable {

    private XMLNode root;

    public DocumentHighlightingService(XMLNode root) {
        this.root = root;
    }
    
	@Override
	public void accept(XMLNodeVisitor node) {
        for(XMLNode child : root.children) {
            child.accept(node);    
        }
		node.visit(root);
	}

}