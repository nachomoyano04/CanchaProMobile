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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentHistorialTurnosBinding;
import com.nachomoyano04.canchapro.models.Turno;

import java.time.LocalTime;
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
        vm.getMMensaje().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visibilidad = vm.getVisibilidadAPartirDeBoolean(aBoolean);
                binding.tvMensajeErrorFragmentHistorialSin.setVisibility(visibilidad);
            }
        });
        vm.getMSpinnerFiltro().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer numero) {
                vm.llenarLista(numero);
            }
        });
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Completado");
        strings.add("Cancelado");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        binding.spinnerHistorialTurnosFiltro.setAdapter(adapter);
        binding.spinnerHistorialTurnosFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vm.setearFiltro(adapterView.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(HistorialTurnosViewModel.class);
    }

}