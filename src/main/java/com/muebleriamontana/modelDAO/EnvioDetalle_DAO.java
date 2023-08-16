package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.Envio;
import com.muebleriamontana.model.EnvioDetalle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnvioDetalle_DAO implements DAO<EnvioDetalle>{
    EnvioDetalle envioDetalle;
    Envio_DAO envioDao;
    Producto_DAO productoDAO;
    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public List<EnvioDetalle> listarL() {
        ArrayList<EnvioDetalle> envioDetalles = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_detalle_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()){
            while (resultSet.next()){
                envioDetalles.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioDetalles;
    }

    @Override
    public ObservableList<EnvioDetalle> listarOL() {
        ObservableList<EnvioDetalle> envioDetalleObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_detalle_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()){
            while (resultSet.next()){
                envioDetalleObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioDetalleObservableList;
    }

    public ObservableList<EnvioDetalle> listarOL_Detalle(int id) throws SQLException {
        ObservableList<EnvioDetalle> envioDetalleObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_detalle_busquedaID(?)}")){
            builCallID((Integer) id, callableStatement);
            try (ResultSet resultSet = callableStatement.executeQuery()){
                while (resultSet.next()){
                    envioDetalleObservableList.add(buildObject(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioDetalleObservableList;
    }

    @Override
    public boolean agregar(EnvioDetalle envioDetalle) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_detalle_agregar(?,?,?,?,?)}")){
            buildCallObject(envioDetalle, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public EnvioDetalle busquedaID(Object id) {
        EnvioDetalle envioDetalle = new EnvioDetalle();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_detalle_busquedaID(?)}")) {
            builCallID((Integer) id, callableStatement);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    envioDetalle = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioDetalle;
    }

    @Override
    public ObservableList<EnvioDetalle> busquedaGeneral(Object obj) {
        return null;
    }

    @Override
    public boolean actualizar(EnvioDetalle envioDetalle) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_detalle_actualizar(?,?,?,?,?)}")) {
            builCallID(envioDetalle.getEnvio().getId(), callableStatement);
            buildCallObject(envioDetalle, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean eliminar(Object obj) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_detalle_eliminar_envio(?)}")) {
            builCallID((Integer) obj, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public EnvioDetalle buildObject(ResultSet resultSet) throws SQLException {
        envioDetalle = new EnvioDetalle();
        envioDao = new Envio_DAO();
        productoDAO = new Producto_DAO();
        envioDetalle.setEnvio(envioDao.busquedaID(resultSet.getInt("envio")));
        envioDetalle.setNro(resultSet.getInt("nro"));
        envioDetalle.setProducto(productoDAO.busquedaID(resultSet.getInt("producto")));
        envioDetalle.setCantidad(resultSet.getInt("cantidad"));
        envioDetalle.setDescripcion(resultSet.getString("descripcion"));
        return envioDetalle;
    }
    private static void builCallID(Integer id, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("envioV", id);
    }
    private static void buildCallObject(EnvioDetalle envioDetalle, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("nroV",envioDetalle.getNro());
        callableStatement.setInt("productoV", envioDetalle.getProducto().getSku());
        callableStatement.setInt("cantidadV", envioDetalle.getCantidad());
        callableStatement.setString("descripcionV", envioDetalle.getDescripcion());
    }
}
