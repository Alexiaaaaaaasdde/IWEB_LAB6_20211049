package org.example.lab6_2021104.daos;

import org.example.lab6_2021104.beans.Genero;
import org.example.lab6_2021104.beans.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaService {
    private String jdbcURL = "jdbc:mysql://localhost:3306/lab6";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String SELECT_ALL_PELICULAS = "SELECT p.id, p.titulo, p.director, p.anio, p.rating, p.box_office, g.nombre AS genero FROM pelicula p JOIN genero g ON p.genero_id = g.id";
    private static final String SELECT_PELICULA_BY_ID = "SELECT p.id, p.titulo, p.director, p.anio, p.rating, p.box_office, g.nombre AS genero FROM pelicula p JOIN genero g ON p.genero_id = g.id WHERE p.id = ?";

    protected Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        return connection;
    }

    public List<Pelicula> getPeliculas() {
        List<Pelicula> peliculas = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PELICULAS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String director = rs.getString("director");
                int anio = rs.getInt("anio");
                double rating = rs.getDouble("rating");
                double boxOffice = rs.getDouble("box_office");
                String nombreGenero = rs.getString("genero");
                Genero genero = new Genero(nombreGenero);
                peliculas.add(new Pelicula(id, titulo, director, anio, rating, boxOffice, genero));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return peliculas;
    }

    public Pelicula getPeliculaById(int id) {
        Pelicula pelicula = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PELICULA_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String titulo = rs.getString("titulo");
                String director = rs.getString("director");
                int anio = rs.getInt("anio");
                double rating = rs.getDouble("rating");
                double boxOffice = rs.getDouble("box_office");
                String nombreGenero = rs.getString("genero");
                Genero genero = new Genero(nombreGenero);
                pelicula = new Pelicula(id, titulo, director, anio, rating, boxOffice, genero);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return pelicula;
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
