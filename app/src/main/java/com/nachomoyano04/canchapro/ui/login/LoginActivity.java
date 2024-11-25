package com.nachomoyano04.canchapro.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.nachomoyano04.canchapro.MainActivity;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginActivityViewModel.class);
        setContentView(binding.getRoot());
        binding.btnLoginIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.login(binding.etCorreoLogin.getText().toString(), binding.etPasswordLogin.getText().toString());
                binding.etCorreoLogin.setText("");
                binding.etPasswordLogin.setText("");
            }
        });
        binding.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.recuperarPassword(binding.etCorreoLogin.getText().toString());
            }
        });
        binding.btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.registrarUsuario();
            }
        });
        Intent i = getIntent();
        String mensaje = i.getStringExtra("mensaje");
        if(mensaje != null){
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }
}