package com.nachomoyano04.canchapro.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.ActivityRegistrarBinding;
import com.nachomoyano04.canchapro.models.Usuario;

public class RegistrarActivity extends AppCompatActivity {

    private RegistrarActivityViewModel vm;
    private ActivityRegistrarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(RegistrarActivityViewModel.class);
        binding = ActivityRegistrarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnGuardarRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = binding.etDniRegistrarUsuario.getText().toString();
                String nombre = binding.etNombreRegistrarUsuario.getText().toString();
                String telefono = binding.etTelefonoRegistrarUsuario.getText().toString();
                String apellido = binding.etApellidoRegistrarUsuario.getText().toString();
                String correo = binding.etCorreoRegistrarPassword.getText().toString();
                String password = binding.etPasswordRegistrarUsuario.getText().toString();
                String repetirPassword = binding.etRepetirPasswordRegistrarUsuario.getText().toString();
                vm.guardarRegistroUsuario(new Usuario(dni, nombre, apellido, telefono, correo, password, repetirPassword));
            }
        });
    }
}