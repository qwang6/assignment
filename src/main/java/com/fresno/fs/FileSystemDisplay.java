package com.fresno.fs;
// adaptee
public class FileSystemDisplay {

    private static FileSystemDisplay fileSystemDisplay;

    private FileSystemDisplay() {}
    // singleton pattern
    public static FileSystemDisplay getInstance() {
        if (fileSystemDisplay == null) {
            fileSystemDisplay = new FileSystemDisplay();
        }
        return fileSystemDisplay;
    }

    public void printFileSystem(Node root) {
        printHelper(root);
    }

    private static void printHelper(Node node) {
        if (node == null) return;

        int depth = node.depth;
        StringBuilder sb = new StringBuilder("|");              // create a mutable sequence of characters
        for (int i = 0; i < depth * FSConstant.INDENT; i++) {   // print indentation
            sb.append("-");
        }
        sb.append(node.name);                                   // print file|folder name
        if (node.isFile) {
            sb.append("    ").append(node.fileSize);            // print file size after name
        }
        System.out.println(sb.toString());                      // print file system tree recursively
        for (Node next : node.children.values()) {
            printHelper(next);
        }
    }
}
