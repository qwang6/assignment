package com.fresno.fs;

public class SizeVisitor implements Visitor {

    private static Visitor sizeVisitor;

    private SizeVisitor() {

    }

    // singleton pattern
    public static Visitor getInstance() {
        if (sizeVisitor == null) {
            sizeVisitor = new SizeVisitor();
        }
        return sizeVisitor;
    }
    // size visit, get the summation size of folder or get size of file directly.
    public void visit(Node node) {
        if (!node.isFile) {         // node is a folder
            int folderSize = getSize(node);
            System.out.println(FSUtils.getAbsolutePath(node) + " is a folder, the total size of this folder is " + folderSize);
        } else {                    // node is a file
            System.out.println("The file " + FSUtils.getAbsolutePath(node) + " size is " + node.fileSize);
        }
    }
    private int getSize(Node node) {
        if (node == null) return 0;
        int res = 0;
        for (Node next : node.children.values()) {
            if (next.isFile) {
                res += next.fileSize;        // sum up file size directly
            } else {
                res += getSize(next);        // sum up folder size recursively
            }
        }
        return res;
    }
}
