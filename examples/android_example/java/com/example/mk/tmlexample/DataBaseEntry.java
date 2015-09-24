package com.example.mk.tmlexample;

// class for saving and loading the rows from the database
public class DataBaseEntry{

    int id;
    String title;
    String body;

    public DataBaseEntry(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    //needed for the ListAdapter, ArrayAdapter calls this method for displaying the list
    public String toString() {
        return title;
    }

}
