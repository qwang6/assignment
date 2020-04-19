package com.fresno.fs;

// proxy pattern
public class ExitProxy implements Visitor {
    private Visitor exitVisitor;

    public ExitProxy() {

    }

    public ExitProxy(Visitor exitVisitor) {
        this.exitVisitor = exitVisitor;
    }

    @Override
    public void visit(Node node) throws Exception {
        exitVisitor.visit(node);
    }
}