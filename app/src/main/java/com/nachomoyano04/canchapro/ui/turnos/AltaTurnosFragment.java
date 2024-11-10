package com.nachomoyano04.canchapro.ui.turnos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentAltaTurnosBinding;
import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.models.Horarios;
import com.nachomoyano04.canchapro.models.Turno;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.ArrayList;

public class AltaTurnosFragment extends Fragment {

    private FragmentAltaTurnosBinding binding;
    private AltaTurnosViewModel vm;
    private Turno t;
    private Cancha c;

    public static AltaTurnosFragment newInstance() {
        return new AltaTurnosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAltaTurnosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(AltaTurnosViewModel.class);
        vm.getMTurno().observe(getViewLifecycleOwner(), new Observer<Turno>() {
            @Override
            public void onChanged(Turno t) {
                binding.etFechaAltaTurnos.setText(t.getFechaInicio().toLocalDate().toString());
                binding.tvCanchaAltaTurnos.setText(t.getCancha().getTipo().getNombre());
                vm.getHorariosDisponibles(t);
            }
        });
        vm.getMHorarios().observe(getViewLifecycleOwner(), new Observer<Horarios>() {
            @Override
            public void onChanged(Horarios horarios) {
                vm.setMutableHoraInicio(t, horarios);
            }
        });
        vm.getMHoraInicio().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, strings);
                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                binding.spinnerHoraInicioAltaTurnos.setAdapter(adapter);
                binding.spinnerHoraInicioAltaTurnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        vm.setMutableHoraFin(t, LocalTime.parse(adapterView.getSelectedItem().toString()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
        vm.getMHoraFin().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> localTimes) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, localTimes);
                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                binding.spinnerHoraFinAltaTurnos.setAdapter(adapter);
            }
        });
        binding.ivBtnEditarFechaAltaTurnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.etFechaAltaTurnos.setText(year+"-"+(month+1)+"-"+day);
                        vm.setearMutable(c,t);
                    }
                }, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()-1, LocalDateTime.now().getDayOfMonth());
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.getDatePicker().setMaxDate(LocalDateTime.now().plusDays(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                dpd.show();
            }
        });
        Bundle b = getArguments();
        t = (Turno) b.getSerializable("turno");
        c = (Cancha) b.getSerializable("cancha");
        vm.setearMutable(c, t);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(AltaTurnosViewModel.class);
    }

}