package com.fresno.fs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LsAction implements Action {

    private static Action lsAction;

    private LsAction() {

    }

    // singleton pattern
    public static Action getInstance() {
        if (lsAction == null) {
            lsAction = new LsAction();
        }
        return lsAction;
    }

    public void visit(Node node) {
        List<Node> folderList = new ArrayList<>();
        List<Node> fileList = new ArrayList<>();

        for (String name : node.children.keySet()) {
            Node next = node.children.get(name);
            if (next.isFile) fileList.add(next);
            else folderList.add(next);
        }
        folderList.sort(Comparator.comparing(a -> a.name));
        fileList.sort(Comparator.comparing(a -> a.name));
        StringBuilder sb = new StringBuilder();
        for (Node n : folderList) {
            sb.append("--d    ").append(n.name).append('\n');
        }
        for (Node n : fileList) {
            sb.append("--f    ").append(n.name).append("    ").append(n.fileSize).append('\n');
        }
        System.out.println("List the folder " + FSUtils.getAbsolutePath(node));
        System.out.println(sb.toString());
    }

}
