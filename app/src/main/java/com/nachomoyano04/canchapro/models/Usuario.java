package com.nachomoyano04.canchapro.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String password;
    private String repetirPassword;
    private String avatar;
    private boolean estado;

    public Usuario(){}

    public Usuario(String dni, String nombre, String apellido, String telefono, String correo, String password, String repetirPassword) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.password = password;
        this.repetirPassword = repetirPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean camposLlenos() {
        return !dni.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !password.isEmpty();
    }

    public boolean passwordsCoinciden() {
        return password.equals(repetirPassword);
    }

    public String nombreYApellido(){return nombre+" "+apellido;}
}
