package com.fresno.fs;

public class DelVisitor implements Visitor {    // label nodes as "to be deleted", not really delete.

    private static Visitor delVisitor;

    private DelVisitor() {

    }

    // singleton pattern
    public static Visitor getInstance() {
        if (delVisitor == null) {
            delVisitor = new DelVisitor();
        }
        return delVisitor;
    }

    // delete visitor
    public void visit(Node node) throws Exception {
        // 1. check folder, root cannot be delete
        if (!node.isFile) {
            if (node.name.equals(FSConstant.ROOT_NAME)) {
                throw new Exception("The root directory cannot be deleted!");
            }
            delRecursive(node);                // delete folder recursively
        } else {
            node.toBeDeleted = true;           // label file as "to be deleted"
        }
        System.out.println("Tag the deletion on node " + FSUtils.getAbsolutePath(node));
    }

    private void delRecursive(Node node) {     // delete folder recursively
        if (node == null) return;
        node.toBeDeleted = true;               // label node as "to be deleted"
        for (Node next : node.children.values()) {
            delRecursive(next);                //  delete all the children before delete parent
        }
    }
}
