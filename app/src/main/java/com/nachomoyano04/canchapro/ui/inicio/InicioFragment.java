package com.nachomoyano04.canchapro.ui.inicio;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nachomoyano04.canchapro.MainActivity;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentInicioBinding;
import com.nachomoyano04.canchapro.models.Turno;
import com.nachomoyano04.canchapro.models.Usuario;
import com.nachomoyano04.canchapro.request.ApiCliente;
import com.nachomoyano04.canchapro.ui.login.LoginActivity;

import java.util.ArrayList;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioViewModel vm;

    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        vm.getMListaTurnos().observe(getViewLifecycleOwner(), new Observer<ArrayList<Turno>>() {
            @Override
            public void onChanged(ArrayList<Turno> turnos) {
                GridLayoutManager grid = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
                InicioAdapter adapter = new InicioAdapter(turnos, inflater);
                binding.listadoMisTurnos.setAdapter(adapter);
                binding.listadoMisTurnos.setLayoutManager(grid);
                binding.tvProximosTurnosFragmentInicio.setText(turnos.size()+"");

            }
        });
        vm.getMUsuario().observe(getViewLifecycleOwner(), new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.tvCorreoFragmentInicio.setText(usuario.getCorreo());
                binding.tvNYAFragmentInicio.setText(usuario.nombreYApellido());
                Glide.with(getContext())
                        .load(ApiCliente.URLIMAGENUSUARIO+usuario.getAvatar())
                        .placeholder(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivAvatarFragmentInicio);
            }
        });
        vm.getMMensajeSinTurno().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visibilidad = vm.getVisibilidadAPartirDeBoolean(aBoolean);
                binding.tvMensajeErrorFragmentInicioSin.setVisibility(visibilidad);
            }
        });
        binding.btnAniadirTurnoInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AGREGAMOS NAV OPTIONS PARA SACAR nav_inicio del stack de fragments, y poder volver al nav_inico despues...
                Navigation.findNavController(view).navigate(R.id.nav_canchas, null, new NavOptions.Builder().setPopUpTo(R.id.nav_inicio,true).build());
            }
        });
        binding.btnVerHistorialTurnosFragmentInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_historial_turnos);
            }
        });
        vm.llenarLista();
        vm.cargarDatosUsuario();
        //cuando toque el boton para volver atras pregunte si quiere desloguearse
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                alertCerrarSesion();
            }
        });
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
        vm = new ViewModelProvider(this).get(InicioViewModel.class);
    }

    public void alertCerrarSesion(){
        new AlertDialog.Builder(requireContext())
                .setMessage("Estas seguro que desea salir?")
                .setTitle("Logout")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences shared = requireContext().getSharedPreferences("tokenUsuario", 0);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.remove("token");
                        editor.apply();
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences shared = getContext().getSharedPreferences("tokenUsuario", 0);
        String token = shared.getString("token",null);
        if(token == null){
            Intent i = new Intent(getContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(i);
        }
    }

}