<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.lab6_2021104.beans.Pelicula" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${pelicula.titulo}</title>
</head>
<body>
<h1>${pelicula.titulo}</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Título</th>
        <th>Director</th>
        <th>Año de Publicación</th>
        <th>Rating</th>
        <th>Box Office</th>
        <th>Género</th>
        <th>Actores</th>
    </tr>
    <tr>
        <td>${pelicula.idPelicula}</td>
        <td>${pelicula.titulo}</td>
        <td>${pelicula.director}</td>
        <td>${pelicula.anoPublicacion}</td>
        <td>${pelicula.rating}/10</td>
        <td>$${pelicula.boxOffice}</td>
        <td>${pelicula.genero.nombre}</td>
        <td><a href="ActorServlet?idPelicula=${pelicula.idPelicula}">Ver Actores</a></td>
    </tr>
</table>
</body>
</html>
