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
import java.time.format.FormatStyle;
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
//                binding.tvCanchaAltaTurnos.setText(t.getCancha().getTipo().getNombre());
                binding.tvCanchaAltaTurnos.setText(t.getCancha().getNombre());
                LocalDate fechaDeHoy = t.getFechaInicio().toLocalDate();
                ArrayList<String> fechas = vm.getArrayListDeFechas(fechaDeHoy);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, fechas);
                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                binding.spinnerFechaAltaTurnos.setAdapter(adapter);
                binding.spinnerFechaAltaTurnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEEE dd/MM yyyy", Locale.forLanguageTag("es"));
                        try {
                            int year = LocalDate.now().getYear();
                            LocalDate date = LocalDate.parse(adapterView.getSelectedItem().toString()+" "+year, inputFormatter);
                            LocalDateTime dateAndHour = date.atTime(t.getFechaInicio().toLocalTime());
                            vm.setearMutableFecha(dateAndHour);
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, localTimes);
                adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                binding.spinnerHoraFinAltaTurnos.setAdapter(adapter);
            }
        });
        vm.getMSinHorarios().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                int visibilidad = vm.getVisibilidadFromBoolean(aBoolean);
                binding.spinnerHoraInicioAltaTurnos.setVisibility(visibilidad);
                binding.spinnerHoraFinAltaTurnos.setVisibility(visibilidad);
                binding.tvCanchaAltaTurnos.setVisibility(visibilidad);
                binding.textView35.setVisibility(visibilidad);
                binding.textView37.setVisibility(visibilidad);
                binding.textView41.setVisibility(visibilidad);
                binding.tvMensajeFragmentAltaTurnos.setVisibility(vm.getVisibilidadFromBoolean(!aBoolean));
                binding.btnGuardarAltaTurnos.setEnabled(aBoolean);
            }
        });
        vm.getMRespuestaAltaYEditar().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mensaje) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Reserva:")
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
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEEE dd/MM yyyy", Locale.forLanguageTag("es"));
                try {
                    int year = LocalDate.now().getYear();
                    LocalDate fecha = LocalDate.parse(binding.spinnerFechaAltaTurnos.getSelectedItem().toString() + " " + year, inputFormatter);
                    String horaInicio = (String) binding.spinnerHoraInicioAltaTurnos.getSelectedItem();
                    String horaFin = (String) binding.spinnerHoraFinAltaTurnos.getSelectedItem();
                    vm.btnGuardar(view, fecha.toString(), horaInicio, horaFin);
                }catch (Exception e){
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    Log.d("getContext()", e.getMessage());
                }
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