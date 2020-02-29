package assignment.fw.main;

import assignment.fw.datastore.DataStore;

import java.util.concurrent.TimeUnit;

public class AssignmentMain {
public static void main(String[] args) {
    DataStore ds  = new DataStore();
  System.out.println(ds.insertData("Value08", "Fist data of the value",2));
    System.out.println(ds.insertData("Value08", "Fist data of the value",2));
    System.out.println(ds.insertData("Value10", "Third data of the value"));

    try {
        TimeUnit.SECONDS.sleep(6);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println(ds.getData("Value08"));
    System.out.println(ds.deleteData("Value10"));
    System.out.println(ds.getData("Value10"));
  /*  System.out.println(ds.getData("Value09"));
    System.out.println(ds.deleteData("Value07s"));*/
}
}
