package com.nachomoyano04.canchapro.ui.turnos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentHistorialTurnosBinding;
import com.nachomoyano04.canchapro.models.Turno;

import java.util.ArrayList;

public class HistorialTurnosFragment extends Fragment {

    private FragmentHistorialTurnosBinding binding;
    private HistorialTurnosViewModel vm;

    public static HistorialTurnosFragment newInstance() {
        return new HistorialTurnosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(HistorialTurnosViewModel.class);
        binding = FragmentHistorialTurnosBinding.inflate(inflater, container, false);
        vm.getMListaMisTurnos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Turno>>() {
            @Override
            public void onChanged(ArrayList<Turno> turnos) {
                GridLayoutManager grid = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
                HistorialTurnoAdapter adapter = new HistorialTurnoAdapter(turnos, inflater);
                binding.listaHistorialTurnos.setLayoutManager(grid);
                binding.listaHistorialTurnos.setAdapter(adapter);
            }
        });
        vm.llenarLista();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(HistorialTurnosViewModel.class);
    }

}