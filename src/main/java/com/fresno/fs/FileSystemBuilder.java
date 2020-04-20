package com.fresno.fs;

// concrete builder
public class FileSystemBuilder implements Builder {
    private Node root;
    private Node curNode;
    private Visitor delVisitor;
    private Visitor lsVisitor;
    private Visitor sizeVisitor;
    private Visitor exitProxy;
    private ReVisitor reSizeVisitor;

    public FileSystemBuilder(TreeDisplay treeDisplay) {
        this.root = new Node();
        root.name = FSConstant.ROOT_NAME; // define a changeable name for root. here root is "/".
        root.depth = 0;
        this.curNode = root;  // start from an empty file system
        this.delVisitor = DelVisitor.getInstance();  //use singleton pattern to instantiate an object.
        this.lsVisitor = LsVisitor.getInstance();
        this.sizeVisitor = SizeVisitor.getInstance();
        this.exitProxy = new ExitProxy(new ExitVisitor());
        this.reSizeVisitor = new ResizeVisitor(treeDisplay);
    }

    @Override
    public Node getRoot() {
        return this.root;
    }

    @Override
    public void makeDirAction(String cmd)  throws Exception{
        System.out.println(FSUtils.getCmdPrefix(curNode, cmd));

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
    }

    @Override
    public void cdAction(String cmd) throws Exception {
        System.out.println(FSUtils.getCmdPrefix(curNode, cmd));

        String[] strs = cmd.split(" ");
        // 1. check command
        if (strs.length != 2) {
            throw new Exception("cd command need one parameter!");
        }
        String dirName = strs[1];

        // 2. enter to parent directory and print current path
        if (dirName.equals("..")) {
            curNode = curNode.parent;
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
    }

    @Override
    public void createAction(String cmd) throws Exception {
        System.out.println(FSUtils.getCmdPrefix(curNode, cmd));

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
    }

    /**
     * Common Function to accept cmd line, and to execute the action in visitor pattern
     * @param cmd       cmd line
     * @param visitor   visitor object
     * @throws Exception
     */
    private void action(String cmd, Visitor visitor)  throws Exception {
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
        //apply visitor pattern, pass lsVisitor object to implement ls
        curNode.children.get(name).accept(visitor);  //current node call accept->ls visit
    }

    @Override
    public void lsAction(String cmd) throws Exception {
        System.out.println(FSUtils.getCmdPrefix(curNode, cmd));

        //current node call accept->ls visit
        action(cmd, this.lsVisitor);
    }

    @Override
    public void sizeAction(String cmd) throws Exception {
        System.out.println(FSUtils.getCmdPrefix(curNode, cmd));

        // map name to node and call accept->size visit
        action(cmd, this.sizeVisitor);
    }

    @Override
    public void delAction(String cmd) throws Exception {
        System.out.println(FSUtils.getCmdPrefix(curNode, cmd));

        // map name to node and call accept->del visit
        action(cmd, this.delVisitor);
    }

    @Override
    public void reSizeAction(String cmd) throws Exception{
        System.out.println(FSUtils.getCmdPrefix(curNode, cmd));

        String[] strs = cmd.split(" ");
        // 1. check command
        if (strs.length != 3) {
            throw new Exception("size command need two parameters!");
        }
        // 2. check if the name existed
        String name = strs[1];
        if (!curNode.children.containsKey(name)) {
            throw new Exception("The file " + name + " doesn't exist!");
        }
        // 3. check if the node is a folder
        if (!curNode.children.get(name).isFile) {
            throw new Exception( name + " cannot be resized!");
        }

        // 4. apply visitor pattern, since the new size need be passed to visitor, I design a Revisitor interface for resize visitor.
        curNode.children.get(name).accept(reSizeVisitor, Integer.parseInt(strs[2])); // map name to node and call accept->resize visit
    }

    @Override
    public void exit() throws Exception {
        System.out.println(FSUtils.getCmdPrefix(curNode, "exit"));

        // apply proxy pattern and print the result
        exitProxy.visit(root);
        System.out.println("Did the final clean deletion.\n");
    }
}
