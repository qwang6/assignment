package com.fresno.fs;

import java.util.HashMap;
import java.util.Map;

// composite pattern, hide difference between whole and part from clients.
// in order to simplify the structure, I use Node class to represent both file and folder.
public class Node {
    String name;
    int fileSize;
    int depth;
    Map<String, Node> children = new HashMap<>();  // store the info of nodes to HashMap
    Node parent;
    boolean isFile;         // set file label
    boolean toBeDeleted;    // set delete label

    public Node() {}

    public Node(String name) {  // directory constructor
        this.name = name;
    }

    public Node(String name, int fileSize) {  // file constructor
        this.name = name;
        this.fileSize = fileSize;
    }

    // visitor pattern, pass visitor object to Node then call visit function
    // visit function depends on the visitor object
    public void accept(Visitor visitor) throws Exception {
        visitor.visit(this);
    }
}
