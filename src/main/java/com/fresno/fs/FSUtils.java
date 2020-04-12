package com.fresno.fs;

public class FSUtils {
    public static String getAbsolutePath(Node node) {
        if (node.name.equals(FSConstant.ROOT_NAME)) return FSConstant.ROOT_NAME;

        String res = "";
        while (node != null) {
            res = node.name + "/" + res;
            node = node.parent;
        }
        return res.substring(1, res.length()-1);
    }
}
