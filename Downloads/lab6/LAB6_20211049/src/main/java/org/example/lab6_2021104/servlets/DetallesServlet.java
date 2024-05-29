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

@WebServlet("/detallesServlet")
public class DetallesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
        Pelicula pelicula = obtenerPelicula(idPelicula);

        request.setAttribute("pelicula", pelicula);
        request.getRequestDispatcher("viewPelicula.jsp").forward(request, response);
    }

    private Pelicula obtenerPelicula(int idPelicula) {
        Pelicula pelicula = null;

        try {
            // Establecer conexión con la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", "Password");

            // Preparar la consulta SQL
            String query = "SELECT p.idPelicula, p.titulo, p.director, p.anoPublicacion, p.rating, p.boxOffice, g.nombre AS genero " +
                    "FROM Pelicula p " +
                    "INNER JOIN Genero g ON p.idGenero = g.idGenero " +
                    "WHERE p.idPelicula = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idPelicula);

            // Ejecutar la consulta y obtener los resultados
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pelicula = new Pelicula();
                pelicula.setIdPelicula(rs.getInt("idPelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setDirector(rs.getString("director"));
                pelicula.setAnoPublicacion(rs.getInt("anoPublicacion"));
                pelicula.setRating(rs.getDouble("rating"));
                pelicula.setBoxOffice(rs.getDouble("boxOffice"));
                pelicula.setGenero(new Genero(rs.getString("genero")));
            }

            // Cerrar conexiones
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return pelicula;
    }
}
