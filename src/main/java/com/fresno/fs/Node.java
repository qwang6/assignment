package com.fresno.fs;

import java.util.HashMap;
import java.util.Map;

public class Node {
    String name;
    int fileSize;
    int depth;
    Map<String, Node> children = new HashMap<>();
    Node parent;
    boolean isFile;
    boolean toBeDeleted;

    public Node() {}

    public Node(String name) {
        this.name = name;
    }

    public Node(String name, int fileSize) {
        this.name = name;
        this.fileSize = fileSize;
    }

    public void accept(Action action) throws Exception {
        action.visit(this);
    }
}
