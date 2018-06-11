package com.redhat.xml.ls.parser.XMLNodes;

public interface Visitable {
    public void accept(XMLNodeVisitor node);
}