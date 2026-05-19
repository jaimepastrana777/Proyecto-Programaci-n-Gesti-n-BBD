package Model;

import java.time.LocalDate;

public class Evento {
    private int id;
    private String nombre;
    private String ubicacion;
    private LocalDate fecha;
    private double precio;

    public Evento() {}

    public Evento(int id, String nombre, String ubicacion, LocalDate fecha, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.precio = precio;
    }

    public Evento(String nombre, String ubicacion, LocalDate fecha, double precio) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return "Evento{id=" + id + ", nombre='" + nombre + "', ubicacion='" + ubicacion + "', fecha=" + fecha + ", precio=" + precio + "}";
    }
}