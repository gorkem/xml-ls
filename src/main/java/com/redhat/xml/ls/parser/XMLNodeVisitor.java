package com.redhat.xml.ls.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;





public class XMLNodeVisitor extends ASTVisitor{

    List<MethodDeclaration> methods = new ArrayList<>();

    @Override
    public boolean visit(MethodDeclaration node){
        methods.add(node);
        return super.visit(node);
    }


    public List<MethodDeclaration> getMethods(){
        return methods;
    }

    public static void main(String[] args){
        //ASTNode.
    }

    
}