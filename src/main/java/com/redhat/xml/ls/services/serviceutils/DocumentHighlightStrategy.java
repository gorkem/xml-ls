package com.redhat.xml.ls.services.serviceutils;

import java.util.ArrayList;
import java.util.List;

import com.redhat.xml.ls.parser.XMLNodes.XMLNode;

import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightKind;
import org.eclipse.lsp4j.Range;

/**
 * DocumentHighlightStrategy
 */
public class DocumentHighlightStrategy extends ServiceObjectStrategyAbstract {

  List<DocumentHighlight> highlightList;

  public DocumentHighlightStrategy() {
    this.highlightList = new ArrayList<DocumentHighlight>();
  }

  @Override
  public void create(XMLNode node) {
    DocumentHighlight dh = new DocumentHighlight();
    dh.setKind(DocumentHighlightKind.Text);
    Range r = new Range(node.start, node.end);
    dh.setRange(r);
    this.highlightList.add(dh);

  }

  @Override
  public List<?> getNodeList() {
    return this.highlightList;
  }

}