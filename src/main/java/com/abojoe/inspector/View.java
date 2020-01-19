/*
 * Copyright (c) 2020 Abojoe Limited. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package com.abojoe.inspector;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

class View  extends JFrame {
    private JTextArea textArea;
    private JTree tree;
    private Controller controller;
    protected View(String title, ObjNode treeNode, int windowW, int windowH, int posX, int posY) {
        try{
            //UIManager.LookAndFeelInfo[] lookandFeel=UIManager.getInstalledLookAndFeels();
            //UIManager.setLookAndFeel(lookandFeel[2].getClassName());
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e){
            System.out.println(e);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame f = new JFrame("Java Object Inspector - (" + title + ")");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tree = new JTree(treeNode);
        tree.setCellRenderer(new NodeIconRenderer());
        javax.swing.ToolTipManager.sharedInstance().registerComponent(tree);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);//TODO
        textArea.setBorder(BorderFactory.createCompoundBorder(
                textArea.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panel.setDividerSize(2);

        panel.add(new JScrollPane(tree));
        panel.add(new JScrollPane(textArea));

        Container content = f.getContentPane();
        content.add(panel, "Center");
        controller=new Controller(this);
        tree.addTreeSelectionListener(controller);

        f.setSize(windowW, windowH);
        if (posX < 0 || posY < 0)
            f.setLocationRelativeTo(null);
        else
            f.setLocation(posX, posY);

        f.setVisible(true);
    }

    protected JTextArea getTextArea() {
        return textArea;
    }

    protected JTree getTree() {
        return tree;
    }

    private class NodeIconRenderer extends DefaultTreeCellRenderer {
        CompoundBorder border;
        protected NodeIconRenderer() {
            border =BorderFactory.createCompoundBorder(
                    this.getBorder(),
                    BorderFactory.createEmptyBorder(2, 0, 2, 0));
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            ObjNode node = (ObjNode) value;
            URL imageUrl;

            if (node.isPrimitive()) {
                imageUrl = getClass().getResource("/images/sim.png");
            } else if (node.isCollection()) {
                imageUrl = getClass().getResource("/images/col.png");
            } else if (node.isMap()) {
                imageUrl = getClass().getResource("/images/map.png");
            }else {
                imageUrl = getClass().getResource("/images/cus.png");
            }

            this.setBorder(border);
            if (imageUrl != null) {
                this.setIcon(new ImageIcon(imageUrl));
                this.setText(node.toString());
                this.setToolTipText(node.getToolTip());
            }
            return this;
        }
    }
}


