package com.nachomoyano04.canchapro.models;

import java.io.Serializable;

public class Cancha implements Serializable {
    private int id;
//    private int tipoId;
//    private Tipo tipo;
    private String nombre;
    private int capacidadTotal;
    private String tipoDePiso;
    private String imagen;
    private Double precioPorHora;
    private String descripcion;
    private double porcentajeCalificacion;
    private int estado;

    public Cancha() {}

    public Cancha(int id, String nombre, int capacidadTotal, String tipoDePiso, String imagen, Double precioPorHora, String descripcion, double porcentajeCalificacion, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.capacidadTotal = capacidadTotal;
        this.tipoDePiso = tipoDePiso;
        this.imagen = imagen;
        this.precioPorHora = precioPorHora;
        this.descripcion = descripcion;
        this.porcentajeCalificacion = porcentajeCalificacion;
        this.estado = estado;
    }

    //    public Cancha(int id, int tipoId, Tipo tipo, String imagen, Double precioPorHora, String descripcion, double porcentajeCalificacion, int estado) {
//        this.id = id;
//        this.tipoId = tipoId;
//        this.tipo = tipo;
//        this.imagen = imagen;
//        this.precioPorHora = precioPorHora;
//        this.descripcion = descripcion;
//        this.porcentajeCalificacion = porcentajeCalificacion;
//        this.estado = estado;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getTipoId() {
//        return tipoId;
//    }
//
//    public void setTipoId(int tipoId) {
//        this.tipoId = tipoId;
//    }
//
//    public Tipo getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(Tipo tipo) {
//        this.tipo = tipo;
//    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPrecioPorHora() {
        return precioPorHora;
    }

    public void setPrecioPorHora(Double precioPorHora) {
        this.precioPorHora = precioPorHora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public String getTipoDePiso() {
        return tipoDePiso;
    }

    public void setTipoDePiso(String tipoDePiso) {
        this.tipoDePiso = tipoDePiso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPorcentajeCalificacion() {
        return porcentajeCalificacion;
    }

    public void setPorcentajeCalificacion(double porcentajeCalificacion) {
        this.porcentajeCalificacion = porcentajeCalificacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

//    @Override
//    public String toString() {
//        return "Cancha{" +
//                "descripcion='" + descripcion + '\'' +
//                ", id=" + id +
//                ", tipoId=" + tipoId +
//                ", tipo=" + tipo +
//                ", imagen='" + imagen + '\'' +
//                ", precioPorHora=" + precioPorHora +
//                ", porcentajeCalificacion=" + porcentajeCalificacion +
//                ", estado=" + estado +
//                '}';
//    }

    @Override
    public String toString() {
        return "Cancha{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", capacidadTotal=" + capacidadTotal +
                ", tipoDePiso='" + tipoDePiso + '\'' +
                ", imagen='" + imagen + '\'' +
                ", precioPorHora=" + precioPorHora +
                ", descripcion='" + descripcion + '\'' +
                ", porcentajeCalificacion=" + porcentajeCalificacion +
                ", estado=" + estado +
                '}';
    }
}
