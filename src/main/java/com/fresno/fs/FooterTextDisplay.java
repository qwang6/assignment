package com.fresno.fs;

public class FooterTextDisplay implements TreeDisplay {
    private TreeDisplay treeDisplay;
    public FooterTextDisplay(TreeDisplay treeDisplay) {
        this.treeDisplay = treeDisplay;
    }

    public void display(Node node) {
        treeDisplay.display(node);
        System.out.println("The File System Footer ********************");
        System.out.println("*******************************************");
    }
}
