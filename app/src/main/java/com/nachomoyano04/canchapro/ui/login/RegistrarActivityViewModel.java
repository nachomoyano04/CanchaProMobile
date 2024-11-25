package com.nachomoyano04.canchapro.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nachomoyano04.canchapro.models.Usuario;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarActivityViewModel extends AndroidViewModel {

    private Context context;

    public RegistrarActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void guardarRegistroUsuario(Usuario u){
        if(u.camposLlenos()){
            if(u.passwordsCoinciden()){
                ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
                api.registrar(u.getDni(), u.getNombre(), u.getApellido(), u.getCorreo(), u.getPassword()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(context, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }else{
                            if(response.code() == 400){
                                try {
                                    Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Bad Request, but could not read error body", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        Log.d("ErrorRegistrarUsuario", throwable.getMessage());
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(context, "Las passwords deben coincidir", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
