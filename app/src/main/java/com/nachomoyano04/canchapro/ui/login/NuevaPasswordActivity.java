package com.nachomoyano04.canchapro.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.ActivityNuevaPasswordBinding;

public class NuevaPasswordActivity extends AppCompatActivity {

    private ActivityNuevaPasswordBinding binding;
    private NuevaPasswordActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNuevaPasswordBinding.inflate(getLayoutInflater());
        vm = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(NuevaPasswordActivityViewModel.class);
        setContentView(binding.getRoot());
        binding.btnGuardarRecuperoDePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                Uri data = i.getData();
                String token = data.getQueryParameter("access_token");
                vm.nuevaPassword("Bearer "+token, binding.etPasswordRecuperarPassword.getText().toString(), binding.etRepetirPasswordRecuperar.getText().toString());
            }
        });
    }
}