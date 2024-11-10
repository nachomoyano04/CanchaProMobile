package com.nachomoyano04.canchapro.models;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Horarios {

    private int id;
    @JsonAdapter(LocalTimeAdapter.class)
    private LocalTime horaInicio;
    @JsonAdapter(LocalTimeAdapter.class)
    private LocalTime horaFin;
    private boolean despuesDe12;

    public Horarios(int id, LocalTime horaInicio, LocalTime horaFin, boolean despuesDe12) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.despuesDe12 = despuesDe12;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isDespuesDe12() {
        return despuesDe12;
    }

    public void setDespuesDe12(boolean despuesDe12) {
        this.despuesDe12 = despuesDe12;
    }

    @Override
    public String toString() {
        return "Horarios{" +
                "id=" + id +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", despuesDe12=" + despuesDe12 +
                '}';
    }

    public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
        @Override
        public void write(com.google.gson.stream.JsonWriter out, LocalTime value) throws IOException {
            out.value(value.toString());

        }

        @Override
        public LocalTime read(com.google.gson.stream.JsonReader in) throws IOException {
            return LocalTime.parse(in.nextString());
        }
    }
}
