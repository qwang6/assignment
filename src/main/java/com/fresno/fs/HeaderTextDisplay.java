package com.fresno.fs;

public class HeaderTextDisplay implements TreeDisplay {

    private TreeDisplay treeDisplay;

    public HeaderTextDisplay(TreeDisplay treeDisplay) {
        this.treeDisplay = treeDisplay;
    }

    public void display(Node node) {
        System.out.println("\nThe File System Header ************************");
        treeDisplay.display(node);
    }
}
