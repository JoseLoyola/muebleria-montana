package com.muebleriamontana.model;

import javafx.beans.property.*;

public class Envio {
    private int id;
    private Sucursal emisor;
    private Sucursal receptor;
    private EnvioEstado estado;
    private String fecha;
    private String hora;
    private String descripcion;

    public Envio() {
    }

    public Envio(int id, Sucursal emisor, Sucursal receptor, EnvioEstado estado, String fecha, String hora, String descripcion) {
        this.id = id;
        this.emisor = emisor;
        this.receptor = receptor;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sucursal getEmisor() {
        return emisor;
    }

    public void setEmisor(Sucursal emisor) {
        this.emisor = emisor;
    }

    public Sucursal getReceptor() {
        return receptor;
    }

    public void setReceptor(Sucursal receptor) {
        this.receptor = receptor;
    }

    public EnvioEstado getEstado() {
        return estado;
    }

    public void setEstado(EnvioEstado estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "id=" + id +
                ", emisor=" + emisor.getDistrito() +
                ", receptor=" + receptor.getDistrito() +
                ", estado=" + estado.getNombre() +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
