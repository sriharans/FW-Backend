package assignment.fw.main;

import com.assignment.fw.impl.DataStore;

public class AssignmentMain {
public static void main(String[] args) {
    DataStore ds  = new DataStore();
    System.out.println(ds.insertData("Value08", "Fist data of the value"));
    System.out.println(ds.insertData("Value09", "Second data of the value"));
    System.out.println(ds.insertData("Value10", "Third data of the value"));
    System.out.println(ds.getData("Value07s"));
}
}
