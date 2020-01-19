/*
 * Copyright (c) 2020 Abojoe Limited. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package test;

import java.util.*;

public class SampleObject {
    public static int numberofObjects;
    private String name;
    private Map<String,SampleObject> mapObjects;
    private List<SampleObject> listObjects;
    public SampleObject(String name) {
        this.name=name;
        mapObjects=new Hashtable<>();
        listObjects=new ArrayList<>();
        numberofObjects++;
        if (numberofObjects<1000)
            for (int i=1;i<11;i++){
                mapObjects.put("MapSubObject" + i, new SampleObject("Sub" + i));
                listObjects.add( new SampleObject("ListSubObject" + i));
            }

    }
    public String getName(){
        return this.name;
    }
    @Override
    public String toString() {
        return name;
    }
}
