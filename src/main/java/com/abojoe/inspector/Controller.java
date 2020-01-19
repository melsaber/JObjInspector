/*
 * Copyright (c) 2020 Abojoe Limited. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package com.abojoe.inspector;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

class Controller implements TreeSelectionListener {
    private View view;
    public Controller(View view){
        this.view=view;
    }
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = view.getTree().getSelectionPath();

        if (path != null) {
            ObjNode node=(ObjNode) path.getLastPathComponent();
            if(node.isLeaf()) {
                node.loadChildren();
                if (!node.isLeaf())
                    view.getTree().expandPath(path);
            }
            view.getTextArea().setText(node.getText());
        } else {
            view.getTextArea().setText("");
        }


    }
}