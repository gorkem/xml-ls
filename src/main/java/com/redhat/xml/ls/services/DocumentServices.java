package com.redhat.xml.ls.services;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.swing.ProgressMonitorInputStream;

import com.redhat.xml.ls.XMLLanguageServer;
import com.redhat.xml.ls.parser.XMLParser;
import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.services.serviceutils.Util;
import com.redhat.xml.ls.services.visitors.DocumentHighlightingVisitor;

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
import org.eclipse.lsp4j.jsonrpc.CancelChecker;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

public class DocumentServices<IProgressMonitor> implements TextDocumentService {

  private XMLLanguageServer server;
  private HashMap<String, XMLNode> cache = new HashMap<>();

  private final static Logger LOG = Logger.getLogger(DocumentServices.class.getName());

  public DocumentServices(XMLLanguageServer xmlLanguageServer) {
    this.server = xmlLanguageServer;
  }

  public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
      TextDocumentPositionParams position) {
    return null;
  }

  public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
    return null;
  }

  // Investigate whether necessary
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

  // Shows all other same named of highlighted
  public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {

    XMLNode root = cache.get(position.getTextDocument().getUri());
    XMLNode highlightedNode = Util.FindInnerMostNode(root, position.getPosition());

    if(highlightedNode == null){
      return new CompletableFuture<>();
    }
    
    DocumentHighlightingVisitor visitor = new DocumentHighlightingVisitor(highlightedNode, position.getPosition());

    if (visitor.cancelHighlight()) {
      return new CompletableFuture<>();
    }
    //((monitor) -> (List<DocumentHighlight>) Util.createServiceObjectListBFS(root, visitor));
    List<DocumentHighlight> x = (List<DocumentHighlight>) Util.createServiceObjectListBFS(root, visitor);
    return CompletableFutures.computeAsync(cc -> 
    {
      return x;//(List<DocumentHighlight>) Util.createServiceObjectListBFS(root, visitor);
    });
    

  }

  // private <R> CompletableFuture<R> computeAsync(Function<IProgressMonitor, R> code) {
	// 	return CompletableFutures.computeAsync(cc -> code.apply(toMonitor(cc)));
	// }

	// private IProgressMonitor toMonitor(CancelChecker checker) {
	// 	return new CancellableProgressMonitor(checker);
	// }

  public CompletableFuture<List<? extends SymbolInformation>> documentSymbol(DocumentSymbolParams params) {
    return null;
  }

  public CompletableFuture<List<? extends Command>> codeAction(CodeActionParams params) {

    return null;
  }

  // See where closing brackets are
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
    XMLNode s = parseDocument(params.getTextDocument().getUri(), params.getTextDocument().getText());
    cache.put(params.getTextDocument().getUri(), s);
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

  private XMLNode parseDocument(String uri, String content) {
    CompletableFuture<XMLNode> z = CompletableFuture.supplyAsync(() -> {
      XMLParser parser = new XMLParser(this.server.getClientServices());
      parser.parse(uri, content);
      return parser.getRoot();
    });

    try {
      return z.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    return null;
  }

}
