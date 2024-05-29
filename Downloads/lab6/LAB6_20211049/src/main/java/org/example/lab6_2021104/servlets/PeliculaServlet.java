package org.example.lab6_2021104.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.lab6_2021104.beans.Genero;
import org.example.lab6_2021104.beans.Pelicula;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/PeliculaServlet")
public class PeliculaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String busqueda = request.getParameter("busqueda");
        List<Pelicula> peliculas = obtenerPeliculas(busqueda);

        request.setAttribute("peliculas", peliculas);
        request.getRequestDispatcher("listaPelicula.jsp").forward(request, response);
    }

    private List<Pelicula> obtenerPeliculas(String busqueda) {
        List<Pelicula> peliculas = new ArrayList<>();

        try {
            // Establecer conexión con la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", "Password");

            // Preparar la consulta SQL
            String query = "SELECT p.idPelicula, p.titulo, p.director, p.anoPublicacion, p.rating, p.boxOffice, g.nombre AS genero " +
                    "FROM Pelicula p " +
                    "INNER JOIN Genero g ON p.idGenero = g.idGenero " +
                    "WHERE p.titulo LIKE ? " +
                    "ORDER BY p.rating DESC, p.boxOffice DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + busqueda + "%");

            // Ejecutar la consulta y obtener los resultados
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idPelicula = rs.getInt("idPelicula");
                String titulo = rs.getString("titulo");
                String director = rs.getString("director");
                int anoPublicacion = rs.getInt("anoPublicacion");
                double rating = rs.getDouble("rating");
                double boxOffice = rs.getDouble("boxOffice");
                String nombreGenero = rs.getString("genero");
                Genero genero = new Genero(nombreGenero);
                Pelicula pelicula = new Pelicula(idPelicula, titulo, director, anoPublicacion, rating, boxOffice, genero);
                peliculas.add(pelicula);
            }

            // Cerrar conexiones
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return peliculas;
    }
}
