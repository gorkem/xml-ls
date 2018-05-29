package com.redhat.xml.ls.services.serviceutils;

import com.redhat.xml.ls.parser.XMLNodes.XMLNode;

import org.eclipse.lsp4j.Position;

//General utilities for all services
public class Util {

    public static void main(String[] args) {

    }

    public static XMLNode FindClosestNode(XMLNode root, Position position) {
        
        int character = position.getCharacter(); // column
        int line = position.getLine(); // row

        XMLNode currentRoot = root;
        int rootStartCharacter = currentRoot.start.column;
        int rootStartLine = currentRoot.start.line;

        int rootEndCharacter = currentRoot.end.column;
        int rootEndLine = currentRoot.end.line;

        if(line == rootStartLine){
            int maxCharacterPosition = MaxStartColumnValue(rootStartCharacter, currentRoot.name);
            if(character <= maxCharacterPosition && character >= rootStartCharacter)return currentRoot;
        }

        if(line == rootEndLine){
            int minCharacterPosition = MinEndColumnValue(rootEndCharacter, currentRoot.name);
            if(character <= rootEndCharacter && character >= minCharacterPosition)return currentRoot;
        }
        
       
        // for(XMLNode node : currentRoot.children){
        //     int endNodeCharacter = node.end.column;
        //     int endNodeLine = node.end.line;
        //     int startNodeCharacter = node.start.column;
        //     int startNodeLine = node.start.line;
            
        //     //Selection is in this element
        //     if(line <= endNodeLine){
                

        //         return FindClosestNode(node, position);
        //     }


        // }

        return currentRoot;
    }

    public static int MaxStartColumnValue(int startCharacter, String name){
        return name.length() + startCharacter - 1;
    }

    public static int MinEndColumnValue(int startCharacter, String name){
        return name.length() + startCharacter - 1;
    }
}