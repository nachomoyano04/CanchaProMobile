package com.nachomoyano04.canchapro.ui.turnos;

import android.app.AlertDialog;
import android.util.Log;
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

public class TurnoAdapter extends RecyclerView.Adapter<TurnoAdapter.ViewHolderTurno> {

    private LayoutInflater li;
    private ArrayList<Turno> turnos = new ArrayList<>();

    public TurnoAdapter(LayoutInflater li, ArrayList<Turno> turnos) {
        this.li = li;
        this.turnos = turnos;
    }

    @NonNull
    @Override
    public ViewHolderTurno onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.card_turno, parent, false);
        return new ViewHolderTurno(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTurno holder, int position) {
        Turno t = turnos.get(position);
        holder.tvHoraInicio.setText(t.getFechaInicio().toLocalTime().toString());
        holder.tvHoraFin.setText(t.getFechaFin().toLocalTime().toString());
        holder.btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Turno reservado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return turnos.size();
    }

    public class ViewHolderTurno extends RecyclerView.ViewHolder{
        private TextView tvHoraInicio, tvHoraFin;
        private Button btnReservar;
        public ViewHolderTurno(@NonNull View itemView) {
            super(itemView);
            tvHoraInicio = itemView.findViewById(R.id.tvFechaInicioCardTurno);
            tvHoraFin = itemView.findViewById(R.id.tvHastaCardTurno);
            btnReservar = itemView.findViewById(R.id.btnReservarCardTurno);
        }
    }
}
