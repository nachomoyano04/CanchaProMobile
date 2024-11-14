package com.nachomoyano04.canchapro.ui.inicio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import java.util.ArrayList;

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
        View view = null;
        if(viewType == 1){
            view = li.inflate(R.layout.card_mis_turnos, parent, false);
        }else{
            view = li.inflate(R.layout.card_turno_cancelado, parent, false);
        }
        return new ViewHolderInicio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInicio holder, int position) {
        Turno t = turnos.get(position);
        if(t.getEstado() == 1){
            holder.fecha.setText(t.getFechaInicio().toLocalDate().toString());
            holder.horaInicio.setText(t.getFechaInicio().toLocalTime().toString());
            holder.horaFin.setText(t.getFechaFin().toLocalTime().toString());
            holder.cancha.setText(t.getCancha().getTipo().getNombre());
            holder.precio.setText("$"+t.getCancha().getPrecioPorHora());
            holder.btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Cancelar turno")
                            .setMessage("Está seguro que desea cancelar el turno?")
                            .setPositiveButton("Sí, cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(view.getContext());
                                    api.cancelarTurno(ApiCliente.getToken(view.getContext()), t.getId()).enqueue(new Callback<String>() {
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
                                                if(response.code() != 401){
                                                    try {
                                                        Log.d("errorCancelarTurno", response.errorBody().string());
                                                        Toast.makeText(view.getContext(), "Bad request", Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable throwable) {
                                            Log.d("asfasfas", throwable.getMessage());
                                            Toast.makeText(view.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
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
        }else{
            holder.fecha3.setText(t.getFechaInicio().toLocalDate().toString());
            holder.horaInicio3.setText(t.getFechaInicio().toLocalTime().toString());
            holder.horaFin3.setText(t.getFechaFin().toLocalTime().toString());
            holder.cancha3.setText(t.getCancha().getTipo().getNombre());
            holder.precio3.setText("$"+t.getPago().getMontoTotal());
            holder.btnRecuperar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Recuperando turno", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return turnos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return turnos.get(position).getEstado();
    }

    public class ViewHolderInicio extends RecyclerView.ViewHolder {
        private TextView fecha, horaInicio, horaFin, cancha, precio, fecha3, horaInicio3, horaFin3, cancha3, precio3;
        private Button btnCancelar, btnEditarMisTurnos, btnRecuperar;
        public ViewHolderInicio(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFechaCardMisTurnos);
            horaInicio = itemView.findViewById(R.id.tvHoraInicioCardMisTurnos);
            horaFin = itemView.findViewById(R.id.tvHoraFinCardMisTurnos);
            cancha = itemView.findViewById(R.id.tvCanchaCardMisTurnos);
            precio = itemView.findViewById(R.id.tvPrecioCardMisTurnos);
            btnCancelar = itemView.findViewById(R.id.btnCancelarReservaCardMisTurnos);
            btnEditarMisTurnos = itemView.findViewById(R.id.btnEditarTurnoCardMisTurnos);
            fecha3 = itemView.findViewById(R.id.tvFechaCardMisTurnosCancelados);
            horaInicio3 = itemView.findViewById(R.id.tvHoraInicioCardMisTurnosCancelados);
            horaFin3 = itemView.findViewById(R.id.tvHoraFinCardMisTurnosCancelados);
            cancha3 = itemView.findViewById(R.id.tvCanchaCardMisTurnosCancelados);
            precio3 = itemView.findViewById(R.id.tvPrecioCardMisTurnosCancelados);
            btnRecuperar = itemView.findViewById(R.id.btnRecuperarCardTurnoCancelado);
        }
    }

}
