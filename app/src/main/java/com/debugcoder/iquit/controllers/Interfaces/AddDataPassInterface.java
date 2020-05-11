package com.debugcoder.iquit.controllers.Interfaces;

import com.debugcoder.iquit.models.AddictionUserModel;

public interface AddDataPassInterface {
    public void passData(AddictionUserModel data);

    public void goToViewFragment(int index);

    public void goToEmergencyFragment(int index);
}
