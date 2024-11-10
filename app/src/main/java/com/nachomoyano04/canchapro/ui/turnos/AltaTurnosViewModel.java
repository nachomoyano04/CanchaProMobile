package com.nachomoyano04.canchapro.ui.turnos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.models.Horarios;
import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaTurnosViewModel extends AndroidViewModel {

    private MutableLiveData<Turno> mTurno;
    private MutableLiveData<Horarios> mHorarios;
    private MutableLiveData<ArrayList<String>> mHoraInicio;
    private MutableLiveData<ArrayList<String>> mHoraFin;
    private Context context;

    public AltaTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Turno> getMTurno(){
        if(mTurno == null){
            mTurno = new MutableLiveData<>();
        }
        return mTurno;
    }

    public LiveData<Horarios> getMHorarios(){
        if(mHorarios == null){
            mHorarios = new MutableLiveData<>();
        }
        return mHorarios;
    }

    public LiveData<ArrayList<String>> getMHoraInicio(){
        if(mHoraInicio == null){
            mHoraInicio = new MutableLiveData<>();
        }
        return mHoraInicio;
    }

    public LiveData<ArrayList<String>> getMHoraFin(){
        if(mHoraFin == null){
            mHoraFin = new MutableLiveData<>();
        }
        return mHoraFin;
    }

    public void setearMutable(Cancha c, Turno t){
        if(t != null){
            mTurno.postValue(t);
        }else{
            Turno turno = new Turno(0, c.getId(), c, 0, null, 0, null, LocalDateTime.now(), LocalDateTime.now(), null, 0, null, 1);
            mTurno.postValue(turno);
        }
    }

    public void getHorariosDisponibles(Turno t) {
        if(t != null){
            ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
            api.horariosPorCanchaYDia(ApiCliente.getToken(context), t.getCanchaId(), t.getFechaInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).enqueue(new Callback<Horarios>() {
                @Override
                public void onResponse(Call<Horarios> call, Response<Horarios> response) {
                    if(response.isSuccessful()){
                        mHorarios.postValue(response.body());
                    }else{
                        if(response.code() != 401){
                            try {
                                Log.d("ErrorHorariosDiaYCancha", response.errorBody().string());
                                Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Horarios> call, Throwable throwable) {
                    Log.d("errorTasda", throwable.getMessage());
                    Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void setMutableHoraInicio(Turno t, Horarios horarios) {
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.horariosInicio(ApiCliente.getToken(context), t.getCanchaId(), t.getFechaInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), horarios.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm:ss")), horarios.getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()){
                    mHoraInicio.postValue(response.body());
                }else{
                    if(response.code() != 401){
                        try {
                            Log.d("tagsads", response.errorBody().string());
                            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable throwable) {
                Log.d("throwabelasda", throwable.getMessage());
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setMutableHoraFin(Turno t, LocalTime horaInicio) {
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.horariosfin(ApiCliente.getToken(context), t.getCanchaId(), t.getFechaInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), horaInicio.format(DateTimeFormatter.ofPattern("HH:mm:ss")), mHorarios.getValue().getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()){
                    mHoraFin.postValue(response.body());
                }else{
                    if(response.code() != 401){
                        try {
                            Log.d("tagsads", response.errorBody().string());
                            Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable throwable) {
                Log.d("throwabelasda", throwable.getMessage());
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}