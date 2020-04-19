package com.fresno.fs;

import java.util.HashSet;
import java.util.Set;

public class ExitVisitor implements Visitor {
    private void delete(Node root) {
        deleteHelper(root);
    }

    private void deleteHelper(Node node) {
        if (node == null) return;
        Set<String> names = new HashSet<>(node.children.keySet());

        for (String name : names) {
            if (node.children.containsKey(name)) {
                Node next = node.children.get(name);
                deleteHelper(next);
            }
        }
        if (node.toBeDeleted) {
            Node parent = node.parent;
            String name = node.name;
            parent.children.remove(name);
            node.parent = null;
        }
    }

    @Override
    public void visit(Node node) throws Exception {
        delete(node);
    }
}
