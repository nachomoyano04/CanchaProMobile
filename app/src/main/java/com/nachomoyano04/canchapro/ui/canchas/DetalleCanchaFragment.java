package com.nachomoyano04.canchapro.ui.canchas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentDetalleCanchaBinding;
import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.request.ApiCliente;

public class DetalleCanchaFragment extends Fragment {
    private FragmentDetalleCanchaBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleCanchaBinding.inflate(getLayoutInflater());
        Bundle b = getArguments();
        Cancha cancha = (Cancha) b.getSerializable("cancha");
        double porcentaje = cancha.getPorcentajeCalificacion();
        binding.tvNombreDetalleCancha.setText(cancha.getTipo().getNombre());
        Glide.with(getContext())
                .load(ApiCliente.URLIMAGENCANCHA+cancha.getImagen())
                .placeholder(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageView);
        binding.tvPrecioPorHoraDetalleCancha.setText("$"+cancha.getPrecioPorHora());
        binding.tvCapacidadDetalleCancha.setText(cancha.getTipo().getCapacidadTotal()+"");
        binding.tvTipoDePisoDetalleCancha.setText(cancha.getTipo().getTipoDePiso());
        binding.tvPorcentajeCalificacionDetalleCancha.setText(porcentaje+"");
        binding.tvPorcentajeCalificacionDetalleCancha.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0,0);
        binding.descripcionDetallesCancha.setText(cancha.getDescripcion());
        return binding.getRoot();
    }
}