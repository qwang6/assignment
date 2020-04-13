package com.fresno.fs;

public class FileSystemBuilder implements Builder {
    private Node root;
    private Node curNode;
    private Visitor delVisitor;
    private Visitor lsVisitor;
    private Visitor sizeVisitor;
    private DeleteProxy deleteProxy;
    private static Builder builder;

    private FileSystemBuilder() {
        this.root = new Node();
        root.name = FSConstant.ROOT_NAME;
        root.depth = 0;
        this.curNode = root;
        this.delVisitor = DelVisitor.getInstance();
        this.lsVisitor = LsVisitor.getInstance();
        this.sizeVisitor = SizeVisitor.getInstance();
        this.deleteProxy = DeleteProxy.getInstance();
    }

    // singleton pattern
    public static Builder getInstance() {
        if (builder == null) {
            builder = new FileSystemBuilder();
        }
        return builder;
    }

    @Override
    public Node getRoot() {
        return this.root;
    }

    @Override
    public void makeDirAction(String cmd)  throws Exception{
        String[] strs = cmd.split(" ");
        if (strs.length != 2) {
            throw new Exception("mkdir command need one parameter!");
        }

        String dirName = strs[1];
        // if the folder already existed, then throw exception
        if (curNode.children.containsKey(dirName)) {
            throw new Exception("The directory of " + dirName + " was already existed!");
        }

        Node newNode = new Node(dirName);
        curNode.children.put(dirName, newNode);
        newNode.parent = curNode;
        newNode.depth = newNode.parent.depth + 1;
        System.out.println("Make dir of " + FSUtils.getAbsolutePath(newNode));
    }

    @Override
    public void cdAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        if (strs.length != 2) {
            throw new Exception("cd command need one parameter!");
        }
        String dirName = strs[1];

        // if enter to upper directory
        if (dirName.equals("..")) {
            curNode = curNode.parent;
            System.out.println("Entered the parent directory " + FSUtils.getAbsolutePath(curNode));
            return;
        }

        // enter to child directory
        if (!curNode.children.containsKey(dirName)) {
            throw new Exception("The directory of " + dirName + " is not existed!");
        }
        if (curNode.children.get(dirName).isFile) {
            throw new Exception("The directory of " + dirName + " to be entered is a file!");
        }

        curNode = curNode.children.get(dirName);
        System.out.println("Entered the directory " + FSUtils.getAbsolutePath(curNode));
    }

    @Override
    public void createAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        if (strs.length != 3) {
            throw new Exception("create command need two parameter!");
        }

        String fileName = strs[1];
        int fileSize = Integer.parseInt(strs[2]);
        if (curNode.children.containsKey(fileName)) {
            throw new Exception("The file " + fileName + " was already existed!");
        }

        Node newNode = new Node(fileName, fileSize);
        newNode.isFile = true;
        newNode.parent = curNode;
        newNode.depth = newNode.parent.depth + 1;
        curNode.children.put(fileName, newNode);
        System.out.println("Created the file of " + FSUtils.getAbsolutePath(newNode) + " and the size is " + newNode.fileSize);
    }

    @Override
    public void delAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        if (strs.length != 2) {
            throw new Exception("del command need one parameter!");
        }
        String name = strs[1];
        if (!curNode.children.containsKey(name)) {
            throw new Exception("The target " + name + " is not existed!");
        }
        curNode.children.get(name).accept(delVisitor);
    }

    @Override
    public void lsAction() throws Exception {
        curNode.accept(lsVisitor);
    }

    @Override
    public void sizeAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        if (strs.length != 2) {
            throw new Exception("size command need one parameter!");
        }
        String name = strs[1];
        if (!curNode.children.containsKey(name)) {
            throw new Exception("The file " + name + " doesn't exist!");
        }
        curNode.children.get(name).accept(sizeVisitor);
    }

    @Override
    public void exit() {
        deleteProxy.delete(root);
        System.out.println("Did the final clean deletion.\n");
    }
}
