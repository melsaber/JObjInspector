/*
 * Copyright (c) 2020 Abojoe Limited. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package test;

import com.abojoe.inspector.Inspector;

public class Main {

    public static void main(String[] args) {
        SampleObject rootObj=new SampleObject("trees");
        Inspector.inspect("Sample Title",rootObj.getName(),rootObj);

    }
}
