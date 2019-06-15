package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion implements AutoCloseable {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Conexion() throws SQLException {
        connect();
    }

    private void connect() throws SQLException {
        // TODO: program this method
        try {
            String url = "jdbc:mysql://localhost/bdboletaje?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url,
                    "root", "contrasena");
            System.out.println("Conexión exitosa a bdboletaje ");

        } catch (SQLException ex) {
            connection = null;
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());
        }
    }

    /**
     * Close the connection to the database if it is still open.
     *
     */
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }

}