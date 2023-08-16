package com.muebleriamontana.conexionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String url = "jdbc:mysql://localhost:3306/inventario_montana";
    private static String user = "root";
    private static String password = ".";
    private static String password2 = "K4vn33/9825u16";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password2);
    }

}
