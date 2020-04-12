package com.fresno.fs;

public class SizeAction implements Action {

    private static Action sizeAction;

    private SizeAction() {

    }

    // singleton pattern
    public static Action getInstance() {
        if (sizeAction == null) {
            sizeAction = new SizeAction();
        }
        return sizeAction;
    }

    public void visit(Node node) {
        if (!node.isFile) {
            int folderSize = getSize(node);
            System.out.println(FSUtils.getAbsolutePath(node) + " is a folder, the total size of this folder is " + folderSize);
        } else {
            System.out.println("The file " + FSUtils.getAbsolutePath(node) + " size is " + node.fileSize);
        }
    }
    private int getSize(Node node) {
        if (node == null) return 0;
        int res = 0;
        for (Node next : node.children.values()) {
            if (next.isFile) {
                res += next.fileSize;
            } else {
                res += getSize(next);
            }
        }
        return res;
    }
}
