package com.nachomoyano04.canchapro.ui.inicio;

import android.app.Application;
import android.content.Context;
import android.opengl.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.models.Usuario;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<ArrayList<Turno>> mListaTurnos;
    private MutableLiveData<Usuario> mUsuario;
    private MutableLiveData<Boolean> mMensajeSinTurno;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Turno>> getMListaTurnos(){
        if(mListaTurnos == null){
            mListaTurnos = new MutableLiveData<>();
        }
        return mListaTurnos;
    }

    public LiveData<Usuario> getMUsuario(){
        if(mUsuario == null){
            mUsuario = new MutableLiveData<>();
        }
        return mUsuario;
    }

    public LiveData<Boolean> getMMensajeSinTurno(){
        if(mMensajeSinTurno == null){
            mMensajeSinTurno = new MutableLiveData<>();
        }
        return mMensajeSinTurno;
    }

    public void llenarLista(){
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.misProximosTurnos(ApiCliente.getToken(context)).enqueue(new Callback<ArrayList<Turno>>() {
            @Override
            public void onResponse(Call<ArrayList<Turno>> call, Response<ArrayList<Turno>> response) {
                if(response.isSuccessful()){
                    if(response.code() == 204){
                        mListaTurnos.postValue(new ArrayList<>());
                        mMensajeSinTurno.setValue(true);
                    }else{
                        mListaTurnos.postValue(response.body());
                        mMensajeSinTurno.setValue(false);
                    }
                }else{
                    if(response.code() != 401){
                        try {
                            Log.d("ProximosTurnoss", response.errorBody().string());
                            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Turno>> call, Throwable throwable) {
                Log.d("ProximosTurnos", throwable.getMessage());
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarDatosUsuario(){
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.getUsuario(ApiCliente.getToken(context)).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    mUsuario.postValue(response.body());
                }else{
                    if(response.code() != 401){
                        try {
                            Log.d("ErrorUsuarioInicio", response.errorBody().string());
                            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                Log.d("UsuarioEnInicio", throwable.getMessage());
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getVisibilidadAPartirDeBoolean(Boolean b){
        if(b != null && b){
            return View.VISIBLE;
        }
        return View.INVISIBLE;
    }
}