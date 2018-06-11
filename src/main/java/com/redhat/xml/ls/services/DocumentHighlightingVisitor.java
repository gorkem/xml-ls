package com.redhat.xml.ls.services;

import com.redhat.xml.ls.parser.XMLNodes.XMLAttributeNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLContentNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLDeclarationNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLDocumentNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLElementNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLNodeVisitor;

public class DocumentHighlightingVisitor implements XMLNodeVisitor {

	@Override
	public void visit(XMLNode xmlNode) {
		System.out.println("Visiting xmlNode");
	}

	@Override
	public void visit(XMLAttributeNode attrNode) {
		System.out.println("Visiting attrNode");
	}

	@Override
	public void visit(XMLContentNode contentNode) {
		System.out.println("Visiting contentNode");
	}

	@Override
	public void visit(XMLDeclarationNode declNode) {
		System.out.println("Visiting declNode");
	}

	@Override
	public void visit(XMLDocumentNode documentNode) {
		System.out.println("Visiting documentNode");
	}

	@Override
	public void visit(XMLElementNode elementNode) {
		System.out.println("Visiting elementNode");
	}

}