package com.nachomoyano04.canchapro.ui.canchas;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.request.ApiCliente;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            Context contexto = holder.itemView.getContext();
            ApiCliente.CanchaProService api = ApiCliente.getApiCanchaPro(contexto);
            Cancha c = listaCanchas.get(position);
            holder.titulo.setText(c.getTipo().getNombre());
            Glide.with(holder.itemView)
                    .load(ApiCliente.URLIMAGENCANCHA+c.getImagen())
                    .placeholder(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imagen);
            holder.tipoPiso.setText(c.getTipo().getTipoDePiso());
            holder.capacidad.setText(c.getTipo().getCapacidadTotal()+"");
            NumberFormat moneda = NumberFormat.getCurrencyInstance();
            holder.precio.setText(moneda.format(c.getPrecioPorHora()));
            holder.descripcion.setText(c.getDescripcion());
            holder.porcentaje.setText(c.getPorcentajeCalificacion()+"");
            holder.porcentaje.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0,0);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putSerializable("cancha", c);
                    Navigation.findNavController(view).navigate(R.id.nav_alta_update_turno, b);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaCanchas.size();
    }

    public class CanchaViewHolder extends RecyclerView.ViewHolder {
        private TextView titulo, tipoPiso, capacidad, precio, descripcion, porcentaje;
        private ImageView imagen;

        public CanchaViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tvCardCanchaTitulo);
            tipoPiso = itemView.findViewById(R.id.tvCardCanchaTipoPiso);
            capacidad = itemView.findViewById(R.id.tvCardCanchaCapacidad);
            precio = itemView.findViewById(R.id.tvCardCanchaPrecio);
            descripcion = itemView.findViewById(R.id.tvCardCanchaDescripcion);
            imagen = itemView.findViewById(R.id.ivCardCancha);
            porcentaje = itemView.findViewById(R.id.tvPorcentajeCalificacionCardCancha);
        }
    }
}
