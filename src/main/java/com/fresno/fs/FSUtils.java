package com.fresno.fs;

// print file path
public class FSUtils {
    // static method can be called without object.
    public static String getAbsolutePath(Node node) {
        // root return /
        if (node.name.equals(FSConstant.ROOT_NAME)) return FSConstant.ROOT_NAME;

        // print path bottom up
        String res = "";
        while (node != null) {
            res = node.name + "/" + res;
            node = node.parent;
        }
        // there will be two "/" at the beginning of path, remove the first "/"
        return res.substring(1, res.length()-1);
    }
}
