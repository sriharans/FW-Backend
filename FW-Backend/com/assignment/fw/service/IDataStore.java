package com.assignment.fw.service;

public interface IDataStore {
    
    void addData(String key,Object obj);
    String readData(String key);
    void deleteData(String key);

}
