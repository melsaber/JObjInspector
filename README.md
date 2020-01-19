# JObjInspector

Java Object Viewer Inspection utility


Usage:

import com.abojoe.inspector.Inspector;

public class Main {

    public static void main(String[] args) {
        SampleObject rootObj=new SampleObject("trees");
        Inspector.inspect("Sample Title",rootObj.getName(),rootObj);

    }
}
