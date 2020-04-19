package com.fresno.fs;
// concrete builder
public class FileSystemBuilder implements Builder {
    private Node root;
    private Node curNode;
    private Visitor delVisitor;
    private Visitor lsVisitor;
    private Visitor sizeVisitor;
    private Visitor deleteProxy;
    private static Builder builder;
    //private constructor for singleton pattern
    private FileSystemBuilder() {
        this.root = new Node();
        root.name = FSConstant.ROOT_NAME; // define a changeable name for root. here root is "/".
        root.depth = 0;
        this.curNode = root;  // start from an empty file system
        this.delVisitor = DelVisitor.getInstance();  //use singleton pattern to instantiate an object.
        this.lsVisitor = LsVisitor.getInstance();
        this.sizeVisitor = SizeVisitor.getInstance();
        this.deleteProxy = new ExitProxy(new ExitVisitor());
    }

    // static builder method for singleton pattern
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
        String[] strs = cmd.split(" ");  // split command to word
        // 1. check command
        if (strs.length != 2) {                // mkdir <dirname> command needs two words
            throw new Exception("mkdir command need one parameter!");
        }
        // 2. check duplicated dir name
        String dirName = strs[1];  // get directory name
        // if the name already existed, then throw exception
        if (curNode.children.containsKey(dirName)) {
            throw new Exception("The directory of " + dirName + " is already existed!");
        }
        // 3. create new directory
        Node newNode = new Node(dirName);
        curNode.children.put(dirName, newNode);    // store the new directory info to children HashMap
        newNode.parent = curNode;                  // set parent node
        newNode.depth = newNode.parent.depth + 1;  // get depth from parent node
        // 4. call static function to print path
        System.out.println("Make dir of " + FSUtils.getAbsolutePath(newNode));
    }

    @Override
    public void cdAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        // 1. check command
        if (strs.length != 2) {
            throw new Exception("cd command need one parameter!");
        }
        String dirName = strs[1];

        // 2. enter to parent directory and print current path
        if (dirName.equals("..")) {
            curNode = curNode.parent;
            System.out.println("Entered the parent directory " + FSUtils.getAbsolutePath(curNode));
            return;
        }

        // 3. enter to child directory and print current path
        // 3.1 check children HashMap, throw exception if the name doesn't exist.
        if (!curNode.children.containsKey(dirName)) {
            throw new Exception("The directory of " + dirName + " is not existed!");
        }
        // 3.2 check children HashMap, throw exception if it's a file name.
        if (curNode.children.get(dirName).isFile) {
            throw new Exception("The directory of " + dirName + " to be entered is a file!");
        }
        // 3.3 print path
        curNode = curNode.children.get(dirName); // get Node from children HashMap
        System.out.println("Entered the directory " + FSUtils.getAbsolutePath(curNode));
    }

    @Override
    public void createAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        // 1. check command
        if (strs.length != 3) {        //create <filename> <size> command needs three words
            throw new Exception("create command need two parameter!");
        }

        // 2. check duplicated name
        String fileName = strs[1];
        int fileSize = Integer.parseInt(strs[2]);  // convert size to int
        if (curNode.children.containsKey(fileName)) {
            throw new Exception("The file " + fileName + " is already existed!");
        }
        // 3. create new file and print path
        Node newNode = new Node(fileName, fileSize);
        newNode.isFile = true;                     // leaf node
        newNode.parent = curNode;                  // set parent
        newNode.depth = newNode.parent.depth + 1;  // get depth from parent
        curNode.children.put(fileName, newNode);   // store info to children HashMap
        System.out.println("Created the file of " + FSUtils.getAbsolutePath(newNode) + " and the size is " + newNode.fileSize);
    }

    @Override
    public void delAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        // 1. check command
        if (strs.length != 2) {
            throw new Exception("del command need one parameter!");
        }
        // 2. check if the name existed
        String name = strs[1];
        if (!curNode.children.containsKey(name)) {
            throw new Exception("The target " + name + " is not existed!");
        }
        // 3. apply visitor pattern, pass delVisitor object to implement delete
        curNode.children.get(name).accept(delVisitor);  // map name to node and call accept->del visit
    }

    @Override
    public void lsAction() throws Exception {
        //apply visitor pattern, pass lsVisitor object to implement ls
        curNode.accept(lsVisitor);  //current node call accept->ls visit
    }

    @Override
    public void sizeAction(String cmd) throws Exception {
        String[] strs = cmd.split(" ");
        // 1. check command
        if (strs.length != 2) {
            throw new Exception("size command need one parameter!");
        }
        // 2. check if the name existed
        String name = strs[1];
        if (!curNode.children.containsKey(name)) {
            throw new Exception("The file " + name + " doesn't exist!");
        }
        // 3. apply visitor pattern, pass sizeVisitor object to get size
        curNode.children.get(name).accept(sizeVisitor); // map name to node and call accept->size visit
    }

    @Override
    public void exit() throws Exception {
        // apply proxy pattern and print the result
        deleteProxy.visit(root);
        System.out.println("Did the final clean deletion.\n");
    }
}
