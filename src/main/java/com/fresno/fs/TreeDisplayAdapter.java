package com.fresno.fs;
// adapter pattern
public class TreeDisplayAdapter implements TreeDisplay {

    private FileSystemDisplay fsDisplay;   // object of adaptee

    public TreeDisplayAdapter(FileSystemDisplay fsDisplay) {
        this.fsDisplay = fsDisplay;
    }

    @Override
    public void display(Node node) {
        fsDisplay.printFileSystem(node);
    }
}
