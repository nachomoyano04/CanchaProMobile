package com.nachomoyano04.canchapro.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RecuperarPasswordActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje;

    public RecuperarPasswordActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMMensaje(){
        if(mMensaje == null){
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public void enviarMail(String mail){
        if(!mail.isEmpty()){

        }else{
            mMensaje.postValue("Ingrese un mail por favor");
        }
    }
}
