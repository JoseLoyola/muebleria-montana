package com.muebleriamontana.conexionBD;

import com.muebleriamontana.model.Empleado;

public class Sesion {
    private static Sesion instance;
    private Empleado empleado;
    private Sesion(){
    }

    public static Sesion getInstance() {
        if (instance == null){
            instance = new Sesion();
        }
        return instance;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
