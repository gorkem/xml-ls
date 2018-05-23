package com.redhat.xml.ls.util;

import org.apache.xerces.xni.parser.XMLParseException;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

/**
 * DiagnosticsHelper
 */
public class DiagnosticsHelper {

  private static Diagnostic toDiagnostic(XMLParseException exception, DiagnosticSeverity severity) {
    Diagnostic $ = new Diagnostic();
    $.setMessage(exception.getMessage());
    $.setSeverity(severity);
    Range range = new Range();
    range.setStart(new Position(exception.getLineNumber() - 1, 0));
    range.setEnd(new Position(exception.getLineNumber() - 1, exception.getColumnNumber()));
    $.setRange(range);
    return $;
  }

  public static Diagnostic warningToDiagnostic(XMLParseException exception) {
    return toDiagnostic(exception, DiagnosticSeverity.Warning);
  }

  public static Diagnostic errorToDiagnostic(XMLParseException exception) {
    return toDiagnostic(exception, DiagnosticSeverity.Error);
  }

  public static Diagnostic fatalErrorToDiagnostic(XMLParseException exception) {
    return toDiagnostic(exception, DiagnosticSeverity.Error);
  }

}
