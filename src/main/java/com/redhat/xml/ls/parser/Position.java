package com.redhat.xml.ls.parser;

/**
 */
public final class Position {
  public final int column;
  public final int line;

  public Position(int line, int col) {
    this.line = line;
    this.column = col;
  }

}
