package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.EnvioDetalle;
import com.muebleriamontana.model.EnvioEstado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnvioEstado_DAO implements DAO<EnvioEstado>{
    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public List<EnvioEstado> listarL() {
        List<EnvioEstado> envioEstadoList = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call envio_estado_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                envioEstadoList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioEstadoList;
    }

    @Override
    public ObservableList<EnvioEstado> listarOL() {
        ObservableList<EnvioEstado> empleadoObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call envio_estado_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                empleadoObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleadoObservableList;
    }

    @Override
    public boolean agregar(EnvioEstado envioEstado) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_estado_agregar(?,?,?)}")){
            buildCallObject(envioEstado, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public EnvioEstado busquedaID(Object id) {
        EnvioEstado envioEstado = new EnvioEstado();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_estado_busquedaID(?)}")){
            callableStatement.setInt(1, (Integer) id);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    envioEstado= buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioEstado;
    }

    @Override
    public ObservableList<EnvioEstado> busquedaGeneral(Object obj) {
        return null;
    }

    @Override
    public boolean actualizar(EnvioEstado envioEstado) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_estado_actualizar(?,?,?)}")) {
            builCallID(envioEstado.getId(), callableStatement);
            buildCallObject(envioEstado, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean eliminar(Object obj) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_estado_eliminar(?)}")) {
            builCallID((Integer) obj, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public EnvioEstado buildObject(ResultSet resultSet) throws SQLException {
        EnvioEstado envioEstado = new EnvioEstado();
        envioEstado.setId(resultSet.getInt("id"));
        envioEstado.setNombre(resultSet.getString("nombre"));
        envioEstado.setDescripcion(resultSet.getString("descripcion"));
        return  envioEstado;
    }
    private static void builCallID(Integer id, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("idV", id);
    }
    private static void buildCallObject(EnvioEstado envioEstado, CallableStatement callableStatement) throws SQLException {
        callableStatement.setString("nombreV", envioEstado.getNombre());
        callableStatement.setString("descripcionV", envioEstado.getDescripcion());
    }

}
