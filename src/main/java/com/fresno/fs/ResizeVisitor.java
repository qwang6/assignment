package com.fresno.fs;

public class ResizeVisitor implements ReVisitor {
    // observer pattern
    private TreeDisplay observer;

    public ResizeVisitor(TreeDisplay treeDisplay) {
        this.observer = treeDisplay;
    }

    // resize visit, set new size to a file.
    public void visit(Node node, int newSize) {
        node.fileSize = newSize;
        System.out.println("Resize the file " + FSUtils.getAbsolutePath(node) + ", new size is " + newSize);
        notifyDisplayTree(node);
    }

    private void notifyDisplayTree(Node node) {
        System.out.println("The file " + node.name + " is resized");
        System.out.println("Re-display the file system");
        Node root = getRoot(node);
        observer.display(root);
    }

    private Node getRoot(Node node) {
        while (!node.name.equals(FSConstant.ROOT_NAME)) {
            node = node.parent;
        }
        return node;
    }
}