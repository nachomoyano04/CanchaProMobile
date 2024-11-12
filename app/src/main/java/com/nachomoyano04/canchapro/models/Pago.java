package com.nachomoyano04.canchapro.models;

import java.time.LocalDateTime;

public class Pago {

    private int id;
    private double montoReserva;
    private double montoTotal;
    private LocalDateTime fechaPagoReserva;
    private LocalDateTime fechaPagoTotal;
    private String metodo;
    private String comprobanteReserva;
    private int estado;

    public Pago(int id, double montoReserva, double montoTotal, LocalDateTime fechaPagoReserva, LocalDateTime fechaPagoTotal, String metodo, String comprobanteReserva, int estado) {
        this.id = id;
        this.montoReserva = montoReserva;
        this.montoTotal = montoTotal;
        this.fechaPagoReserva = fechaPagoReserva;
        this.fechaPagoTotal = fechaPagoTotal;
        this.metodo = metodo;
        this.comprobanteReserva = comprobanteReserva;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontoReserva() {
        return montoReserva;
    }

    public void setMontoReserva(double montoReserva) {
        this.montoReserva = montoReserva;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDateTime getFechaPagoReserva() {
        return fechaPagoReserva;
    }

    public void setFechaPagoReserva(LocalDateTime fechaPagoReserva) {
        this.fechaPagoReserva = fechaPagoReserva;
    }

    public LocalDateTime getFechaPagoTotal() {
        return fechaPagoTotal;
    }

    public void setFechaPagoTotal(LocalDateTime fechaPagoTotal) {
        this.fechaPagoTotal = fechaPagoTotal;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getComprobanteReserva() {
        return comprobanteReserva;
    }

    public void setComprobanteReserva(String comprobanteReserva) {
        this.comprobanteReserva = comprobanteReserva;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
