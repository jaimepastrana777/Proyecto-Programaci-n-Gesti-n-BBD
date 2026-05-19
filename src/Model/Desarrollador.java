package Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Desarrollador {

    @Id
    @GeneratedValue
    private long id;

    private String nombre;
    private int anyosExperiencia;
    private double salario;

    @ManyToMany(mappedBy = "desarrolladores")
    private List<Proyecto> proyectos = new ArrayList<>();

    public Desarrollador() {}

    public Desarrollador(String nombre, int anyosExperiencia, double salario) {
        this.nombre = nombre;
        this.anyosExperiencia = anyosExperiencia;
        this.salario = salario;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getAnyosExperiencia() { return anyosExperiencia; }
    public void setAnyosExperiencia(int anyosExperiencia) { this.anyosExperiencia = anyosExperiencia; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyectos(List<Proyecto> proyectos) { this.proyectos = proyectos; }

    @Override
    public String toString() {
        return "Desarrollador{id=" + id + ", nombre='" + nombre + "', anyosExp=" + anyosExperiencia +
                ", salario=" + salario + "}";
    }
}