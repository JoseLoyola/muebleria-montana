package com.muebleriamontana.model;

public class Empleado {
    private int id;
    private String nombre;
    private String paterno;
    private String materno;
    private String correo;
    private String usuario;
    private String contrasena;
    private EmpleadoCategoria empleadoCategoria;

    public Empleado() {
    }

    public Empleado(int id, String nombre, String paterno, String materno, String correo, String usuario, String contrasena, EmpleadoCategoria empleadoCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.empleadoCategoria = empleadoCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public EmpleadoCategoria getEmpleadoCategoria() {
        return empleadoCategoria;
    }

    public void setEmpleadoCategoria(EmpleadoCategoria empleadoCategoria) {
        this.empleadoCategoria = empleadoCategoria;
    }

    @Override
    public String toString() {
        return nombre + " " + paterno + " " + materno ;
    }

}
