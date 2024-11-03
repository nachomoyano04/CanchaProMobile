package com.nachomoyano04.canchapro.ui.canchas;

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

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentListadoBinding;
import com.nachomoyano04.canchapro.models.Cancha;

import java.util.ArrayList;

public class ListadoFragment extends Fragment {

    private FragmentListadoBinding binding;
    private ListadoViewModel vm;

    public static ListadoFragment newInstance() {
        return new ListadoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListadoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ListadoViewModel.class);
        vm.getMListaCanchas().observe(getViewLifecycleOwner(), new Observer<ArrayList<Cancha>>() {
            @Override
            public void onChanged(ArrayList<Cancha> canchas) {
                GridLayoutManager grid = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
                CanchaAdapter adapter = new CanchaAdapter(canchas, inflater);
                binding.listaCanchas.setLayoutManager(grid);
                binding.listaCanchas.setAdapter(adapter);
            }
        });
        vm.llenarLista();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(ListadoViewModel.class);
        // TODO: Use the ViewModel
    }

}