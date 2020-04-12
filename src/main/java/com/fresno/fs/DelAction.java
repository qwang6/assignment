package com.fresno.fs;

public class DelAction implements Action {

    private static Action delAction;

    private DelAction() {

    }

    // singleton pattern
    public static Action getInstance() {
        if (delAction == null) {
            delAction = new DelAction();
        }
        return delAction;
    }

    public void visit(Node node) throws Exception {
        if (!node.isFile) {
            if (node.name.equals(FSConstant.ROOT_NAME)) {
                throw new Exception("The root directory cannot be deleted!");
            }
            delRecursive(node);
        } else {
            node.toBeDeleted = true;
        }
        System.out.println("Tag the deletion on node " + FSUtils.getAbsolutePath(node));
    }
    private void delRecursive(Node node) {
        if (node == null) return;
        node.toBeDeleted = true;
        for (Node next : node.children.values()) {
            delRecursive(next);
        }
    }
}
