package com.redhat.xml.ls.services.serviceutils;

import java.util.concurrent.CompletableFuture;
import java.util.List;

import org.eclipse.lsp4j.Position;

import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightKind;

public class DocumentHighlightUtil {

    public static void main(String[] args) {
        DocumentHighlight d = new DocumentHighlight();
        d.setKind(DocumentHighlightKind.Text);

    }

    public CompletableFuture<List<? extends DocumentHighlight>> start(Position p) {
        return null;
    }
}