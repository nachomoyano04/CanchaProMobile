package com.nachomoyano04.canchapro.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Usuario;
import com.nachomoyano04.canchapro.request.ApiCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<Boolean> mEditable = new MutableLiveData<>(false);
    private MutableLiveData<Integer> mColor = new MutableLiveData<>(R.color.secundario);
    private MutableLiveData<String> mTexto = new MutableLiveData<>("Editar perfil");
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getMUsuario(){
        if(mUsuario == null){
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public LiveData<Boolean> getMEditable(){
        return mEditable;
    }

    public LiveData<Integer> getMColor(){
        return mColor;
    }

    public LiveData<String> getMTexto(){
        return mTexto;
    }

    public void editarAvatar(View view){
        Bundle b = new Bundle();
        b.putSerializable("usuario", mUsuario.getValue());
        Navigation.findNavController(view).navigate(R.id.editarAvatarFragment, b);
    }

    public void cambiarPassword(View view) {
        Navigation.findNavController(view).navigate(R.id.cambiarPasswordFragment);
    }

    public void cancelarEditar() {
        mEditable.setValue(false);
    }

    public void editarPerfil(String dni, String nombre, String apellido, String correo) {
        if(!dni.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty()){
            if(!mEditable.getValue()){
                mEditable.setValue(true);
            }else{
                ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
                api.editar(ApiCliente.getToken(context), dni, nombre, apellido, correo).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Campos editados.", Toast.LENGTH_SHORT).show();
                            mEditable.setValue(false);
                        }else{
                            if(response.code() != 401){
                                Toast.makeText(context, "Error al editar perfil.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            Toast.makeText(context, "Hay campos vacíos", Toast.LENGTH_SHORT).show();
        }
    }

    public void cambiarColor(Boolean b) {
        if(b){
            mColor.setValue(R.color.success);
        }else{
            mColor.setValue(R.color.secundario);
        }
    }

    public void cambiarTexto(Boolean b) {
        if(b){
            mTexto.setValue("Guardar");
        }else{
            mTexto.setValue("Editar Perfil");
        }
    }

    public void getUsuario() {
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.getUsuario(ApiCliente.getToken(context)).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    mUsuario.postValue(response.body());
                }else{
                    if(response.code() != 401){
                        Toast.makeText(context, "Error al traer el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Log.d("Erro server", throwable.getMessage());
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}