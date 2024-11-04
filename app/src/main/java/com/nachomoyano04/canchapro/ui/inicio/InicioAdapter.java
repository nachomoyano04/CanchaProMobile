package com.nachomoyano04.canchapro.ui.inicio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Turno;

import java.util.ArrayList;

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
        holder.precio.setText("$"+t.getCancha().getPrecioPorHora());
        holder.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Turno cancelado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return turnos.size();
    }

    public class ViewHolderInicio extends RecyclerView.ViewHolder {
        private TextView fecha, horaInicio, horaFin, cancha, precio;
        private Button btnCancelar;
        public ViewHolderInicio(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.tvFechaCardMisTurnos);
            horaInicio = itemView.findViewById(R.id.tvHoraInicioCardMisTurnos);
            horaFin = itemView.findViewById(R.id.tvHoraFinCardMisTurnos);
            cancha = itemView.findViewById(R.id.tvCanchaCardMisTurnos);
            precio = itemView.findViewById(R.id.tvPrecioCardMisTurnos);
            btnCancelar = itemView.findViewById(R.id.btnCancelarReservaCardMisTurnos);
        }
    }

}
