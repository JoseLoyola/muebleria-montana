package com.muebleriamontana.model;

public class Producto {
    private int sku;
    private String nombre;
    private double costo;
    private int  stock;
    private ProductoCategoria productoCategoria;
    private String descripcion;

    public Producto() {
    }

    public Producto(String nombre, double costo, int stock, ProductoCategoria productoCategoria, String descripcion) {
        this.nombre = nombre;
        this.costo = costo;
        this.stock = stock;
        this.productoCategoria = productoCategoria;
        this.descripcion = descripcion;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ProductoCategoria getCategoriaProducto() {
        return productoCategoria;
    }

    public void setCategoriaProducto(ProductoCategoria productoCategoria) {
        this.productoCategoria = productoCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
