package com.muebleriamontana.modelDAO;

import com.muebleriamontana.conexionBD.Conexion;
import com.muebleriamontana.model.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Empleado_DAO implements DAO<Empleado>{

    EmpleadoCategoria_DAO empleadoCategoriaDao;
    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public List<Empleado> listarL() {
        List<Empleado> empleadoList = new ArrayList<>();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call empleado_listar()}");
            ResultSet resultSet = callableStatement.executeQuery()) {
            while (resultSet.next()){
                empleadoList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleadoList;
    }
    public ObservableList<Empleado> listarOLGerente() {
        ObservableList<Empleado> empleadoObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call empleado_listar_gerente()}");
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
    public ObservableList<Empleado> listarOL() {
        ObservableList<Empleado> empleadoObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            CallableStatement callableStatement =connection.prepareCall("{call empleado_listar()}");
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
    public boolean agregar(Empleado empleado) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_agregar(?,?,?,?,?,?,?)}")){
            buildCallObject(empleado, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Empleado busquedaID(Object id) {
        Empleado empleado = null;
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_busquedaID(?)}")) {
            builCallID((Integer) id, callableStatement);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                if (resultSet.next()){
                    empleado = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }

    @Override
    public ObservableList<Empleado> busquedaGeneral(Object obj) {
        ObservableList<Empleado> empleadoObservableList = FXCollections.observableArrayList();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "select * from empleado where " +
                    "id like '%"+obj+"%' or nombre like '%"+obj+"%' or paterno like '%"+obj+"%' or " +
                    "materno like '%"+obj+"%' or correo like '%"+obj+"%' or empleado_categoria like '%\"+obj+\"%';");
            ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                empleadoObservableList.add(buildObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleadoObservableList;
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_actualizar(?,?,?,?,?,?,?,?)}")) {
            builCallID(empleado.getId(), callableStatement);
            buildCallObject(empleado, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean eliminar(Object obj) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_eliminar(?)}")) {
            builCallID((Integer) obj, callableStatement);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Empleado buildObject(ResultSet resultSet) throws SQLException {
        Empleado empleado = new Empleado();
        empleadoCategoriaDao = new EmpleadoCategoria_DAO();
        empleado.setId(resultSet.getShort("id"));
        empleado.setNombre(resultSet.getString("nombre"));
        empleado.setPaterno(resultSet.getString("paterno"));
        empleado.setMaterno(resultSet.getString("materno"));
        empleado.setCorreo(resultSet.getString("correo"));
        empleado.setUsuario(resultSet.getString("usuario"));
        empleado.setContrasena(resultSet.getString("contrasena"));
        empleado.setEmpleadoCategoria(empleadoCategoriaDao.busquedaID(resultSet.getInt("empleado_categoria")));
        return empleado;
    }
    private static void builCallID(Integer id, CallableStatement callableStatement) throws SQLException {
        callableStatement.setInt("idV", id);
    }
    private static void buildCallObject(Empleado empleado, CallableStatement callableStatement) throws SQLException {
        callableStatement.setString("nombreV", empleado.getNombre());
        callableStatement.setString("paternoV", empleado.getPaterno());
        callableStatement.setString("maternoV", empleado.getMaterno());
        callableStatement.setString("correoV", empleado.getCorreo());
        callableStatement.setString("usuarioV", empleado.getUsuario());
        callableStatement.setString("contrasenaV", empleado.getContrasena());
        callableStatement.setInt("empleado_categoriaV", empleado.getEmpleadoCategoria().getId());
    }
    public Empleado validarSesion(String usuario, String contrasena) {
        Empleado empleado = new Empleado();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_validarSesion(?,?)}")){
            callableStatement.setString(1,usuario);
            callableStatement.setString(2,contrasena);
            try (ResultSet resultSet = callableStatement.executeQuery()){
                if (resultSet.next()){
                    empleado = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empleado;
    }
    public boolean validar(String usuario, String contrasena){
        boolean logeado=false;
        Empleado empleado = new Empleado();
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call empleado_validarSesion(?,?)}")){
            callableStatement.setString(1,usuario);
            callableStatement.setString(2,contrasena);
            try (ResultSet resultSet = callableStatement.executeQuery()){
                if (resultSet.next()){
                    empleado = buildObject(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return logeado;
    }
}
