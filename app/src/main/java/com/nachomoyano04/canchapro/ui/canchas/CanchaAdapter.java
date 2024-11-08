package com.nachomoyano04.canchapro.ui.canchas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Cancha;

import java.util.ArrayList;

public class CanchaAdapter extends RecyclerView.Adapter<CanchaAdapter.CanchaViewHolder> {
    private ArrayList<Cancha> listaCanchas = new ArrayList<Cancha>();
    private LayoutInflater inflater;

    public CanchaAdapter(ArrayList<Cancha> listaCanchas, LayoutInflater inflater) {
        this.listaCanchas = listaCanchas;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public CanchaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_cancha, parent, false);
        return new CanchaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanchaViewHolder holder, int position) {
        if(getItemCount() > 0){
            Cancha c = listaCanchas.get(position);
            holder.titulo.setText(c.getTipo().getNombre());
            Glide.with(holder.itemView)
                    .load("http://192.168.1.7:5021/img/usuario/"+c.getImagen())
                    .placeholder(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imagen);
            holder.tipoPiso.setText(c.getTipo().getTipoDePiso());
            holder.capacidad.setText(c.getTipo().getCapacidadTotal()+"");
            holder.precio.setText("$"+c.getPrecioPorHora());
            holder.descripcion.setText(c.getDescripcion());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable("cancha", c);
                    Navigation.findNavController(view).navigate(R.id.listadoTurnosFragment, b);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaCanchas.size();
    }

    public class CanchaViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo, tipoPiso, capacidad, precio, descripcion;
        private ImageView imagen;

        public CanchaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tvCardCanchaTitulo);
            tipoPiso = itemView.findViewById(R.id.tvCardCanchaTipoPiso);
            capacidad = itemView.findViewById(R.id.tvCardCanchaCapacidad);
            precio = itemView.findViewById(R.id.tvCardCanchaPrecio);
            descripcion = itemView.findViewById(R.id.tvCardCanchaDescripcion);
            imagen = itemView.findViewById(R.id.ivCardCancha);
        }
    }
}
