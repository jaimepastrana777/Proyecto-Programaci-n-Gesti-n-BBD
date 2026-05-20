package Dao;
import Model.Desarrollador;
import Model.Proyecto;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DesarrolladorDao {
    private EntityManager em;

    public DesarrolladorDao(EntityManager em) {
        this.em = em;
    }
    public void insertarDesarrollador(Desarrollador d) {
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
        System.out.println("Desarrollador insertado: " + d.getNombre() + " (ID: " + d.getId() + ")");
    }
    public void actualizar(Desarrollador d) {
        em.getTransaction().begin();
        Desarrollador existente = em.find(Desarrollador.class, d.getId());
        if (existente != null) {
            existente.setNombre(d.getNombre());
            existente.setAnyosExperiencia(d.getAnyosExperiencia());
            existente.setSalario(d.getSalario());
        }
        em.getTransaction().commit();
        System.out.println("Desarrollador actualizado con ID: " + d.getId());
    }
    public void borrar(long id) {
        em.getTransaction().begin();
        Desarrollador d = em.find(Desarrollador.class, id);
        if (d != null) {
            for (Proyecto p : new ArrayList<>(d.getProyectos())) {
                p.getDesarrolladores().remove(d);
            }
            d.getProyectos().clear();
            em.remove(d);
        }
        em.getTransaction().commit();
        System.out.println("Desarrollador borrado con ID: " + id);
    }
    public void asignarDesarrollador(long idDesarrollador, long idProyecto) {
        em.getTransaction().begin();
        Desarrollador d = em.find(Desarrollador.class, idDesarrollador);
        Proyecto p = em.find(Proyecto.class, idProyecto);
        if (d != null && p != null && !p.getDesarrolladores().contains(d)) {
            p.getDesarrolladores().add(d);
            d.getProyectos().add(p);
            System.out.println("Asignado: " + d.getNombre() + " -> " + p.getNombre());
        }
        em.getTransaction().commit();
    }
    public void eliminarAsignacion(long idDesarrollador, long idProyecto) {
        em.getTransaction().begin();
        Desarrollador d = em.find(Desarrollador.class, idDesarrollador);
        Proyecto p = em.find(Proyecto.class, idProyecto);
        if (d != null && p != null) {
            p.getDesarrolladores().remove(d);
            d.getProyectos().remove(p);
        }
        em.getTransaction().commit();
        System.out.println("Asignación eliminada: Desarrollador " + idDesarrollador + " -> Proyecto " + idProyecto);
    }
    public List<Proyecto> obtenerProyectosPorDesarrollador(long idDesarrollador) {
        Desarrollador d = em.find(Desarrollador.class, idDesarrollador);
        if (d != null) {
            List<Proyecto> lista = d.getProyectos();
            if (lista.isEmpty()) {
                System.out.println("El desarrollador " + idDesarrollador + " no tiene proyectos asignados.");
            }
            for (Proyecto p : lista) {
                System.out.println("Proyecto del desarrollador " + idDesarrollador + ": " + p);
            }
            return lista;
        }
        System.out.println("Desarrollador con ID " + idDesarrollador + " no encontrado.");
        return List.of();
    }
    public double obtenerMediaExperiencia() {
        Query query = em.createQuery("SELECT AVG(d.anyosExperiencia) FROM Desarrollador d");
        Object resultado = query.getSingleResult();
        double media = resultado != null ? ((Number) resultado).doubleValue() : 0;
        System.out.println("Media de años de experiencia: " + media);
        return media;
    }
    public List<Desarrollador> obtenerSinProyectos() {
        TypedQuery<Desarrollador> query = em.createQuery(
                "SELECT d FROM Desarrollador d", Desarrollador.class);
        List<Desarrollador> todos = query.getResultList();
        List<Desarrollador> sinProyectos = new ArrayList<>();
        for (Desarrollador d : todos) {
            if (d.getProyectos().isEmpty()) {
                sinProyectos.add(d);
                System.out.println("Desarrollador sin proyectos: " + d);
            }
        }
        if (sinProyectos.isEmpty()) {
            System.out.println("(Todos los desarrolladores tienen al menos un proyecto asignado)");
        }
        return sinProyectos;
    }
    public List<Desarrollador> obtenerTodos() {
        return em.createQuery("SELECT d FROM Desarrollador d", Desarrollador.class).getResultList();
    }
}