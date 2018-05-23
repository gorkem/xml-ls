package com.redhat.xml.ls;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import com.redhat.xml.ls.util.LSLogHandler;

public class ServerStarter {
  public static void main(String[] args) {

    XMLLanguageServer server = new XMLLanguageServer();
    Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
    server.connect(launcher.getRemoteProxy());
    LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME)
        .addHandler(new LSLogHandler(server.getClientServices()));
    launcher.startListening();
  }
}
