
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.lab6_2021104.beans.Actor" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>${pelicula.titulo} - Actores</title>
</head>
<body>
<h1>Actores de la película: ${pelicula.titulo}</h1>

<table>
    <tr>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Año de Nacimiento</th>
        <th>Premio Oscar</th>
    </tr>
    <c:forEach var="actor" items="${actores}">
        <tr>
            <td>${actor.nombre}</td>
            <td>${actor.apellido}</td>
            <td>${actor.anoNacimiento}</td>
            <td>${actor.premioOscar ? "Sí" : "No"}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>