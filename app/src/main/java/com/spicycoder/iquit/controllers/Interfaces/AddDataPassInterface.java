package com.spicycoder.iquit.controllers.Interfaces;

import com.spicycoder.iquit.models.AddictionUserModel;

public interface AddDataPassInterface {
    public void passData(AddictionUserModel data);

    public void goToViewFragment(int index);

    public void goToEmergencyFragment(int index);
}
