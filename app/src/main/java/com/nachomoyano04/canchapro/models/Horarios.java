package com.nachomoyano04.canchapro.models;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Horarios {

    private int id;
    private Cancha cancha;
    private int canchaId;
    @JsonAdapter(LocalTimeAdapter.class)
    private LocalTime horaInicio;
    @JsonAdapter(LocalTimeAdapter.class)
    private LocalTime horaFin;
    private String diaSemanal;

    public Horarios(int id, Cancha cancha, int canchaId, LocalTime horaInicio, LocalTime horaFin, String diaSemanal) {
        this.id = id;
        this.cancha = cancha;
        this.canchaId = canchaId;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.diaSemanal = diaSemanal;
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

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public int getCanchaId() {
        return canchaId;
    }

    public void setCanchaId(int canchaId) {
        this.canchaId = canchaId;
    }

    public String getDiaSemanal() {
        return diaSemanal;
    }

    public void setDiaSemanal(String diaSemanal) {
        this.diaSemanal = diaSemanal;
    }

    @Override
    public String toString() {
        return "Horarios{" +
                "id=" + id +
                ", cancha=" + cancha +
                ", canchaId=" + canchaId +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", diaSemanal='" + diaSemanal + '\'' +
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
