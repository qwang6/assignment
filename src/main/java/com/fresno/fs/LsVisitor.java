package com.fresno.fs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LsVisitor implements Visitor {

    private static Visitor lsVisitor;

    private LsVisitor() {

    }

    // singleton pattern
    public static Visitor getInstance() {
        if (lsVisitor == null) {
            lsVisitor = new LsVisitor();
        }
        return lsVisitor;
    }

    //ls visit, print folder and file.
    public void visit(Node node) {
        if (node.isFile) {
            System.out.println(node.name + "    " + node.fileSize);
            return;
        }

        List<Node> folderList = new ArrayList<>();
        List<Node> fileList = new ArrayList<>();

        // distinguish between folder and file
        for (String name : node.children.keySet()) {
            Node next = node.children.get(name);
            if (next.isFile) fileList.add(next);
            else folderList.add(next);
        }
        // sorting name by increasing character order
        folderList.sort(Comparator.comparing(a -> a.name));
        fileList.sort(Comparator.comparing(a -> a.name));

        // print pattern
        StringBuilder sb = new StringBuilder();
        for (Node n : folderList) {
            sb.append("--d    ").append(n.name).append('\n');
        }
        for (Node n : fileList) {
            sb.append("--f    ").append(n.name).append("    ").append(n.fileSize).append('\n');
        }
        System.out.println(sb.toString());  // print folder contents
    }

}
