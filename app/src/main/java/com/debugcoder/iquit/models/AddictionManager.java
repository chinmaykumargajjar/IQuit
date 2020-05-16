package com.debugcoder.iquit.models;

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

    public boolean doesAddictionExist(String addictionName){
        for(AddictionUserModel obj:addictionUserModels){
            if(obj.getAddiction().getName().equals(addictionName)){
                return true;
            }
        }
        return false;
    }
}
