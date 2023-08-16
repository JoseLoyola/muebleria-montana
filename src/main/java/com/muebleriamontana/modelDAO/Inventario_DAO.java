package com.muebleriamontana.modelDAO;

import com.muebleriamontana.model.Inventario;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.List;

public class Inventario_DAO implements DAO<Inventario>{
    @Override
    public List<Inventario> listarL() {
        return null;
    }

    @Override
    public ObservableList<Inventario> listarOL() {
        return null;
    }

    @Override
    public boolean agregar(Inventario inventario) {
        return false;
    }

    @Override
    public Inventario busquedaID(Object id) {
        return null;
    }

    @Override
    public ObservableList<Inventario> busquedaGeneral(Object obj) {
        return null;
    }

    @Override
    public boolean actualizar(Inventario inventario) {
        return false;
    }

    @Override
    public boolean eliminar(Object obj) {
        return false;
    }

    @Override
    public Inventario buildObject(ResultSet resultSet) {
        return null;
    }
}
