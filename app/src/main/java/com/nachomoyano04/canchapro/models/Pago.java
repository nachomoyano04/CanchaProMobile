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
}
