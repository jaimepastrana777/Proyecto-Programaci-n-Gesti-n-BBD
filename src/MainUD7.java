import Dao.DesarrolladorDao;
import Dao.ProyectoDao;
import Model.Desarrollador;
import Model.Proyecto;
import jakarta.persistence.*;

public class MainUD7 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db/proyectos.odb");
        EntityManager em = emf.createEntityManager();

        ProyectoDao pDao = new ProyectoDao(em);
        DesarrolladorDao dDao = new DesarrolladorDao(em);

        System.out.println("=== PROYECTO 3er TRIMESTRE - UD7 ===");


        Long totalProyectos = em.createQuery("SELECT COUNT(p) FROM Proyecto p", Long.class).getSingleResult();
        if (totalProyectos == 0) {
            System.out.println("\n--- Insertando datos de prueba ---");

            pDao.insertarProyecto(new Proyecto("Sistema ERP", 50000.0, "Java"));
            pDao.insertarProyecto(new Proyecto("App Delivery", 12000.0, "Kotlin"));
            pDao.insertarProyecto(new Proyecto("Portal E-commerce", 25000.0, "JavaScript"));
            pDao.insertarProyecto(new Proyecto("Motor IA Clasificador", 80000.0, "Python"));
            pDao.insertarProyecto(new Proyecto("Dashboard Cripto", 15000.0, "TypeScript"));
            pDao.insertarProyecto(new Proyecto("Gestor Hospitalario", 45000.0, "Java"));
            pDao.insertarProyecto(new Proyecto("Red Social Gaming", 30000.0, "C#"));
            pDao.insertarProyecto(new Proyecto("App Finanzas Personales", 8000.0, "Swift"));
            pDao.insertarProyecto(new Proyecto("Sistema Logística", 60000.0, "Go"));
            pDao.insertarProyecto(new Proyecto("Analítica Big Data", 95000.0, "Python"));

            dDao.insertarDesarrollador(new Desarrollador("Ana García", 5, 35000.0));
            dDao.insertarDesarrollador(new Desarrollador("Luis Pérez", 2, 20000.0));
            dDao.insertarDesarrollador(new Desarrollador("Marta Ruiz", 8, 50000.0));
            dDao.insertarDesarrollador(new Desarrollador("Carlos Soler", 1, 15000.0));
            dDao.insertarDesarrollador(new Desarrollador("Elena Beltrán", 12, 65000.0));
            dDao.insertarDesarrollador(new Desarrollador("Jorge Sanz", 4, 30000.0));
            dDao.insertarDesarrollador(new Desarrollador("Sofía Vega", 6, 40000.0));
            dDao.insertarDesarrollador(new Desarrollador("Pablo Lara", 3, 25000.0));
            dDao.insertarDesarrollador(new Desarrollador("Lucía Ortiz", 10, 55000.0));
            dDao.insertarDesarrollador(new Desarrollador("Iván Cano", 7, 42000.0));

            dDao.asignarDesarrollador(1, 1);
            dDao.asignarDesarrollador(1, 6);
            dDao.asignarDesarrollador(2, 2);
            dDao.asignarDesarrollador(2, 3);
            dDao.asignarDesarrollador(3, 1);
            dDao.asignarDesarrollador(3, 4);
            dDao.asignarDesarrollador(3, 10);
            dDao.asignarDesarrollador(4, 2);
            dDao.asignarDesarrollador(5, 4);
            dDao.asignarDesarrollador(5, 9);
            dDao.asignarDesarrollador(5, 10);
            dDao.asignarDesarrollador(6, 6);
            dDao.asignarDesarrollador(6, 7);
            dDao.asignarDesarrollador(7, 3);
            dDao.asignarDesarrollador(7, 5);
            dDao.asignarDesarrollador(7, 10);
            dDao.asignarDesarrollador(8, 5);
            dDao.asignarDesarrollador(8, 8);
            dDao.asignarDesarrollador(9, 1);
            dDao.asignarDesarrollador(9, 9);
            dDao.asignarDesarrollador(9, 10);
            dDao.asignarDesarrollador(10, 4);
            dDao.asignarDesarrollador(10, 7);
            dDao.asignarDesarrollador(10, 10);
            dDao.asignarDesarrollador(2, 10);
            dDao.asignarDesarrollador(4, 10);
            dDao.asignarDesarrollador(6, 10);
            dDao.asignarDesarrollador(8, 10);
            dDao.asignarDesarrollador(3, 9);
            dDao.asignarDesarrollador(10, 9);
        } else {
            System.out.println("\n(Datos ya cargados, se omite la inserción inicial)");
        }
        System.out.println("\n--- 6.a: Insertar un proyecto de prueba ---");
        Proyecto proyectoTest = new Proyecto("Proyecto Test", 5000.0, "Ruby");
        pDao.insertarProyecto(proyectoTest);
        long idPTest = proyectoTest.getId();

        System.out.println("\n--- 6.b: Actualizar ese proyecto ---");
        proyectoTest.setNombre("Proyecto Test Actualizado");
        proyectoTest.setPresupuesto(7500.0);
        pDao.actualizar(proyectoTest);

        System.out.println("\n--- 6.c: Borrar ese proyecto ---");
        pDao.borrar(idPTest);

        System.out.println("\n--- 7.a: Insertar un desarrollador de prueba ---");
        Desarrollador desTest = new Desarrollador("Test Dev", 3, 28000.0);
        dDao.insertarDesarrollador(desTest);
        long idDTest = desTest.getId();

        System.out.println("\n--- 7.b: Actualizar ese desarrollador ---");
        desTest.setNombre("Test Dev Actualizado");
        desTest.setAnyosExperiencia(4);
        dDao.actualizar(desTest);

        System.out.println("\n--- 7.c: Borrar ese desarrollador ---");
        dDao.borrar(idDTest);

        long idDesAsig = 1L;
        long idProjAsig = 3L;

        System.out.println("\n--- 7.d: Asignar desarrollador 1 al proyecto 3 ---");
        dDao.asignarDesarrollador(idDesAsig, idProjAsig);

        System.out.println("\n--- 7.e: Eliminar esa misma asignación ---");
        dDao.eliminarAsignacion(idDesAsig, idProjAsig);

        System.out.println("\n--- 6.d: Número de desarrolladores por proyecto ---");
        pDao.obtenerNumeroDesarrolladoresPorProyecto();

        System.out.println("\n--- 6.e: Desarrolladores del proyecto con ID 1 ---");
        pDao.obtenerDesarrolladoresPorProyecto(1L);

        System.out.println("\n--- 6.f: Proyectos con más de 5 desarrolladores ---");
        pDao.obtenerProyectosConMasDe5Desarrolladores();

        System.out.println("\n--- 6.g: Top 3 proyectos por presupuesto ---");
        pDao.obtenerTop3Presupuesto();

        System.out.println("\n--- 6.h: Proyecto más barato en Python ---");
        pDao.obtenerMasBaratoPorLenguaje("Python");

        System.out.println("\n--- 7.f: Proyectos del desarrollador con ID 1 ---");
        dDao.obtenerProyectosPorDesarrollador(1L);

        System.out.println("\n--- 7.g: Media de años de experiencia ---");
        dDao.obtenerMediaExperiencia();

        System.out.println("\n--- 7.h: Desarrolladores sin proyectos asignados ---");
        dDao.obtenerSinProyectos();

        System.out.println("\n=== FIN UD7 ===");

        em.close();
        emf.close();
    }
}