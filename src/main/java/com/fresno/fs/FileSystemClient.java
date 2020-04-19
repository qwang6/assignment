package com.fresno.fs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileSystemClient {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = FileSystemClient.class.getResourceAsStream("/script1.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);

        Builder builder = FileSystemBuilder.getInstance();
        for (String line; (line = reader.readLine()) != null; ) {
            String[] strs = line.split(" ");
            String cmd = strs[0].toLowerCase();
            switch (cmd) {
                case "mkdir":
                    builder.makeDirAction(line);
                    break;
                case "create":
                    builder.createAction(line);
                    break;
                case "cd":
                    builder.cdAction(line);
                    break;
                case "del":
                    builder.delAction(line);
                    break;
                case "ls":
                    builder.lsAction();
                    break;
                case "size":
                    builder.sizeAction(line);
                    break;
                case "exit":
                    builder.exit();
                    break;
                default:
                    System.out.println("no match");
            }
        }
        // adapter pattern, chaining instantiation
        TreeDisplay treeDisplay = new FooterTextDisplay(new HeaderTextDisplay(new TreeDisplayAdapter(FileSystemDisplay.getInstance())));
        treeDisplay.display(builder.getRoot());  // execute FileSystemDisplay()
    }
}
