package com.nachomoyano04.canchapro.ui.turnos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.nachomoyano04.canchapro.R;
import com.nachomoyano04.canchapro.databinding.FragmentAltaTurnosBinding;
import com.nachomoyano04.canchapro.models.Cancha;
import com.nachomoyano04.canchapro.models.Horarios;
import com.nachomoyano04.canchapro.models.Turno;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AltaTurnosFragment extends Fragment {

    private FragmentAltaTurnosBinding binding;
    private AltaTurnosViewModel vm;

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
                binding.tvCanchaAltaTurnos.setText(t.getCancha().getTipo().getNombre());
                vm.setearMutableFecha(t.getFechaInicio());
                LocalDate fechaDeHoy = t.getFechaInicio().toLocalDate();
                ArrayList<String> fechas = new ArrayList<>();
                fechas.add(fechaDeHoy.toString());
                fechas.add(fechaDeHoy.plusDays(1).toString());
                fechas.add(fechaDeHoy.plusDays(2).toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, fechas);
                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                binding.spinnerFechaAltaTurnos.setAdapter(adapter);
                binding.spinnerFechaAltaTurnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        vm.setearMutableFecha(LocalDateTime.parse(adapterView.getSelectedItem().toString()+" 00:00", formatter));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
        vm.getMFecha().observe(getViewLifecycleOwner(), new Observer<LocalDateTime>() {
            @Override
            public void onChanged(LocalDateTime localDateTime) {
                //cuando seteamos la fecha deberia haber un metodo que
                // setee los mutables de hora de inicio con los horarios de inicio
                //disponibles con esa fecha
                vm.setMutableHoraInicio(localDateTime);
            }
        });
        vm.getMHoraInicio().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                //viniendo desde el seteo de la fecha lo que deberia hacerse
                //es poner el spinner y setear el horario de fin con el spinner seleccionado
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, strings);
                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                binding.spinnerHoraInicioAltaTurnos.setAdapter(adapter);
                binding.spinnerHoraInicioAltaTurnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        vm.setMutableHoraFin(LocalTime.parse(adapterView.getSelectedItem().toString()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
        //Observador que setea el Spinner de hora fin.
        vm.getMHoraFin().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> localTimes) {
                for(String s: localTimes){
                    Log.d("asdfgrwnfg", s);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, localTimes);
                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                binding.spinnerHoraFinAltaTurnos.setAdapter(adapter);
            }
        });
        vm.getMRespuestaAltaYEditar().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                new AlertDialog.Builder(getContext())
                        .setTitle("El monto del total es:")
                        .setMessage(mensaje)
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                Navigation.findNavController(getView()).navigate(R.id.nav_inicio);
            }
        });
        binding.btnGuardarAltaTurnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha = (String) binding.spinnerFechaAltaTurnos.getSelectedItem();
                String horaInicio = (String) binding.spinnerHoraInicioAltaTurnos.getSelectedItem();
                String horaFin = (String) binding.spinnerHoraFinAltaTurnos.getSelectedItem();
                vm.btnGuardar(view, fecha, horaInicio, horaFin);
            }
        });
//        binding.ivBtnEditarFechaAltaTurnos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        binding.etFechaAltaTurnos.setText(year+"-"+(month+1)+"-"+day);
//                        LocalDateTime fechaElegida = LocalDateTime.of(year, (month+1), day,0,0);
//                        vm.setearMutableFecha(fechaElegida);
//                    }
//                }, LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue()-1, LocalDateTime.now().getDayOfMonth());
//                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
//                dpd.getDatePicker().setMaxDate(LocalDateTime.now().plusDays(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//                dpd.show();
//            }
//        });
        binding.spinnerFechaAltaTurnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Bundle b = getArguments();
        Turno turno = (Turno) b.getSerializable("turno");
        Cancha cancha = (Cancha) b.getSerializable("cancha");
        Boolean editar = (Boolean) b.getSerializable("editar");
        vm.setearMutable(cancha, turno, editar);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = new ViewModelProvider(this).get(AltaTurnosViewModel.class);
    }

}