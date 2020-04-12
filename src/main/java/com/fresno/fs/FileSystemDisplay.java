package com.fresno.fs;

public class FileSystemDisplay {

    private static FileSystemDisplay fileSystemDisplay;

    private FileSystemDisplay() {}

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
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < depth * 4; i++) {
            sb.append("-");
        }
        sb.append(node.name);
        if (node.isFile) {
            sb.append("    ").append(node.fileSize);
        }
        System.out.println(sb.toString());
        for (Node next : node.children.values()) {
            printHelper(next);
        }
    }
}
