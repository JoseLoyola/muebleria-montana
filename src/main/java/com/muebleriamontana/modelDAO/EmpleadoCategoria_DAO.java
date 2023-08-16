package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.EmpleadoCategoria;
import com.muebleriamontana.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoCategoria_DAO implements DAO<EmpleadoCategoria>{

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public List<EmpleadoCategoria> listarL() {
        List<EmpleadoCategoria> empleadoCategoriaList = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_categoria_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()){
            while (resultSet.next()){
                EmpleadoCategoria empleadoCategoria = buildObject(resultSet);
                empleadoCategoriaList.add(empleadoCategoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleadoCategoriaList;
    }

    @Override
    public ObservableList<EmpleadoCategoria> listarOL() {
        ObservableList<EmpleadoCategoria> empleadoCategoriaObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_categoria_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()){
            while (resultSet.next()){
                EmpleadoCategoria empleadoCategoria = buildObject(resultSet);
                empleadoCategoriaObservableList.add(empleadoCategoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleadoCategoriaObservableList;
    }

    @Override
    public boolean agregar(EmpleadoCategoria empleadoCategoria) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_categoria_agregar(?,?)}")){
            callableStatement.setString(1, empleadoCategoria.getNombre());
            callableStatement.setString(2,empleadoCategoria.getDescripcion());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public EmpleadoCategoria busquedaID(Object id) {
        EmpleadoCategoria empleadoCategoria = new EmpleadoCategoria();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_categoria_busquedaID(?)}")){
            callableStatement.setInt(1, (Integer) id);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    empleadoCategoria = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleadoCategoria;
    }

    @Override
    public ObservableList<EmpleadoCategoria> busquedaGeneral(Object obj) {
        ObservableList<EmpleadoCategoria> productoObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""+
                    "SELECT * FROM empleado_categoria where id like '%"+ obj +"%' or nombre like '%"+ obj +"%' or descripcion like '%" + obj + "%';")) {
            while (resultSet.next()){
                EmpleadoCategoria empleadoCategoria = buildObject(resultSet);
                productoObservableList.add(empleadoCategoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoObservableList;
    }

    @Override
    public boolean actualizar(EmpleadoCategoria empleadoCategoria) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_categoria_actualizar(?,?,?)}")){
            callableStatement.setInt(1, empleadoCategoria.getId());
            callableStatement.setString(2, empleadoCategoria.getNombre());
            callableStatement.setString(3,empleadoCategoria.getDescripcion());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean eliminar(Object obj) {
        boolean eliminado = false;
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_categoria_eliminar(?)}")){
            callableStatement.setInt(1, (Integer) obj);
            if (callableStatement.executeUpdate()==1){
                eliminado = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return eliminado;
    }

    @Override
    public EmpleadoCategoria buildObject(ResultSet resultSet) throws SQLException{
        EmpleadoCategoria empleadoCategoria = new EmpleadoCategoria();
        empleadoCategoria.setId(resultSet.getInt("id"));
        empleadoCategoria.setNombre(resultSet.getString("nombre"));
        empleadoCategoria.setDescripcion(resultSet.getString("descripcion"));
        return empleadoCategoria;
    }

}
