package com.fresno.fs;

public class ResizeVisitor implements ReVisitor {

    private TreeDisplay observer;   // used for observer pattern
    // dependency injection, ResizeVisitor class needs a TreeDisplay object to execute notify and update.
    public ResizeVisitor(TreeDisplay treeDisplay) {
        this.observer = treeDisplay;
    }

    // resize visitor, set new size to a file.
    public void visit(Node node, int newSize) {
        node.fileSize = newSize;
        notifyDisplayTree(node);
    }

    // notify TreeDisplay observer and update display
    private void notifyDisplayTree(Node node) {
        System.out.println("Resize the file " + node.name + " ,re-display the file system");
        Node root = getRoot(node);
        observer.display(root);       //observer call display to update the file system.
    }

    private Node getRoot(Node node) {
        while (!node.name.equals(FSConstant.ROOT_NAME)) {
            node = node.parent;
        }
        return node;
    }
}