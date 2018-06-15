package com.redhat.xml.ls.services.visitors;

import java.util.ArrayList;
import java.util.List;

import com.redhat.xml.ls.parser.XMLNodes.XMLAttributeNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLContentNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLDeclarationNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLDocumentNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLElementNode;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.services.serviceutils.Util;

import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightKind;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

public class DocumentHighlightingVisitor implements XMLNodeVisitor {

	public XMLNode highlightedNode;
	public short type;
	public ArrayList<DocumentHighlight> highlightList;
	public Position position;
	public Position startPosition, endPosition; //
	public boolean cancel = false; // Should the highlight search end

	public DocumentHighlightingVisitor(XMLNode highlightedNode, Position position) {
		this.highlightedNode = highlightedNode;
		this.highlightList = new ArrayList<DocumentHighlight>();
		this.position = position;
		this.type = this.highlightedNode.nodeType;

		// Checks if highlightedNode position was name or value
		if (this.type == XMLNode.ATTRIBUTE_NODE) {
			Position endPos = new Position(highlightedNode.start.getLine(),
					highlightedNode.start.getCharacter() + highlightedNode.name.length());

			// Is it a name
			if (Util.withinStartEnd(highlightedNode.start, endPos, position) == false) {
				this.cancel = true;// end highlight search
			}
		}
	}

	public boolean cancelHighlight() {
		return this.cancel;
	}

	
	@Override
	public void visit(XMLAttributeNode attrNode) {
		if (highlightedNode.nodeType == XMLNode.ATTRIBUTE_NODE && highlightedNode.name == attrNode.name) {

			DocumentHighlight temp = new DocumentHighlight();
			temp.setKind(DocumentHighlightKind.Text);
			Position newEnd = new Position(attrNode.start.getLine(), attrNode.start.getCharacter() + attrNode.name.length());
			Range r = new Range(attrNode.start, newEnd);
			temp.setRange(r);
			highlightList.add(temp);
		}
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
	  if(highlightedNode.nodeType == XMLNode.ELEMENT_NODE && highlightedNode.name == elementNode.name){
			
			//Start tag
			DocumentHighlight temp = new DocumentHighlight();
			temp.setKind(DocumentHighlightKind.Text);
			Position newEnd = new Position(elementNode.start.getLine(), elementNode.start.getCharacter() + 
												elementNode.name.length() + 1);
			Range r = new Range(elementNode.start, newEnd);
			temp.setRange(r);
			highlightList.add(temp);

			//End tag
			temp = new DocumentHighlight();
			temp.setKind(DocumentHighlightKind.Text);
			Position newStart = new Position(elementNode.end.getLine(), elementNode.end.getCharacter() - 
													elementNode.name.length() - 1);
			r = new Range(newStart, elementNode.end);
			temp.setRange(r);
			highlightList.add(temp);
		}
	}

	@Override
	public void visit(XMLNode xmlNode) {
		System.out.println("HELLOOOOO");
	}


	@Override
	public List<?> getList() {
		return this.highlightList;
	}

}