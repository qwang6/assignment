package com.fresno.fs;

public class FooterTextDisplay extends HeaderTextDisplay {
    public FooterTextDisplay(TreeDisplay treeDisplay) {
        super(treeDisplay);
    }

    public void display(Node node) {
        treeDisplay.display(node);
        System.out.println("The File System Footer ********************");
        System.out.println("*******************************************");
    }
}
