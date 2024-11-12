package com.nachomoyano04.canchapro.ui.perfil;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentPerfilBinding;
import com.nachomoyano04.canchapro.models.Usuario;
import com.nachomoyano04.canchapro.request.ApiCliente;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel vm;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        vm.getMUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario u) {
                binding.etDniPerfil.setText(u.getDni());
                binding.etNombrePerfil.setText(u.getNombre());
                binding.etApellidoPerfil.setText(u.getApellido());
                binding.etCorreoPerfil.setText(u.getCorreo());
                Glide.with(getContext())
                        .load(ApiCliente.URLIMAGENUSUARIO+u.getAvatar())
                        .placeholder(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivAvatarPerfil);
            }
        });
        vm.getMEditable().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                binding.etDniPerfil.setEnabled(b);
                binding.etNombrePerfil.setEnabled(b);
                binding.etApellidoPerfil.setEnabled(b);
                binding.etCorreoPerfil.setEnabled(b);
                binding.btnCambiarPasswordPerfil.setEnabled(!b);
                binding.btnEditarAvatarPerfil.setEnabled(!b);
                binding.btnCancelarEditarPerfil.setVisibility(b.compareTo(!b));
                vm.cambiarColor(b);
                vm.cambiarTexto(b);
            }
        });

        vm.getMTexto().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditarPerfil.setText(s);
            }
        });

        vm.getMColor().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.btnEditarPerfil.setBackgroundColor(getResources().getColor(integer));
            }
        });

        binding.btnEditarAvatarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.editarAvatar(view);
            }
        });
        binding.btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = binding.etDniPerfil.getText().toString();
                String nombre = binding.etNombrePerfil.getText().toString();
                String apellido = binding.etApellidoPerfil.getText().toString();
                String correo = binding.etCorreoPerfil.getText().toString();
                vm.editarPerfil(dni, nombre, apellido, correo);
            }
        });
        binding.btnCambiarPasswordPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.cambiarPassword(view);
            }
        });
        binding.btnCancelarEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.cancelarEditar();
            }
        });
        binding.btnVerHistorialDeTurnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_historial_turnos);
            }
        });
        vm.getUsuario();
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
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

}