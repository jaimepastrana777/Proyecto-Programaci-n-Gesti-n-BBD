package Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Proyecto {

    @Id
    @GeneratedValue
    private long id;

    private String nombre;
    private double presupuesto;
    private String lenguajePrincipal;

    @ManyToMany
    private List<Desarrollador> desarrolladores = new ArrayList<>();

    public Proyecto() {}

    public Proyecto(String nombre, double presupuesto, String lenguajePrincipal) {
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getPresupuesto() { return presupuesto; }
    public void setPresupuesto(double presupuesto) { this.presupuesto = presupuesto; }
    public String getLenguajePrincipal() { return lenguajePrincipal; }
    public void setLenguajePrincipal(String lenguajePrincipal) { this.lenguajePrincipal = lenguajePrincipal; }
    public List<Desarrollador> getDesarrolladores() { return desarrolladores; }
    public void setDesarrolladores(List<Desarrollador> desarrolladores) { this.desarrolladores = desarrolladores; }

    @Override
    public String toString() {
        return "Proyecto{id=" + id + ", nombre='" + nombre + "', presupuesto=" + presupuesto +
                ", lenguaje='" + lenguajePrincipal + "', numDesarrolladores=" + desarrolladores.size() + "}";
    }
}