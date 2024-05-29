package org.example.lab6_2021104.daos;

import org.example.lab6_2021104.beans.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorService {
    private String jdbcURL = "jdbc:mysql://localhost:3306/lab6";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String SELECT_ACTORES_BY_PELICULA_ID = "SELECT a.id, a.nombre, a.apellido, a.anio_nacimiento, a.gano_oscar " +
            "FROM actor a " +
            "JOIN protagonista p ON a.id = p.id_actor " +
            "WHERE p.id_pelicula = ?";

    protected Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    public List<Actor> getActoresByPeliculaId(int idPelicula) {
        List<Actor> actores = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACTORES_BY_PELICULA_ID);) {
            preparedStatement.setInt(1, idPelicula);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int anioNacimiento = rs.getInt("anio_nacimiento");
                boolean ganoOscar = rs.getBoolean("gano_oscar");
                actores.add(new Actor(id, nombre, apellido, anioNacimiento, ganoOscar));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return actores;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
