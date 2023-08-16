package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.EmpleadoCategoria;
import com.muebleriamontana.model.Sucursal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sucursal_DAO implements DAO<Sucursal>{
    Empleado_DAO empleadoDao;
    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public List<Sucursal> listarL() {
        ArrayList<Sucursal> sucursalObservableList = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call sucursal_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                sucursalObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sucursalObservableList;
    }

    @Override
    public ObservableList<Sucursal> listarOL() {
        ObservableList<Sucursal> sucursalObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call sucursal_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                sucursalObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sucursalObservableList;
    }

    @Override
    public boolean agregar(Sucursal sucursal) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call sucursal_agregar(?,?,?,?)}")){
            buildCallObject(sucursal, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Sucursal busquedaID(Object id) {
        Sucursal sucursal = new Sucursal();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call sucursal_busquedaID(?)}")) {
            builCallID((Integer) id, callableStatement);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    sucursal = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sucursal;
    }

    @Override
    public ObservableList<Sucursal> busquedaGeneral(Object obj) {
        ObservableList<Sucursal> sucursalObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from sucursal" +
                    "    where id like '%"+obj+"%' " +
                    "    or nombre like '%"+obj+"%' " +
                    "    or direccion like '%"+obj+"%' " +
                    "    or distrito like '%"+obj+"%' " +
                    "    or encargado like '%"+obj+"%' ")) {
            while (resultSet.next()){
                Sucursal sucursal = buildObject(resultSet);
                sucursalObservableList.add(sucursal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sucursalObservableList;
    }

    @Override
    public boolean actualizar(Sucursal sucursal) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call sucursal_actualizar(?,?,?,?,?)}")) {
            builCallID(sucursal.getId(), callableStatement);
            buildCallObject(sucursal, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean eliminar(Object obj) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call sucursal_eliminar(?)}")) {
            builCallID((Integer) obj, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Sucursal buildObject(ResultSet resultSet) throws SQLException {
        Sucursal sucursal = new Sucursal();
        empleadoDao = new Empleado_DAO();
        sucursal.setId(resultSet.getInt("id"));
        sucursal.setNombre(resultSet.getString("nombre"));
        sucursal.setDireccion(resultSet.getString("direccion"));
        sucursal.setDistrito(resultSet.getString("distrito"));
        sucursal.setEncargado(empleadoDao.busquedaID(resultSet.getInt("encargado")));
        return sucursal;
    }
    private static void builCallID(Integer id, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("idV", id);
    }
    private static void buildCallObject(Sucursal sucursal, CallableStatement callableStatement) throws SQLException {
        callableStatement.setString("nombreV", sucursal.getNombre());
        callableStatement.setString("direccionV", sucursal.getDireccion());
        callableStatement.setString("distritoV", sucursal.getDistrito());
        callableStatement.setInt("encargadoV", sucursal.getEncargado().getId());
    }
}
