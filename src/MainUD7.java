import Dao.DesarrolladorDao;
import Dao.ProyectoDao;
import Model.Desarrollador;
import Model.Proyecto;
import jakarta.persistence.*;
import java.util.List;

public class MainUD7 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db/proyectos.odb");
        EntityManager em = emf.createEntityManager();

        ProyectoDao pDao = new ProyectoDao(em);
        DesarrolladorDao dDao = new DesarrolladorDao(em);

        System.out.println("=== PROYECTO 3er TRIMESTRE - UD7 ===\n");

        Long totalProyectos = em.createQuery("SELECT COUNT(p) FROM Proyecto p", Long.class).getSingleResult();
        if (totalProyectos == 0) {
            System.out.println("--- Insertando datos de prueba ---");

            Proyecto p1  = new Proyecto("Sistema ERP", 50000.0, "Java");
            Proyecto p2  = new Proyecto("App Delivery", 12000.0, "Kotlin");
            Proyecto p3  = new Proyecto("Portal E-commerce", 25000.0, "JavaScript");
            Proyecto p4  = new Proyecto("Motor IA Clasificador", 80000.0, "Python");
            Proyecto p5  = new Proyecto("Dashboard Cripto", 15000.0, "TypeScript");
            Proyecto p6  = new Proyecto("Gestor Hospitalario", 45000.0, "Java");
            Proyecto p7  = new Proyecto("Red Social Gaming", 30000.0, "C#");
            Proyecto p8  = new Proyecto("App Finanzas Personales", 8000.0, "Swift");
            Proyecto p9  = new Proyecto("Sistema Logística", 60000.0, "Go");
            Proyecto p10 = new Proyecto("Analítica Big Data", 95000.0, "Python");

            pDao.insertarProyecto(p1); pDao.insertarProyecto(p2);
            pDao.insertarProyecto(p3); pDao.insertarProyecto(p4);
            pDao.insertarProyecto(p5); pDao.insertarProyecto(p6);
            pDao.insertarProyecto(p7); pDao.insertarProyecto(p8);
            pDao.insertarProyecto(p9); pDao.insertarProyecto(p10);

            Desarrollador d1  = new Desarrollador("Ana García", 5, 35000.0);
            Desarrollador d2  = new Desarrollador("Luis Pérez", 2, 20000.0);
            Desarrollador d3  = new Desarrollador("Marta Ruiz", 8, 50000.0);
            Desarrollador d4  = new Desarrollador("Carlos Soler", 1, 15000.0);
            Desarrollador d5  = new Desarrollador("Elena Beltrán", 12, 65000.0);
            Desarrollador d6  = new Desarrollador("Jorge Sanz", 4, 30000.0);
            Desarrollador d7  = new Desarrollador("Sofía Vega", 6, 40000.0);
            Desarrollador d8  = new Desarrollador("Pablo Lara", 3, 25000.0);
            Desarrollador d9  = new Desarrollador("Lucía Ortiz", 10, 55000.0);
            Desarrollador d10 = new Desarrollador("Iván Cano", 7, 42000.0);

            dDao.insertarDesarrollador(d1); dDao.insertarDesarrollador(d2);
            dDao.insertarDesarrollador(d3); dDao.insertarDesarrollador(d4);
            dDao.insertarDesarrollador(d5); dDao.insertarDesarrollador(d6);
            dDao.insertarDesarrollador(d7); dDao.insertarDesarrollador(d8);
            dDao.insertarDesarrollador(d9); dDao.insertarDesarrollador(d10);

            dDao.asignarDesarrollador(d1.getId(), p1.getId());
            dDao.asignarDesarrollador(d1.getId(), p6.getId());
            dDao.asignarDesarrollador(d2.getId(), p2.getId());
            dDao.asignarDesarrollador(d2.getId(), p3.getId());
            dDao.asignarDesarrollador(d3.getId(), p1.getId());
            dDao.asignarDesarrollador(d3.getId(), p4.getId());
            dDao.asignarDesarrollador(d3.getId(), p10.getId());
            dDao.asignarDesarrollador(d4.getId(), p2.getId());
            dDao.asignarDesarrollador(d5.getId(), p4.getId());
            dDao.asignarDesarrollador(d5.getId(), p9.getId());
            dDao.asignarDesarrollador(d5.getId(), p10.getId());
            dDao.asignarDesarrollador(d6.getId(), p6.getId());
            dDao.asignarDesarrollador(d6.getId(), p7.getId());
            dDao.asignarDesarrollador(d7.getId(), p3.getId());
            dDao.asignarDesarrollador(d7.getId(), p5.getId());
            dDao.asignarDesarrollador(d7.getId(), p10.getId());
            dDao.asignarDesarrollador(d8.getId(), p5.getId());
            dDao.asignarDesarrollador(d8.getId(), p8.getId());
            dDao.asignarDesarrollador(d9.getId(), p1.getId());
            dDao.asignarDesarrollador(d9.getId(), p9.getId());
            dDao.asignarDesarrollador(d9.getId(), p10.getId());
            dDao.asignarDesarrollador(d10.getId(), p4.getId());
            dDao.asignarDesarrollador(d10.getId(), p7.getId());
            dDao.asignarDesarrollador(d10.getId(), p10.getId());
            dDao.asignarDesarrollador(d2.getId(), p10.getId());
            dDao.asignarDesarrollador(d4.getId(), p10.getId());
            dDao.asignarDesarrollador(d6.getId(), p10.getId());
            dDao.asignarDesarrollador(d8.getId(), p10.getId());
            dDao.asignarDesarrollador(d3.getId(), p9.getId());
            dDao.asignarDesarrollador(d10.getId(), p9.getId());

        } else {
            System.out.println("(Datos ya cargados, se omite la inserción inicial)\n");
        }

        List<Proyecto> todosProyectos = em.createQuery("SELECT p FROM Proyecto p ORDER BY p.presupuesto ASC", Proyecto.class).getResultList();
        List<Desarrollador> todosDesarrolladores = dDao.obtenerTodos();

        long idPrimerProyecto    = todosProyectos.get(0).getId();
        long idSegundoProyecto   = todosProyectos.get(1).getId();
        long idPrimerDes         = todosDesarrolladores.get(0).getId();

        System.out.println("--- 6.a: Insertar un proyecto de prueba ---");
        Proyecto proyectoTest = new Proyecto("Proyecto Test", 5000.0, "Ruby");
        pDao.insertarProyecto(proyectoTest);

        System.out.println("\n--- 6.b: Actualizar ese proyecto ---");
        proyectoTest.setNombre("Proyecto Test Actualizado");
        proyectoTest.setPresupuesto(7500.0);
        pDao.actualizar(proyectoTest);

        System.out.println("\n--- 6.c: Borrar ese proyecto ---");
        pDao.borrar(proyectoTest.getId());

        System.out.println("\n--- 7.a: Insertar un desarrollador de prueba ---");
        Desarrollador desTest = new Desarrollador("Test Dev", 3, 28000.0);
        dDao.insertarDesarrollador(desTest);

        System.out.println("\n--- 7.b: Actualizar ese desarrollador ---");
        desTest.setNombre("Test Dev Actualizado");
        desTest.setAnyosExperiencia(4);
        dDao.actualizar(desTest);

        System.out.println("\n--- 7.c: Borrar ese desarrollador ---");
        dDao.borrar(desTest.getId());

        System.out.println("\n--- 7.d: Asignar desarrollador al proyecto ---");
        dDao.asignarDesarrollador(idPrimerDes, idSegundoProyecto);

        System.out.println("\n--- 7.e: Eliminar esa misma asignación ---");
        dDao.eliminarAsignacion(idPrimerDes, idSegundoProyecto);

        System.out.println("\n--- 6.d: Número de desarrolladores por proyecto ---");
        pDao.obtenerNumeroDesarrolladoresPorProyecto();

        System.out.println("\n--- 6.e: Desarrolladores del primer proyecto ---");
        pDao.obtenerDesarrolladoresPorProyecto(idPrimerProyecto);

        System.out.println("\n--- 6.f: Proyectos con más de 5 desarrolladores ---");
        pDao.obtenerProyectosConMasDe5Desarrolladores();

        System.out.println("\n--- 6.g: Top 3 proyectos por presupuesto ---");
        pDao.obtenerTop3Presupuesto();

        System.out.println("\n--- 6.h: Proyecto más barato en Python ---");
        pDao.obtenerMasBaratoPorLenguaje("Python");

        System.out.println("\n--- 7.f: Proyectos del primer desarrollador ---");
        dDao.obtenerProyectosPorDesarrollador(idPrimerDes);

        System.out.println("\n--- 7.g: Media de años de experiencia ---");
        dDao.obtenerMediaExperiencia();

        System.out.println("\n--- 7.h: Desarrolladores sin proyectos asignados ---");
        dDao.obtenerSinProyectos();

        System.out.println("\n=== FIN UD7 ===");

        em.close();
        emf.close();
    }
}