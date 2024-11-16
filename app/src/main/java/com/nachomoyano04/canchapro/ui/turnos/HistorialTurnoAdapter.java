package com.nachomoyano04.canchapro.ui.turnos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialTurnoAdapter extends RecyclerView.Adapter<HistorialTurnoAdapter.ViewHolderHistorialTurnoAdapter> {
    private ArrayList<Turno> listadoTurnos = new ArrayList<>();
    private LayoutInflater li;

    public HistorialTurnoAdapter(ArrayList<Turno> listadoTurnos, LayoutInflater li) {
        this.listadoTurnos = listadoTurnos;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolderHistorialTurnoAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = null;
        if(viewType == 2){
            vista = li.inflate(R.layout.card_turno_completado, parent, false);
        }else if(viewType == 3){
            vista = li.inflate(R.layout.card_turno_cancelado, parent, false);
        }
        return new ViewHolderHistorialTurnoAdapter(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistorialTurnoAdapter holder, int position) {
        Turno t = listadoTurnos.get(position);
        int estado = t.getEstado();
        if(estado == 2){
            ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(li.getContext());
            holder.fecha2.setText(t.getFechaInicio().toLocalDate().toString());
            holder.horaInicio2.setText(t.getFechaInicio().toLocalTime().toString());
            holder.horaFin2.setText(t.getFechaFin().toLocalTime().toString());
            holder.cancha2.setText(t.getCancha().getTipo().getNombre());
            holder.precio2.setText("$"+t.getPago().getMontoTotal());
            holder.btnEditarYGuardarComentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.btnEditarYGuardarComentario.getText().equals("Editar")){
                        holder.btnEditarYGuardarComentario.setText("Guardar");
                        holder.btnCancelarEditarComentario.setVisibility(view.VISIBLE);
                        holder.btnEditarYGuardarComentario.setBackgroundColor(li.getContext().getResources().getColor(R.color.success));
                        holder.etComentarioTurnoCompletado.setEnabled(true);
                        holder.ratingCardTurnoCompletado.setIsIndicator(false);
                    }else{
                        int calificacion = (int) holder.ratingCardTurnoCompletado.getRating();
                        String comentario = holder.etComentarioTurnoCompletado.getText().toString();
                        if(!comentario.isEmpty() && calificacion != t.getCalificacion() || !comentario.equals(t.getComentario())){
                            api.nuevoComentario(ApiCliente.getToken(li.getContext()), t.getId(), calificacion, comentario).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(li.getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(response.code() != 401){
                                            try {
                                                Toast.makeText(li.getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                                Log.d("ErrorComnetsario", response.errorBody().string());
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable throwable) {
                                    Toast.makeText(li.getContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                                    Log.d("Comentariosd213", throwable.getMessage());
                                }
                            });
                        }else{
                            Toast.makeText(li.getContext(), "Debe modificar algo", Toast.LENGTH_SHORT).show();
                        }
                        holder.btnEditarYGuardarComentario.setText("Editar");
                        holder.btnEditarYGuardarComentario.setBackgroundColor(li.getContext().getResources().getColor(R.color.secundario));
                        holder.ratingCardTurnoCompletado.setIsIndicator(true);
                        holder.etComentarioTurnoCompletado.setEnabled(false);
                        holder.btnCancelarEditarComentario.setVisibility(view.INVISIBLE);
                    }
                }
            });
            holder.btnCancelarEditarComentario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.ratingCardTurnoCompletado.setIsIndicator(true);
                    holder.btnEditarYGuardarComentario.setText("Editar");
                    holder.btnEditarYGuardarComentario.setBackgroundColor(li.getContext().getResources().getColor(R.color.secundario));
                    holder.btnCancelarEditarComentario.setVisibility(view.INVISIBLE);
                    holder.etComentarioTurnoCompletado.setEnabled(false);
                    holder.ratingCardTurnoCompletado.setRating(t.getCalificacion());
                    holder.etComentarioTurnoCompletado.setText(t.getComentario());
                }
            });
            if(t.getComentario() == null){
                holder.ratingCardTurnoCompletado.setRating(0);
                holder.etComentarioTurnoCompletado.setText("");
            }else{
                holder.ratingCardTurnoCompletado.setRating(t.getCalificacion());
                holder.etComentarioTurnoCompletado.setText(t.getComentario());
            }
        }else if(estado == 3){
            holder.fecha3.setText(t.getFechaInicio().toLocalDate().toString());
            holder.horaInicio3.setText(t.getFechaInicio().toLocalTime().toString());
            holder.horaFin3.setText(t.getFechaFin().toLocalTime().toString());
            holder.cancha3.setText(t.getCancha().getTipo().getNombre());
            holder.precio3.setText("$"+t.getPago().getMontoTotal());
        }
    }

    @Override
    public int getItemCount() {
        return listadoTurnos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listadoTurnos.get(position).getEstado();
    }

    public class ViewHolderHistorialTurnoAdapter extends RecyclerView.ViewHolder{

        private TextView fecha2, horaInicio2, horaFin2, cancha2, precio2, fecha3, horaInicio3, horaFin3, cancha3, precio3;
        private Button btnEditarYGuardarComentario, btnCancelarEditarComentario;
        private RatingBar ratingCardTurnoCompletado;
        private EditText etComentarioTurnoCompletado;

        public ViewHolderHistorialTurnoAdapter(@NonNull View itemView) {
            super(itemView);
            fecha2 = itemView.findViewById(R.id.tvFechaCardMisTurnosCompletados);
            horaInicio2 = itemView.findViewById(R.id.tvHoraInicioCardMisTurnosCompletados);
            horaFin2 = itemView.findViewById(R.id.tvHoraFinCardMisTurnosCompletados);
            cancha2 = itemView.findViewById(R.id.tvCanchaCardMisTurnosCompletados);
            precio2 = itemView.findViewById(R.id.tvPrecioCardMisTurnosCompletados);
            btnCancelarEditarComentario = itemView.findViewById(R.id.btnCancelarEditarReseniaCardCompletado);
            btnEditarYGuardarComentario = itemView.findViewById(R.id.btnEditarYGuardarReseniaCardCompletado);
            ratingCardTurnoCompletado = itemView.findViewById(R.id.ratingCardTurnoCompletado);
            etComentarioTurnoCompletado = itemView.findViewById(R.id.etComentarioCardTurnoCompletado);

            fecha3 =  itemView.findViewById(R.id.tvFechaCardMisTurnosCancelados);
            horaInicio3 = itemView.findViewById(R.id.tvHoraInicioCardMisTurnosCancelados);
            horaFin3 = itemView.findViewById(R.id.tvHoraFinCardMisTurnosCancelados);
            cancha3 = itemView.findViewById(R.id.tvCanchaCardMisTurnosCancelados);
            precio3 = itemView.findViewById(R.id.tvPrecioCardMisTurnosCancelados);
        }
    }

}
