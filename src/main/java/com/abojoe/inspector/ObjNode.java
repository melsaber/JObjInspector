/*
 * Copyright (c) 2020 Abojoe Limited. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package com.abojoe.inspector;

import javax.swing.tree.TreeNode;
import java.lang.reflect.Field;
import java.util.*;

class ObjNode implements TreeNode {
    private Object object;
    private String name;
    private TreeNode parent;
    private String text="";
    private String toolTip="";
    private List<TreeNode> children;

    public ObjNode(String name, Object object){
        this.object=object;
        this.name=name;
        children=new ArrayList<>();
    }

    public ObjNode(String name, Object object, TreeNode parent){
        this.object=object;
        this.name=name;
        children=new ArrayList<>();
        this.parent=parent;

    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children.size();
    }
    private List<TreeNode> getChildren(){
        return children;
    }
    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return (Enumeration) children;
    }

    @Override
    public String toString() {
        if (isPrimitive()) {
            String shortend=object.toString();
            shortend=shortend.length()>16 ? shortend.substring(0,12) + " ...":shortend;
            return  name + " (" + object.getClass().getSimpleName() + "): " + shortend;
        }
        else {
            return name  + " (" + object.getClass().getSimpleName() + ")";
        }
    }

    public Object getObject() {
        return object;
    }

    public String getName() {
        return name;
    }

    public String getText(){
        if (!text.isEmpty())
            return text;

        text=object.getClass().getName() + "\n-------------------------\n\ntoString()= ";
        if(isCollection()){
            Collection col=(Collection) object;
            int i=0;
            text+="List:\n";
            for (Object v:col){
                text+= name  + "[" + i++ + "] = "+ v + "\n";
            }
            return text;
        }

        if (isMap()){
            Map col=(Map) object;
            text+="Map:\n";
            for (Object v:col.keySet()){
                text+= name  + "[" + v + "]=" + col.get(v)+ "\n";
            }
            return text;
        }
        text+=object.toString();
        return text;
    }

    public String getToolTip(){
        if (!toolTip.isEmpty())
            return toolTip;
        else if(isCollection()){
            Collection col=(Collection) object;
            int i=0;
            toolTip+="List:";
            for (Object v:col){
                toolTip+= name  + "[" + i++ + "] = "+ v + "; ";
            }
        }

        else if (isMap()){
            Map col=(Map) object;
            toolTip+="Map: ";
            for (Object v:col.keySet()){
                toolTip+= name  + "[" + v + "]=" + col.get(v)+ "; ";
            }
        }else {

        toolTip+=object.toString();}

        toolTip=toolTip.length()>40 ? toolTip.substring(0,36) + " ...":toolTip;
        return toolTip;
    }
    public void loadChildren(){
        if (!this.isLeaf())
            return;

        if(isCollection()){
            ObjNode t1;
            Collection col = (Collection) object;
            int i = 0;
            for (Object v : col) {
                t1 = new ObjNode(name + "[" + i++ + "]", v,this);
                this.getChildren().add(t1);
            }
            
            return;
        }

        if (isMap()){
            ObjNode t1;
            Map map=(Map) object;
            for (Object v:map.keySet()){
                t1=new ObjNode(name+ "[" + v + "]",map.get(v),this);
                this.getChildren().add(t1);
            }
            
            return;
        }
        if (!isPrimitive()){// Custom Object Class
            ObjNode t1;
            Class<?> objClass =object.getClass();
            do {
                for (Field f : objClass.getDeclaredFields()) {
                    f.setAccessible(true);
                    try {
                        t1 = new ObjNode(f.getName(), f.get(object),this);
                        this.getChildren().add(t1);
                    } catch (Exception e) { }
                }
                objClass = objClass.getSuperclass();
            }while (objClass!=null);
            
        }

    }
    
    public boolean isMap() {
        String className=object.getClass().getName();
        switch(className) {
            case "java.util.HashMap":
            case "java.util.LinkedHashMap":
            case "java.util.WeakHashMap":
            case "java.util.TreeMap":
            case "java.util.Hashtable":
                return true;
            default:
                return false;
        }

    }

    public boolean isCollection() {
        String className=object.getClass().getName();
        switch(className) {
            case "java.util.ArrayList":
            case "java.util.LinkedList":
            case "java.util.TreeSet":
            case "java.util.HashSet":
            case "java.util.Vector":
                return true;
            default:
                return className.charAt(0) == '[';
        }
    }

    public boolean isPrimitive() {
        String className=object.getClass().getName();
        switch(className) {
            case "boolean":
            case "byte":
            case "short":
            case "char":
            case "int":
            case "long":
            case "float":
            case "double":
            case "long double":
            case "java.lang.Boolean":
            case "java.lang.Byte":
            case "java.lang.Short":
            case "java.lang.Character":
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.lang.Float":
            case "java.lang.Double":
            case "java.lang.String":
                return true;
            default:
                return false;
        }
    }
}
