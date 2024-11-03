package com.nachomoyano04.canchapro.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentCambiarPasswordBinding;

public class CambiarPasswordFragment extends Fragment {
    private FragmentCambiarPasswordBinding binding;
    private CambiarPasswordViewModel vm;

    public static CambiarPasswordFragment newInstance() {
        return new CambiarPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(CambiarPasswordViewModel.class);
        binding = FragmentCambiarPasswordBinding.inflate(inflater, container, false);
        vm.getMMensaje().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeErrorCambiarPassword.setText(s);
            }
        });
        binding.btnGuardarCambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.cambiarPassword(binding.etPassActualCambiarPass.getText().toString(), binding.etNuevaPassCmbiarPass.getText().toString(), binding.etRepetirPassCambiarPass.getText().toString());
                binding.etPassActualCambiarPass.setText("");
                binding.etNuevaPassCmbiarPass.setText("");
                binding.etRepetirPassCambiarPass.setText("");
            }
        });
        return binding.getRoot();
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(CambiarPasswordViewModel.class);
        // TODO: Use the ViewModel
    }

}