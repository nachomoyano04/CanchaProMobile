package com.nachomoyano04.canchapro.ui.perfil;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentEditarAvatarBinding;
import com.nachomoyano04.canchapro.models.Usuario;

public class EditarAvatarFragment extends Fragment {

    private EditarAvatarViewModel vm;
    private FragmentEditarAvatarBinding binding;
    private Intent intent;
    private ActivityResultLauncher launcher;
    private boolean isEnabled = true;

    public static EditarAvatarFragment newInstance() {
        return new EditarAvatarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(EditarAvatarViewModel.class);
        binding = FragmentEditarAvatarBinding.inflate(inflater, container, false);
        Bundle b = getArguments();
        Usuario u = (Usuario) b.getSerializable("usuario");
        abrirGaleria();
        vm.getMAvatar().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(getContext())
                        .load(s.startsWith("http")?s: Uri.parse(s))
                        .placeholder(R.drawable.ic_launcher_background)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivAvatarEditarAvatarFragment);
            }
        });
        binding.btnPickerEditarAvatarFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch(intent);
            }
        });
        binding.btnCancelarEditarAvatarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_perfilFragment);
            }
        });
        binding.btnGuardarEditarAvatarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.guardar();
            }
        });
        vm.setearImagen(u.getAvatar());
        return binding.getRoot();
    }

    public void abrirGaleria(){
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                vm.recibirFoto(o);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(EditarAvatarViewModel.class);
        // TODO: Use the ViewModel
    }

}