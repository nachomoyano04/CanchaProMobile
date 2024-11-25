package com.nachomoyano04.canchapro.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarPasswordActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje;
    private MutableLiveData<String> mCorreo;
    private Context context;

    public RecuperarPasswordActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getMMensaje(){
        if(mMensaje == null){
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public LiveData<String> getMCorreo(){
        if(mCorreo == null){
            mCorreo = new MutableLiveData<>();
        }
        return mCorreo;
    }

    public void enviarCorreo(String correo){
        if(!correo.isEmpty()){
            ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
            api.recuperarPassword(correo).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        mMensaje.postValue(response.body());
                    }else{
                        try {
                            mMensaje.postValue(response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            mMensaje.postValue("Ingrese un mail por favor");
        }
    }

    public void ponerCorreo(Intent intent) {
        String correo = intent.getStringExtra("correo");
        if(correo != null){
            mCorreo.setValue(correo);
        }
    }
}
