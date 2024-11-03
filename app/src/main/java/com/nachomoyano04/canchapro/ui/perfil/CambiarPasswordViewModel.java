package com.nachomoyano04.canchapro.ui.perfil;

import android.app.Application;
import android.content.Context;
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

public class CambiarPasswordViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<String> mMensaje;

    public CambiarPasswordViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getMMensaje(){
        if(mMensaje == null){
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public void cambiarPassword(String actual, String nueva, String repetida){
        if(!actual.isEmpty() && !nueva.isEmpty() && !repetida.isEmpty()){
            if(nueva.equals(repetida)){
                ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
                api.cambiarPassword(ApiCliente.getToken(context), actual, nueva).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            mMensaje.postValue(response.body());
                        }else{
                            if(response.code() != 401){
                                try {
                                    mMensaje.postValue(response.errorBody().string());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mMensaje.setValue("Las passwords no coinciden.");
            }
        }else{
            mMensaje.setValue("Debe llenar todos los campos");
        }
    }
}