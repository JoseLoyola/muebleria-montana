package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.Envio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Envio_DAO implements DAO<Envio>{
    Sucursal_DAO sucursalDAO;
    EnvioEstado_DAO envioEstadoDAO;
    EnvioDetalle_DAO envioDetalleDao;
    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }
    @Override
    public List<Envio> listarL() {
        ArrayList<Envio> envioArrayList = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call envio_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                envioArrayList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioArrayList;
    }

    @Override
    public ObservableList<Envio> listarOL() {
        ObservableList<Envio> envioObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call envio_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                envioObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioObservableList;
    }

    @Override
    public boolean agregar(Envio envio) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_agregar(?,?,?,?,?,?)}")){
            buildCallObject(envio, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Envio busquedaID(Object id) {
        Envio envio = null;
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_busquedaID(?)}")) {
            builCallID((Integer) id, callableStatement);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    envio = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envio;
    }

    @Override
    public ObservableList<Envio> busquedaGeneral(Object obj) {
        ObservableList<Envio> envioObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select * from envio where " +
                    "emisor like '%"+obj+"%' or receptor like '%"+obj+"%' or estado like '%"+obj+"%' or " +
                    "fecha like '%"+obj+"%' or hora like '%"+obj+"%' or descripcion like '%"+obj+"%';");
            ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                envioObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return envioObservableList;
    }

    @Override
    public boolean actualizar(Envio envio) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_actualizar(?,?,?,?,?,?,?)}")) {
            builCallID(envio.getId(), callableStatement);
            buildCallObject(envio, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean eliminar(Object obj) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call envio_eliminar(?)}")) {
            builCallID((Integer) obj, callableStatement);
            envioDetalleDao = new EnvioDetalle_DAO();
            envioDetalleDao.eliminar(obj);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Envio buildObject(ResultSet resultSet) throws SQLException {
        Envio envio = new Envio();
        sucursalDAO = new Sucursal_DAO();
        envioEstadoDAO = new EnvioEstado_DAO();
        envio.setId(resultSet.getInt("id"));
        envio.setEmisor(sucursalDAO.busquedaID(resultSet.getInt("emisor")));
        envio.setReceptor(sucursalDAO.busquedaID(resultSet.getInt("receptor")));
        envio.setEstado(envioEstadoDAO.busquedaID(resultSet.getInt("estado")));
        envio.setFecha(resultSet.getString("fecha"));
        envio.setHora(resultSet.getString("hora"));
        envio.setDescripcion(resultSet.getString("descripcion"));
        return envio;
    }
    private static void builCallID(Integer id, CallableStatement callableStatement) throws SQLException {
            callableStatement.setInt("idV", id);
    }
    private static void buildCallObject(Envio envio, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("emisorV", envio.getEmisor().getId());
        callableStatement.setInt("receptorV", envio.getReceptor().getId());
        callableStatement.setInt("estadoV", envio.getEstado().getId());
        callableStatement.setString("fechaV", envio.getFecha());
        callableStatement.setString("horaV", envio.getHora());
        callableStatement.setString("descripcionV", envio.getDescripcion());
    }
}
