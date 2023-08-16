package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.Producto;
import com.muebleriamontana.model.ProductoCategoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoCategoria_DAO implements DAO<ProductoCategoria>{
    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public List<ProductoCategoria> listarL() {
        List<ProductoCategoria> productoCategoriaList = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call producto_categoria_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                productoCategoriaList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoCategoriaList;
    }

    @Override
    public ObservableList<ProductoCategoria> listarOL() {
        ObservableList<ProductoCategoria> productoCategoriaList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call producto_categoria_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                productoCategoriaList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoCategoriaList;
    }

    @Override
    public boolean agregar(ProductoCategoria productoCategoria) {
        try(Connection connection = getConnection();
        CallableStatement callableStatement = connection.prepareCall("{call producto_categoria_agregar(?,?)}")){
            buildCallObject(productoCategoria, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public ProductoCategoria busquedaID(Object id) {
        ProductoCategoria productoCategoria = new ProductoCategoria();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_categoria_busquedaID(?)}")) {
            builCallID((Integer) id, callableStatement);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    productoCategoria = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoCategoria;
    }

    @Override
    public ObservableList<ProductoCategoria> busquedaGeneral(Object obj) {
        ObservableList<ProductoCategoria> productoCategoriaObservableList= FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""+
                    "select * from producto_categoria where " +
                    "id like '%"+ obj +"%' or nombre like '%"+ obj +"%' or descripcion like '%"+ obj +"%';")) {
            while (resultSet.next()){
                productoCategoriaObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoCategoriaObservableList;
    }

    @Override
    public boolean actualizar(ProductoCategoria productoCategoria) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_categoria_actualizar(?,?,?)}")) {
            builCallID(productoCategoria.getId(), callableStatement);
            buildCallObject(productoCategoria, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean eliminar(Object obj) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_categoria_eliminar(?)}")) {
            builCallID((Integer) obj, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public ProductoCategoria buildObject(ResultSet resultSet) throws SQLException {
        ProductoCategoria productoCategoria = new ProductoCategoria();
        productoCategoria.setId(resultSet.getInt("id"));
        productoCategoria.setNombre(resultSet.getString("nombre"));
        productoCategoria.setDescripcion(resultSet.getString("descripcion"));
        return productoCategoria;
    }
    private static void builCallID(Integer id, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("idV", id);
    }
    private static void buildCallObject(ProductoCategoria productoCategoria, CallableStatement callableStatement) throws SQLException {
        callableStatement.setString("nombreV", productoCategoria.getNombre());
        callableStatement.setString("descripcionV", productoCategoria.getDescripcion());
    }
}
