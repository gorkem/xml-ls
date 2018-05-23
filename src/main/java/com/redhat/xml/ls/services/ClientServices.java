package com.redhat.xml.ls.services;

import java.util.List;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.services.LanguageClient;

public class ClientServices {

  private LanguageClient client;

  public ClientServices(LanguageClient client) {
    this.client = client;
  }

  public void log(MessageType type, String message) {
    if (type == null || message == null)
      return;
    MessageParams $ = new MessageParams();
    $.setType(type);
    $.setMessage(message);
    this.client.logMessage($);
  }

  public void publishDiagnostic(String uri, List<Diagnostic> diagnostics) {
    PublishDiagnosticsParams $ = new PublishDiagnosticsParams();
    $.setUri(uri);
    $.setDiagnostics(diagnostics);
    this.client.publishDiagnostics($);

  }

}
