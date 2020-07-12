package com.spicycoder.iquit.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

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

    public ArrayList<AddictionUserModel> addNewAddiction(AddictionUserModel addictionUserModel) {
        addictionUserModels.add(addictionUserModel);
        return addictionUserModels;
    }

    public AddictionUserModel getItemAtPosition(int index){
        return addictionUserModels.get(index);
    }

    public AddictionUserModel removeItemAtPosition(int index){
        return addictionUserModels.remove(index);
    }

    public boolean doesAddictionExist(String addictionName){
        for(AddictionUserModel obj:addictionUserModels){
            if(obj.getAddiction().getName().equals(addictionName)){
                return true;
            }
        }
        return false;
    }

}
