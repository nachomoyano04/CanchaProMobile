package com.nachomoyano04.canchapro.ui.turnos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentTurnosPorCanchaBinding;
import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.models.Turno;

import java.util.ArrayList;

public class TurnosPorCanchaFragment extends Fragment {

    private FragmentTurnosPorCanchaBinding binding;
    private TurnosPorCanchaViewModel vm;

    public static TurnosPorCanchaFragment newInstance() {
        return new TurnosPorCanchaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(TurnosPorCanchaViewModel.class);
        binding = FragmentTurnosPorCanchaBinding.inflate(inflater, container, false);
        Bundle b = getArguments();
        Cancha c = (Cancha) b.getSerializable("cancha");
        vm.getMListaTurnos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Turno>>() {
            @Override
            public void onChanged(ArrayList<Turno> turnos) {
                GridLayoutManager grid = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
                TurnoAdapter adapter = new TurnoAdapter(inflater, turnos);
                binding.listaTurnos.setLayoutManager(grid);
                binding.listaTurnos.setAdapter(adapter);
                binding.tvTituloCanchaListadoTurnos.setText(c.getTipo().getNombre());
            }
        });
        vm.llenarLista(c);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(TurnosPorCanchaViewModel.class);
        // TODO: Use the ViewModel
    }

}