package com.fresno.fs;

public class TreeDisplayAdapter implements TreeDisplay {

    private FileSystemDisplay fsDisplay;

    public TreeDisplayAdapter(FileSystemDisplay fsDisplay) {
        this.fsDisplay = fsDisplay;
    }

    @Override
    public void display(Node node) {
        fsDisplay.printFileSystem(node);
    }
}
