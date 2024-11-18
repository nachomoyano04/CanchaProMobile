package com.nachomoyano04.canchapro.ui.inicio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioAdapter extends RecyclerView.Adapter<InicioAdapter.ViewHolderInicio> {

    private ArrayList<Turno> turnos = new ArrayList<>();
    private LayoutInflater li;

    public InicioAdapter(ArrayList<Turno> turnos, LayoutInflater li) {
        this.turnos = turnos;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolderInicio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.card_mis_turnos, parent, false);
        return new ViewHolderInicio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInicio holder, int position) {
        Turno t = turnos.get(position);
        holder.fecha.setText(t.getFechaInicio().toLocalDate().toString());
        holder.horaInicio.setText(t.getFechaInicio().toLocalTime().toString());
        holder.horaFin.setText(t.getFechaFin().toLocalTime().toString());
        holder.cancha.setText(t.getCancha().getTipo().getNombre());
        NumberFormat moneda = NumberFormat.getCurrencyInstance();
        holder.precio.setText(moneda.format(t.getPago().getMontoTotal()));
        holder.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(view.getContext());
                LocalDateTime fechaCancelacion = LocalDateTime.now();
                api.getPoliticasDeCancelacion(ApiCliente.getToken(view.getContext()), t.getId(), fechaCancelacion).enqueue(new Callback<ArrayList<String>>() {
                    @Override
                    public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                        if(response.isSuccessful()){
                            AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                                    .setTitle("Politica de cancelación")
                                    .setMessage(response.body().get(0))
                                    .setPositiveButton("Cancelar turno", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            api.cancelarTurno(ApiCliente.getToken(view.getContext()), t.getId(), fechaCancelacion, response.body().get(1)).enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    if(response.isSuccessful()){
                                                        if(response.code() == 200){
                                                            Toast.makeText(view.getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                                            Navigation.findNavController(view).navigate(R.id.nav_inicio);
                                                        }else{
                                                            Toast.makeText(view.getContext(), "Ya no se puede cancelar", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else{
                                                        Toast.makeText(view.getContext(), "Error en respuesta", Toast.LENGTH_SHORT).show();
                                                        try {
                                                            Log.d("aeri4hg", response.errorBody().string());
                                                        } catch (IOException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable throwable) {
                                                    Toast.makeText(view.getContext(), "Error servidor", Toast.LENGTH_SHORT).show();
                                                    Log.d("error2dfas", throwable.getMessage());
                                                }
                                            });
                                        }
                                    }).setNegativeButton("No cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create();
                            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                    Button boton = ((AlertDialog)dialogInterface).getButton(DialogInterface.BUTTON_NEGATIVE);
                                    String textoBoton = (String) boton.getText();
                                    new CountDownTimer(60000, 1000){
                                        @Override
                                        public void onTick(long l) {
                                            boton.setText(String.format(Locale.getDefault(), "%s (%d)",
                                                    textoBoton,
                                                    TimeUnit.MILLISECONDS.toSeconds(l) + 1));
                                        }
                                        @Override
                                        public void onFinish() {
                                            dialogInterface.dismiss();
                                        }
                                    }.start();
                                }
                            });
                            dialog.show();
                        }else{
                            Toast.makeText(view.getContext(), "Error en respuesta", Toast.LENGTH_SHORT).show();
                            try {
                                Log.d("aeri4hg", response.errorBody().string());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<String>> call, Throwable throwable) {
                        Toast.makeText(view.getContext(), "Error servidor", Toast.LENGTH_SHORT).show();
                        Log.d("error2dfas", throwable.getMessage());
                    }
                });
            }
        });
        holder.btnEditarMisTurnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putSerializable("turno", t);
                b.putSerializable("editar", true);
                Navigation.findNavController(view).navigate(R.id.nav_alta_update_turno, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return turnos.size();
    }

    public class ViewHolderInicio extends RecyclerView.ViewHolder {
        private TextView fecha, horaInicio, horaFin, cancha, precio;
        private Button btnCancelar, btnEditarMisTurnos;
        public ViewHolderInicio(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFechaCardMisTurnos);
            horaInicio = itemView.findViewById(R.id.tvHoraInicioCardMisTurnos);
            horaFin = itemView.findViewById(R.id.tvHoraFinCardMisTurnos);
            cancha = itemView.findViewById(R.id.tvCanchaCardMisTurnos);
            precio = itemView.findViewById(R.id.tvPrecioCardMisTurnos);
            btnCancelar = itemView.findViewById(R.id.btnCancelarReservaCardMisTurnos);
            btnEditarMisTurnos = itemView.findViewById(R.id.btnEditarTurnoCardMisTurnos);
        }
    }

}
