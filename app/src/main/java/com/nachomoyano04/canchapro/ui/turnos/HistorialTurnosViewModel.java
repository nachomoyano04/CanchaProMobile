package com.nachomoyano04.canchapro.ui.turnos;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialTurnosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<ArrayList<Turno>> mListaMisTurnos;

    public HistorialTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Turno>> getMListaMisTurnos(){
        if(mListaMisTurnos == null){
            mListaMisTurnos = new MutableLiveData<>();
        }
        return mListaMisTurnos;
    }

    public void llenarLista(){
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.turnosCompletadosPorUsuario(ApiCliente.getToken(context)).enqueue(new Callback<ArrayList<Turno>>() {
            @Override
            public void onResponse(Call<ArrayList<Turno>> call, Response<ArrayList<Turno>> response) {
                if(response.isSuccessful()){
                    if(response.code() == 204){
                        mListaMisTurnos.postValue(new ArrayList<>());
                    }else{
                        mListaMisTurnos.postValue(response.body());
                    }
                }else{
                    if(response.code() != 401){
                        try {
                            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            Log.d("ERroasda", response.errorBody().string());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Turno>> call, Throwable throwable) {
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
                Log.d("errorTurnsoHistorial", throwable.getMessage());
            }
        });
    }

}