package com.nachomoyano04.canchapro.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nachomoyano04.canchapro.MainActivity;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void login(String correo, String password){
        if(!correo.isEmpty() && !password.isEmpty()){
            ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
            api.login(correo, password).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        String token = response.body();
                        ApiCliente.guardarToken(context, token);
                        Intent i = new Intent(context, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }else{
                        try {
                            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    Log.d("ErroLogin", throwable.getMessage());
                }
            });
        }else{
            Toast.makeText(context, "Llene los campos o registrese", Toast.LENGTH_SHORT).show();
        }
    }

    public void recuperarPassword(String correo){
        Intent i = new Intent(context, RecuperarPasswordActivity.class);
        if(!correo.isEmpty()){
            i.putExtra("correo", correo);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void registrarUsuario() {
        Intent i = new Intent(context, RegistrarActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
