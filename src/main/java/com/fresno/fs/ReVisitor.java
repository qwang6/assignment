package com.fresno.fs;

// design a new visitor interface for resize, pass two parameters.
public interface ReVisitor {

    public void visit(Node node, int newSize) throws Exception;
}
