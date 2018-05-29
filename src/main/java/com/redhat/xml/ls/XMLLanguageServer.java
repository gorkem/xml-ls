package com.redhat.xml.ls;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import com.redhat.xml.ls.services.ClientServices;
import com.redhat.xml.ls.services.DocumentServices;
import com.redhat.xml.ls.services.WorkspaceServices;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.SaveOptions;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextDocumentSyncOptions;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

//DELETE THIS COMMENT

public class XMLLanguageServer implements LanguageServer, LanguageClientAware {

  private TextDocumentService documentService;
  private WorkspaceService workspaceService;
  private ClientServices clientServices;
  private static final Logger LOG = Logger.getLogger(XMLLanguageServer.class.getName());

  public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
    LOG.info("Initializing server");
    final ServerCapabilities $ = new ServerCapabilities();
    TextDocumentSyncOptions syncOptions = new TextDocumentSyncOptions();
    syncOptions.setChange(TextDocumentSyncKind.None);
    syncOptions.setSave(new SaveOptions(true));
    syncOptions.setOpenClose(true);
    $.setTextDocumentSync(syncOptions);
    $.setDocumentHighlightProvider(true);

    return CompletableFuture.completedFuture(new InitializeResult($));
  }

  public CompletableFuture<Object> shutdown() {
    LOG.info("Shutting down");
    return CompletableFuture.completedFuture(null);
  }

  public void exit() {
    LOG.info("Exiting");
    System.exit(0);
  }

  public TextDocumentService getTextDocumentService() {
    if (this.documentService == null) {
      this.documentService = new DocumentServices(this);
    }
    return documentService;
  }

  public WorkspaceService getWorkspaceService() {
    if (this.workspaceService == null) {
      this.workspaceService = new WorkspaceServices(this);
    }
    return workspaceService;
  }

  public void connect(LanguageClient client) {
    this.clientServices = new ClientServices(client);
  }

  /**
   * @return the clientServices
   */
  public ClientServices getClientServices() {
    return clientServices;
  }

}
