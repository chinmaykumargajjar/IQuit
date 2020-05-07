package com.debugcoder.iquit.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddictionManager {
    ArrayList<AddictionUserModel> addictionUserModels;

    public AddictionManager() {
        addictionUserModels= new ArrayList<>();
    }

    public ArrayList<AddictionUserModel> getAddictionUserModels() {
        return addictionUserModels;
    }

    public void setAddictionUserModels(ArrayList<AddictionUserModel> addictionUserModels) {
        this.addictionUserModels = addictionUserModels;
    }

}
