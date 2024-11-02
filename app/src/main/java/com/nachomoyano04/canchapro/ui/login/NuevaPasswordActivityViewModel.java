package com.nachomoyano04.canchapro.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaPasswordActivityViewModel extends AndroidViewModel {

    private Context context;

    public NuevaPasswordActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void nuevaPassword(String token, String nuevaPassword, String repetida){
        if(!nuevaPassword.isEmpty() && !repetida.isEmpty()){
            if(nuevaPassword.equals(repetida)){
                ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
                api.nuevaPassword(token, nuevaPassword).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(context, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }else{
                            if(response.code() != 401){
                                try {
                                    Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "Las passwords no coinciden...", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Por favor llene todos los campos...", Toast.LENGTH_SHORT).show();
        }
    }
}
