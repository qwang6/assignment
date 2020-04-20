package com.fresno.fs;
// builder pattern
public interface Builder {
    public Node getRoot();

    public void makeDirAction(String cmd) throws Exception; //condition check, throw exception if false.

    public void cdAction(String cmd) throws Exception;

    public void createAction(String cmd) throws Exception;

    public void delAction(String cmd) throws Exception;

    public void lsAction(String cmd) throws Exception;

    public void sizeAction(String cmd) throws Exception;

    public void reSizeAction(String cmd) throws Exception;

    public void exit() throws Exception;
}
