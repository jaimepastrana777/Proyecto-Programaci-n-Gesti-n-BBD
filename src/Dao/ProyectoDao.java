package Dao;

import Model.Desarrollador;
import Model.Proyecto;
import jakarta.persistence.*;
import java.util.List;

public class ProyectoDao {
    private EntityManager em;

    public ProyectoDao(EntityManager em) {
        this.em = em;
    }
    public void insertarProyecto(Proyecto p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        System.out.println("Proyecto insertado: " + p.getNombre());
    }
    public void actualizar(Proyecto p) {
        em.getTransaction().begin();
        Proyecto existente = em.find(Proyecto.class, p.getId());
        if (existente != null) {
            existente.setNombre(p.getNombre());
            existente.setPresupuesto(p.getPresupuesto());
            existente.setLenguajePrincipal(p.getLenguajePrincipal());
        }
        em.getTransaction().commit();
        System.out.println("Proyecto actualizado con ID: " + p.getId());
    }
    public void borrar(long id) {
        em.getTransaction().begin();
        Proyecto p = em.find(Proyecto.class, id);
        if (p != null) {
            for (Desarrollador d : p.getDesarrolladores()) {
                d.getProyectos().remove(p);
            }
            p.getDesarrolladores().clear();
            em.remove(p);
        }
        em.getTransaction().commit();
        System.out.println("Proyecto borrado con ID: " + id);
    }
    public List<Object[]> obtenerNumeroDesarrolladoresPorProyecto() {
        TypedQuery<Proyecto> query = em.createQuery("SELECT p FROM Proyecto p", Proyecto.class);
        List<Proyecto> proyectos = query.getResultList();
        List<Object[]> resultado = new java.util.ArrayList<>();
        for (Proyecto p : proyectos) {
            int numDes = p.getDesarrolladores().size();
            resultado.add(new Object[]{p.getNombre(), numDes});
            System.out.println("Proyecto: " + p.getNombre() + " | Nº desarrolladores: " + numDes);
        }
        return resultado;
    }
    public List<Desarrollador> obtenerDesarrolladoresPorProyecto(long idProyecto) {
        Proyecto p = em.find(Proyecto.class, idProyecto);
        if (p != null) {
            List<Desarrollador> lista = p.getDesarrolladores();
            for (Desarrollador d : lista) {
                System.out.println("Desarrollador en proyecto " + idProyecto + ": " + d);
            }
            return lista;
        }
        return List.of();
    }
    public List<Proyecto> obtenerProyectosConMasDe5Desarrolladores() {
        TypedQuery<Proyecto> query = em.createQuery(
                "SELECT p FROM Proyecto p WHERE SIZE(p.desarrolladores) > 5", Proyecto.class);
        List<Proyecto> lista = query.getResultList();
        for (Proyecto p : lista) {
            System.out.println("Proyecto con más de 5 desarrolladores: " + p);
        }
        return lista;
    }
    public List<Proyecto> obtenerTop3Presupuesto() {
        TypedQuery<Proyecto> query = em.createQuery(
                "SELECT p FROM Proyecto p ORDER BY p.presupuesto DESC", Proyecto.class);
        query.setMaxResults(3);
        List<Proyecto> lista = query.getResultList();
        for (Proyecto p : lista) {
            System.out.println("Top presupuesto: " + p.getNombre() + " -> " + p.getPresupuesto() + "€");
        }
        return lista;
    }
    public Proyecto obtenerMasBaratoPorLenguaje(String lenguaje) {
        TypedQuery<Proyecto> query = em.createQuery(
                "SELECT p FROM Proyecto p WHERE p.lenguajePrincipal = :lenguaje ORDER BY p.presupuesto ASC",
                Proyecto.class);
        query.setParameter("lenguaje", lenguaje);
        query.setMaxResults(1);
        List<Proyecto> lista = query.getResultList();
        if (!lista.isEmpty()) {
            Proyecto p = lista.get(0);
            System.out.println("Proyecto más barato en " + lenguaje + ": " + p);
            return p;
        }
        return null;
    }
}