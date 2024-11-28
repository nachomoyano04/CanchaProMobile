package com.nachomoyano04.canchapro.ui.turnos;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
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
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialTurnosViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<ArrayList<Turno>> mListaMisTurnos;
    private MutableLiveData<Boolean> mMensaje;
    private MutableLiveData<Integer> mSpinnerFiltro;

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

    public LiveData<Boolean> getMMensaje(){
        if(mMensaje == null){
            mMensaje = new MutableLiveData<>();
        }
        return mMensaje;
    }

    public LiveData<Integer> getMSpinnerFiltro(){
        if(mSpinnerFiltro == null){
            mSpinnerFiltro = new MutableLiveData<>();
        }
        return mSpinnerFiltro;
    }

    public void llenarLista(Integer numero){
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.turnosPorUsuarioYEstado(ApiCliente.getToken(context), numero).enqueue(new Callback<ArrayList<Turno>>() {
            @Override
            public void onResponse(Call<ArrayList<Turno>> call, Response<ArrayList<Turno>> response) {
                if(response.isSuccessful()){
                    if(response.code() == 204){
                        mListaMisTurnos.postValue(new ArrayList<>());
                        mMensaje.setValue(true);
                    }else{
                        ArrayList<Turno> turnoss = response.body();
                        if(turnoss.get(0).getEstado()==2){
                            turnoss.sort(new Comparator<Turno>() {
                                @Override
                                public int compare(Turno turno, Turno t1) {
                                    return t1.getFechaInicio().compareTo(turno.getFechaInicio());
                                }
                            });
                        }
                        mListaMisTurnos.postValue(turnoss);
                        mMensaje.setValue(false);
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

    public int getVisibilidadAPartirDeBoolean(Boolean b){
        if(b != null && b){
            return View.VISIBLE;
        }
        return View.INVISIBLE;
    }

    public void setearFiltro(String estado) {
        if(estado.equalsIgnoreCase("Completado")){
            mSpinnerFiltro.setValue(2);
        }else{
            mSpinnerFiltro.setValue(3);
        }
    }
}