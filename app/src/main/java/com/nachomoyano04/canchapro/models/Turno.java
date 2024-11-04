package com.nachomoyano04.canchapro.models;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.io.IOException;
import java.time.LocalDateTime;

public class Turno {
    private int id;
    private int canchaId;
    private Cancha cancha;
    private int usuarioId;
    private Usuario usuario;
    private int pagoId;
    private Pago pago;
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaInicio;
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime fechaFin;
    private int estado;

    public Turno() {}

    public Turno(int id, int canchaId, Cancha cancha, int usuarioId, Usuario usuario, int pagoId, Pago pago, LocalDateTime fechaInicio, LocalDateTime fechaFin, int estado) {
        this.id = id;
        this.canchaId = canchaId;
        this.cancha = cancha;
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.pagoId = pagoId;
        this.pago = pago;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCanchaId() {
        return canchaId;
    }

    public void setCanchaId(int canchaId) {
        this.canchaId = canchaId;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getPagoId() {
        return pagoId;
    }

    public void setPagoId(int pagoId) {
        this.pagoId = pagoId;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", canchaId=" + canchaId +
                ", cancha=" + cancha +
                ", usuarioId=" + usuarioId +
                ", usuario=" + usuario +
                ", pagoId=" + pagoId +
                ", pago=" + pago +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado=" + estado +
                '}';
    }
    public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(com.google.gson.stream.JsonWriter out, LocalDateTime value) throws IOException {
            out.value(value.toString());

        }

        @Override
        public LocalDateTime read(com.google.gson.stream.JsonReader in) throws IOException {
            return LocalDateTime.parse(in.nextString());
        }
    }
}
