package com.example.poi.utils;

import java.util.List;

public interface TreeNode<T> {
    T id();
    T parentId();
    boolean root();
    void setChildren(List<? extends TreeNode<T>> children);
    List<? extends TreeNode<T>> getChildren();

}
