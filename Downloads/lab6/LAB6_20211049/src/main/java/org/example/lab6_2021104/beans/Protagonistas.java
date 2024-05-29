package org.example.lab6_2021104.beans;

public class Protagonistas {
    private int idPelicula;
    private int idActor;
    private Pelicula pelicula;
    private Actor actor;

    public Protagonistas(int idPelicula, int idActor, Pelicula pelicula, Actor actor) {
        this.idPelicula = idPelicula;
        this.idActor = idActor;
        this.pelicula = pelicula;
        this.actor = actor;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
