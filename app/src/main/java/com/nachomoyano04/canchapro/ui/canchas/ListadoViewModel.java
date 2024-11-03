package com.nachomoyano04.canchapro.ui.canchas;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoViewModel extends AndroidViewModel {

    private Context context;
    private MutableLiveData<ArrayList<Cancha>> mListaCanchas;

    public ListadoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Cancha>> getMListaCanchas(){
        if(mListaCanchas == null){
            mListaCanchas = new MutableLiveData<>();
        }
        return mListaCanchas;
    }

    public void llenarLista(){
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.getCanchas(ApiCliente.getToken(context)).enqueue(new Callback<ArrayList<Cancha>>() {
            @Override
            public void onResponse(Call<ArrayList<Cancha>> call, Response<ArrayList<Cancha>> response) {
                if(response.isSuccessful()){
                    ArrayList<Cancha> canchas = response.body();
                    if(!canchas.isEmpty()){
                        mListaCanchas.postValue(response.body());
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
            public void onFailure(Call<ArrayList<Cancha>> call, Throwable throwable) {
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}