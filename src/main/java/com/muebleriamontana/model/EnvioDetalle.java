package com.muebleriamontana.model;

public class EnvioDetalle {
    private Envio envio;
    private Integer nro;
    private Producto producto;
    private int cantidad;
    private String descripcion;

    public EnvioDetalle() {
    }

    public EnvioDetalle(Envio envio, Integer nro, Producto producto, int cantidad, String descripcion) {
        this.envio = envio;
        this.nro = nro;
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "EnvioDetalle{" +
                "envio=" + envio.getId() +
                ", nro=" + nro +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                '}'+"\n";
    }
}
