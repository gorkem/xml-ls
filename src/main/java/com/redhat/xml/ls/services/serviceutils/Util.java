package com.redhat.xml.ls.services.serviceutils;

import java.util.ArrayList;
import java.util.List;

import com.redhat.xml.ls.parser.XMLNodes.XMLNode;
import com.redhat.xml.ls.services.visitors.XMLNodeVisitor;

import org.eclipse.lsp4j.Position;

//General utilities for all services
public class Util {

    public static XMLNode FindInnerMostNode(XMLNode root, Position position) {

        XMLNode currentNode = root;
        int index = 0;

        if (currentNode.children == null) {
            return null;
        }

        while (index < currentNode.children.length) {
            if (withinStartEnd(currentNode.children[index], position)) {
                currentNode = currentNode.children[index];
                index = 0;
                if (currentNode.children == null) {

                    return currentNode;
                }
            } else {
                index++;
            }

        }

        if (currentNode.nodeType == XMLNode.DOCUMENT_NODE) {
            return null;
        }
        return currentNode;

    }

    public static boolean withinStartEnd(XMLNode current, Position position) {
        return withinStartEnd(current.start, current.end, position);

    }

    public static boolean withinStartEnd(Position start, Position end, Position position) {

        return isPositionBeforeEnd(position, end) && isPositionAfterStart(position, start);

    }

    private static boolean isPositionBeforeEnd(Position position, Position end) {
        if(position.getLine() <= end.getLine()){
            if(position.getLine() == end.getLine()){
                return position.getCharacter() <= end.getCharacter();
            }
            return true;
        }
        return false;
    }

    private static boolean isPositionAfterStart(Position position, Position start) {
        if(position.getLine() >= start.getLine()){
            if(position.getLine() == start.getLine()){
                return position.getCharacter() >= start.getCharacter();
            }
            return true;
        }
        return false;
    }

    public static List<?> createServiceObjectListBFS(XMLNode root, XMLNodeVisitor visitor) {
        NodeQueue queue = new NodeQueue();
        queue.push(root);
        XMLNode currentNode;

        while (queue.size() > 0) {
            currentNode = queue.pop();
            currentNode.accept(visitor);
            if (currentNode.children != null) {
                queue.push(currentNode.children);
            }
        }

        return visitor.getList();

    }

}