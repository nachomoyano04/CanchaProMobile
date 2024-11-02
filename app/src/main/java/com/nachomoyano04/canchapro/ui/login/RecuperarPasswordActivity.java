package com.nachomoyano04.canchapro.ui.login;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.ActivityRecuperarPasswordBinding;

public class RecuperarPasswordActivity extends AppCompatActivity {

    private ActivityRecuperarPasswordBinding binding;
    private RecuperarPasswordActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarPasswordBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(RecuperarPasswordActivityViewModel.class);
        setContentView(binding.getRoot());
        vm.getMMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensajeErrorRecuperaPassword.setText(s);
            }
        });
        binding.btnEnviarMailRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.enviarCorreo(binding.etCorreoRecuperacion.getText().toString());
            }
        });
    }
}