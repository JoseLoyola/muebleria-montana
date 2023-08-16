package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.Empleado;
import com.muebleriamontana.model.ProductoCategoria;
import com.muebleriamontana.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Producto_DAO implements DAO<Producto>{

    ProductoCategoria_DAO productoCategoriaDao;
    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public List<Producto> listarL() {
        List<Producto> productoList = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                productoList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoList;
    }

    @Override
    public ObservableList<Producto> listarOL() {
        ObservableList<Producto> productoList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                productoList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  productoList;
    }

    @Override
    public boolean agregar(Producto producto) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_agregar(?,?,?,?,?)}")){
            buildCallObject(producto, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Producto busquedaID(Object id) {
        Producto producto = new Producto();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_busquedaID(?)}")) {
            builCallID((Integer) id, callableStatement);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    producto = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public ObservableList<Producto> busquedaGeneral(Object obj) {
        ObservableList<Producto> productoObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(""+
                    "select * from producto where " +
                    "sku like '%"+ obj +"%' or nombre like '%"+ obj +"%' or costo like '%"+ obj +"%' or " +
                    "stock like '%"+ obj +"%' or producto_Categoria like '%"+ obj +"%' or descripcion like '%"+ obj +"%';")) {
            while (resultSet.next()){
                productoObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productoObservableList;
    }

    @Override
    public boolean actualizar(Producto producto) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_actualizar(?,?,?,?,?,?)}")) {
            builCallID(producto.getSku(), callableStatement);
            buildCallObject(producto, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    @Override
    public boolean eliminar(Object obj) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call producto_eliminar(?)}")) {
            builCallID((Integer) obj, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Producto buildObject(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        productoCategoriaDao = new ProductoCategoria_DAO();
        producto.setSku(resultSet.getInt("sku"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setCosto(resultSet.getDouble("costo"));
        producto.setStock(resultSet.getInt("stock"));
        producto.setCategoriaProducto(productoCategoriaDao.busquedaID(resultSet.getInt("producto_Categoria")));
        producto.setDescripcion(resultSet.getString("descripcion"));
        return producto;
    }

    private static void builCallID(Integer sku, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("skuV", sku);
    }
    private static void buildCallObject(Producto producto, CallableStatement callableStatement) throws SQLException {
        callableStatement.setString("nombreV", producto.getNombre());
        callableStatement.setDouble("costoV", producto.getCosto());
        callableStatement.setInt("stockV", producto.getStock());
        callableStatement.setInt("producto_CategoriaV", producto.getCategoriaProducto().getId());
        callableStatement.setString("descripcionV", producto.getDescripcion());
    }

}
