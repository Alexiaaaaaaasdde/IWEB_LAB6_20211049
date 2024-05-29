package org.example.lab6_2021104.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.lab6_2021104.beans.Actor;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/actorServlet")
public class ActorServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPelicula = Integer.parseInt(request.getParameter("idPelicula"));
        List<Actor> actores = obtenerActores(idPelicula);

        request.setAttribute("actores", actores);
        request.getRequestDispatcher("listaActores.jsp").forward(request, response);
    }

    private List<Actor> obtenerActores(int idPelicula) {
        List<Actor> actores = new ArrayList<>();

        try {
            // Establecer conexión con la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab6", "root", "Password");

            // Preparar la consulta SQL
            String query = "SELECT a.idActor, a.Nombre, a.Apellido, a.anoNacimiento, a.premioOscar " +
                    "FROM Actor a " +
                    "INNER JOIN Protagonistas p ON a.idActor = p.idActor " +
                    "WHERE p.idPelicula = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idPelicula);

            // Ejecutar la consulta y obtener los resultados
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setIdActor(rs.getInt("idActor"));
                actor.setNombre(rs.getString("Nombre"));
                actor.setApellido(rs.getString("Apellido"));
                actor.setAnoNacimiento(rs.getInt("anoNacimiento"));
                actor.setPremioOscar(rs.getBoolean("premioOscar"));
                actores.add(actor);
            }

            // Cerrar conexiones
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return actores;
    }
}

