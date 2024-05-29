<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="org.example.lab6_2021104.beans.Pelicula" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Películas</title>
</head>
<body>
<h1>Lista de Películas</h1>

<form action="PeliculaServlet" method="get">
    <label for="busqueda">Buscar:</label>
    <input type="text" id="busqueda" name="busqueda" value="${param.busqueda}">
    <input type="submit" value="Buscar">
</form>

<table>
    <tr>
        <th>Título</th>
        <th>Director</th>
        <th>Año de Publicación</th>
        <th>Rating</th>
        <th>Box Office</th>
        <th>Género</th>
        <th>Actores</th>
    </tr>
    <c:forEach var="pelicula" items="${peliculas}">
        <tr>
            <td><a href="DetallesServlet?idPelicula=${pelicula.idPelicula}">${pelicula.titulo}</a></td>
            <td>${pelicula.director}</td>
            <td>${pelicula.anoPublicacion}</td>
            <td>${pelicula.rating}/10</td>
            <td>$${pelicula.boxOffice}</td>
            <td>${pelicula.genero.nombre}</td>
            <td><a href="ActorServlet?idPelicula=${pelicula.idPelicula}">Ver Actores</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

