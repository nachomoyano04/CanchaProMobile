package com.nachomoyano04.canchapro.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LoginActivityViewModel extends AndroidViewModel {

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void recuperarPassword(){
        //iniciar otra activity para que ingrese el mail donde quiere iniciar la recuperación
    }

    public void login(String correo, String password){
        if(!correo.isEmpty() && !correo.isEmpty()){

        }
    }
}
