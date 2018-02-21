package com.example.kukilemo.managementourschedule;

import io.realm.RealmObject;

/**
 * Created by kukilemo on 18/02/01.
 */

public class Belonging extends RealmObject {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
