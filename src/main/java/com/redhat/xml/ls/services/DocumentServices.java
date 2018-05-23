package com.redhat.xml.ls.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import com.redhat.xml.ls.XMLLanguageServer;
import com.redhat.xml.ls.parser.XMLNode;
import com.redhat.xml.ls.parser.XMLParser;

import org.apache.xerces.xni.XNIException;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class DocumentServices implements TextDocumentService {

  private XMLLanguageServer server;
  private HashMap<String, Document> cache = new HashMap<>();

  private final static Logger LOG = Logger.getLogger(DocumentServices.class.getName());

  public DocumentServices(XMLLanguageServer xmlLanguageServer) {
    this.server = xmlLanguageServer;
  }

  public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
      TextDocumentPositionParams position) {
    Document document = cache.get(position.getTextDocument().getUri());
    return null;
  }

  public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
    return null;
  }

  public CompletableFuture<Hover> hover(TextDocumentPositionParams position) {
    return null;
  }

  public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams position) {
    return null;
  }

  public CompletableFuture<List<? extends Location>> definition(TextDocumentPositionParams position) {
    return null;
  }

  public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
    return null;
  }

  public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {
    return null;
  }

  public CompletableFuture<List<? extends SymbolInformation>> documentSymbol(DocumentSymbolParams params) {
    return null;
  }

  public CompletableFuture<List<? extends Command>> codeAction(CodeActionParams params) {
    return null;
  }

  public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
    return null;
  }

  public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
    return null;
  }

  public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
    return null;
  }

  public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
    return null;
  }

  public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
    return null;
  }

  public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
    return null;
  }

  public void didOpen(DidOpenTextDocumentParams params) {
    LOG.info("Document opened");
    parseDocument(params.getTextDocument().getUri(), params.getTextDocument().getText());
  }

  public void didChange(DidChangeTextDocumentParams params) {

  }

  public void didClose(DidCloseTextDocumentParams params) {
    LOG.info("Document closed");
    cache.remove(params.getTextDocument().getUri());
  }

  public void didSave(DidSaveTextDocumentParams params) {
    LOG.info("Document saved");
    parseDocument(params.getTextDocument().getUri(), params.getText());
  }

  private void parseDocument(String uri, String content) {
    CompletableFuture.runAsync(() -> {
      XMLParser parser = new XMLParser(this.server.getClientServices());
      parser.parse(uri, content);
    });
  }

}
