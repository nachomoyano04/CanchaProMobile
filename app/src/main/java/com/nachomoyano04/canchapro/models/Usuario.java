package com.nachomoyano04.canchapro.models;

public class Usuario {
    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String avatar;
    private int estado;

    public Usuario(int id, String dni, String nombre, String apellido, String correo, String password, String avatar, int estado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
        this.avatar = avatar;
        this.estado = estado;
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

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean camposLlenos() {
        return !dni.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !password.isEmpty();
    }
}
