package com.nachomoyano04.canchapro.ui.inicio;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentInicioBinding;
import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.models.Usuario;

import java.util.ArrayList;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioViewModel vm;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        vm.getMListaTurnos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Turno>>() {
            @Override
            public void onChanged(ArrayList<Turno> turnos) {
                GridLayoutManager grid = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
                InicioAdapter adapter = new InicioAdapter(turnos, inflater);
                binding.listadoMisTurnos.setAdapter(adapter);
                binding.listadoMisTurnos.setLayoutManager(grid);
                binding.tvProximosTurnosFragmentInicio.setText(turnos.size()+"");

            }
        });
        vm.getMUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.tvCorreoFragmentInicio.setText(usuario.getCorreo());
                binding.tvNYAFragmentInicio.setText(usuario.nombreYApellido());
                Glide.with(getContext())
                        .load("http://192.168.1.7:5021/img/usuario/"+usuario.getAvatar())
                        .placeholder(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivAvatarFragmentInicio);
            }
        });
        vm.llenarLista();
        vm.cargarDatosUsuario();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
    }

}