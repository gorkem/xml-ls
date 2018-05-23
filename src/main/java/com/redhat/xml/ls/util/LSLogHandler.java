package com.redhat.xml.ls.util;

import com.redhat.xml.ls.services.ClientServices;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import org.eclipse.lsp4j.MessageType;

public class LSLogHandler extends Handler {
  private final ClientServices client;

  public LSLogHandler(ClientServices clientServices) {
    setFormatter(new SimpleFormatter());
    this.client = clientServices;
  }

  @Override
  public void publish(LogRecord record) {
    if (record == null || record.getLevel() == Level.OFF)
      return;
    client.log(toMessageType(record), getFormatter().formatMessage(record));
  }

  private MessageType toMessageType(LogRecord record) {
    if (record.getLevel() == Level.SEVERE) {
      return MessageType.Error;
    }
    if (record.getLevel() == Level.WARNING) {
      return MessageType.Warning;
    }
    if (record.getLevel() == Level.INFO) {
      return MessageType.Info;
    }
    return MessageType.Log;
  }

  @Override
  public void flush() {

  }

  @Override
  public void close() throws SecurityException {

  }

}
