/*
 * Copyright (c) 2020 Abojoe Limited. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package com.abojoe.inspector;

public class Inspector {
      private static Inspector instance;

      public static void inspect(String name, Object root) {
            ObjNode treeNode = new ObjNode(name, root);
            treeNode.loadChildren();
            new View(name,treeNode,800,600,-1,-1);
          }
      public static void inspect(String title, String name, Object root) {
            ObjNode treeNode = new ObjNode(name, root);
            treeNode.loadChildren();
            new View(title,treeNode,800,800,-1,-1);
      }
      public static void inspect(String title, String name, Object root, int windowW, int WindowH, int posX, int posY) {
            ObjNode treeNode = new ObjNode(name, root);
            treeNode.loadChildren();
            new View(title,treeNode,windowW,WindowH, posX,posY);
      }

      protected static Inspector getInstance(){
            if (instance == null)
                  instance=new Inspector();
            return instance;
      }
      private Inspector(){}
    }
