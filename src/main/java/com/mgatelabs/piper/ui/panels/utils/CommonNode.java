package com.mgatelabs.piper.ui.panels.utils;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * Created by @mgatelabs (Michael Fuller) on 10/16/2017
 */
public class CommonNode<T> extends DefaultMutableTreeNode {
    private final NodeType type;

    public CommonNode(NodeType type, T value) {
        super(value);
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }

    public void refresh() {

    }

    @Override
    public T getUserObject() {
        return (T) super.getUserObject();
    }
}
