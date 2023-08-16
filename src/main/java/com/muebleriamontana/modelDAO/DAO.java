package com.muebleriamontana.modelDAO;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DAO <T>{
    
    List<T> listarL();
    ObservableList<T> listarOL();
    boolean agregar(T t);
    T busquedaID(Object id);
    ObservableList<T> busquedaGeneral(Object obj);
    boolean actualizar(T t);
    boolean eliminar(Object obj);
    T buildObject(ResultSet resultSet) throws SQLException;
}
