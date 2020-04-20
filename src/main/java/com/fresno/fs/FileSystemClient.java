package com.fresno.fs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileSystemClient {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = FileSystemClient.class.getResourceAsStream("/script2.txt");
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);

        // Decorator & adaptor Pattern for TreeDisplay, chaining instantiation
        TreeDisplay treeDisplay = new FooterTextDisplay(new HeaderTextDisplay(new TreeDisplayAdapter(FileSystemDisplay.getInstance())));

        // Builder Pattern, using Dependency Injection to pass treeDisplay object to Builder
        Builder builder = new FileSystemBuilder(treeDisplay);
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
                    builder.lsAction(line);
                    break;
                case "size":
                    builder.sizeAction(line);
                    break;
                case "resize":
                    builder.reSizeAction(line);
                    break;
                case "exit":
                    builder.exit();
                    break;
                default:
                    System.out.println("no match");
            }
        }

        treeDisplay.display(builder.getRoot());  // execute FileSystemDisplay()
    }
}
