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

import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TurnosPorCanchaViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Turno>> mListaTurnos;
    private Context context;

    public TurnosPorCanchaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Turno>> getMListaTurnos(){
        if(mListaTurnos == null){
            mListaTurnos = new MutableLiveData<>();
        }
        return  mListaTurnos;
    }

    public void llenarLista(Cancha c){
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.disponiblesPorDia(ApiCliente.getToken(context), c.getId(), LocalDateTime.of(LocalDate.now(), LocalTime.MIN)).enqueue(new Callback<ArrayList<Turno>>() {
            @Override
            public void onResponse(Call<ArrayList<Turno>> call, Response<ArrayList<Turno>> response) {
                if(response.isSuccessful()){
                    if(response.code() == 204){
                        Toast.makeText(context, "No hay turnos hoy", Toast.LENGTH_SHORT).show();
                    }else{
                        ArrayList<Turno> t = response.body();
                        mListaTurnos.postValue(t);
                    }
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
            public void onFailure(Call<ArrayList<Turno>> call, Throwable throwable) {
                Log.d("listadoTurnos", throwable.getMessage());
                Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}