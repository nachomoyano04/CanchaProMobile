package com.nachomoyano04.canchapro.ui.turnos;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaTurnosViewModel extends AndroidViewModel {

    private MutableLiveData<Turno> mTurno;
    private MutableLiveData<LocalDateTime> mFecha;
    private MutableLiveData<ArrayList<String>> mHoraInicio;
    private MutableLiveData<ArrayList<String>> mHoraFin;
    private MutableLiveData<String> mRespuestaAltaYEditar;
    private MutableLiveData<Boolean> mBooleano = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> mSinHorarios;
    private Context context;

    public AltaTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getMRespuestaAltaYEditar(){
        if(mRespuestaAltaYEditar == null){
            mRespuestaAltaYEditar = new MutableLiveData<>();
        }
        return mRespuestaAltaYEditar;
    }

    public LiveData<Turno> getMTurno(){
        if(mTurno == null){
            mTurno = new MutableLiveData<>();
        }
        return mTurno;
    }

    public LiveData<LocalDateTime> getMFecha(){
        if(mFecha == null){
            mFecha = new MutableLiveData<>();
        }
        return mFecha;
    }

    public LiveData<ArrayList<String>> getMHoraInicio(){
        if(mHoraInicio == null){
            mHoraInicio = new MutableLiveData<>();
        }
        return mHoraInicio;
    }

    public LiveData<Boolean> getMSinHorarios(){
        if(mSinHorarios == null){
            mSinHorarios = new MutableLiveData<>();
        }
        return mSinHorarios;
    }

    public LiveData<ArrayList<String>> getMHoraFin(){
        if(mHoraFin == null){
            mHoraFin = new MutableLiveData<>();
        }
        return mHoraFin;
    }

    public void setearMutable(Cancha c, Turno t, Boolean editar){
        if(editar != null){
            mBooleano.setValue(editar);
        }
        if(t != null){
            Log.d("asdasd", t.toString());
            mTurno.postValue(t);
        }else if(c != null){
            Turno turno = new Turno(0, c.getId(), c, 0, null, 0, null, LocalDateTime.now(), LocalDateTime.now(), null, 0, null, 1);
            mTurno.postValue(turno);
        }else{
            Toast.makeText(context, "Todo null", Toast.LENGTH_SHORT).show();
        }
    }

    public void setearMutableFecha(LocalDateTime fecha){
        mFecha.postValue(fecha);
    }

    public void setMutableHoraInicio(LocalDateTime fecha) {
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        Turno t = getMTurno().getValue();
        api.horariosInicio(ApiCliente.getToken(context), t.getCanchaId(), fecha.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), mBooleano.getValue()).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()){
                    if(response.body().size() > 0){
                        mHoraInicio.postValue(response.body());
                        mSinHorarios.setValue(true);
                    }else{
                        mSinHorarios.setValue(false);
                    }
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

    public void setMutableHoraFin(LocalTime horaInicio) {
        Turno t = mTurno.getValue();
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        api.horariosfin(ApiCliente.getToken(context), t.getCanchaId(), mFecha.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), horaInicio.toString(), mBooleano.getValue(), t.getId()).enqueue(new Callback<ArrayList<String>>() {
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


    public void btnGuardar(View view, String fecha, String horaInicio, String horaFin){
        Turno turno = mTurno.getValue();
        ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(context);
        LocalDateTime fechaHoraInicio = LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(horaInicio));
        LocalDateTime fechaHoraFin = LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(horaFin));
        if(mBooleano.getValue()){
            api.editarTurno(ApiCliente.getToken(context), turno.getId(), fechaHoraInicio, fechaHoraFin).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.nav_inicio);
                    }else{
                        if(response.code() != 401){
                            try {
                                Log.d("dasdas", response.errorBody().string());
                                Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Log.d("asdeaujfge", throwable.getMessage());
                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            api.nuevoTurno(ApiCliente.getToken(context), turno.getCanchaId(), fechaHoraInicio, fechaHoraFin, "Transferencia").enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        mRespuestaAltaYEditar.postValue(response.body());
                    }else{
                        if(response.code() != 401){
                            try {
                                Log.d("Error nuevo turno", response.errorBody().string());
                                Toast.makeText(context, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(context, "Error servidor", Toast.LENGTH_SHORT).show();
                    Log.d("Error nuevo turno asdasd ", throwable.getMessage());
                }
            });
        }
    }
    public int getVisibilidadFromBoolean(Boolean valor) {
        if(valor != null && valor){
            return View.VISIBLE;
        }
        return View.INVISIBLE;
    }
}