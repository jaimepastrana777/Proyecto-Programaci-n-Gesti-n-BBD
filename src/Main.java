import Config.DatabaseConnection;
import Dao.AsistenteDao;
import Dao.EventoDao;
import Model.Asistente;
import Model.Evento;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("=== PROYECTO 3er TRIMESTRE - UD6 ===");

            EventoDao eDao = new EventoDao(conn);
            AsistenteDao aDao = new AsistenteDao(conn);

            System.out.println("\n--- 2.a: Insertar un evento ---");
            Evento eventoTest = new Evento("Evento Test", "Sevilla", LocalDate.of(2026, 8, 1), 20.00);
            eDao.insertar(eventoTest);

            eventoTest.setId(obtenerUltimoIdEvento(conn));

            System.out.println("\n--- 2.b: Actualizar el evento ---");
            eventoTest.setNombre("Evento Test Actualizado");
            eventoTest.setPrecio(35.00);
            eDao.actualizar(eventoTest);

            System.out.println("\n--- 2.c: Borrar el evento ---");
            eDao.borrar(eventoTest.getId());

            System.out.println("\n--- 3.a: Insertar un asistente ---");
            Asistente asistenteTest = new Asistente("Test User", "test@example.com", 21);
            aDao.insertar(asistenteTest);

            asistenteTest.setId(obtenerUltimoIdAsistente(conn));

            System.out.println("\n--- 3.b: Actualizar el asistente ---");
            asistenteTest.setNombre("Test User Actualizado");
            asistenteTest.setEdad(22);
            aDao.actualizar(asistenteTest);

            System.out.println("\n--- 3.c: Borrar el asistente ---");
            aDao.borrar(asistenteTest.getId());

            System.out.println("\n--- 3.d: Inscribir asistente 1 en evento 2 ---");
            int idAsistente = 1;
            int idEvento = 2;
            String fechaInscripcion = "2026-05-01";

            if (!existeInscripcion(conn, idAsistente, idEvento)) {
                aDao.inscribir(idAsistente, idEvento, fechaInscripcion);

                System.out.println("\n--- 3.e: Eliminar esa misma inscripción ---");
                aDao.eliminarInscripcion(idAsistente, idEvento);
            } else {
                System.out.println("La inscripción ya existe, se omite para no duplicar.");
            }

            System.out.println("\n--- 2.d: Todos los eventos con su número de asistentes ---");
            eDao.listarConAsistentes();

            System.out.println("\n--- 2.e: Asistentes del evento con ID 1 ---");
            eDao.obtenerAsistentesPorEvento(1);

            System.out.println("\n--- 2.f: Eventos con más de 2 asistentes ---");
            eDao.obtenerEventosConMasDe2Asistentes();

            System.out.println("\n--- 2.g: Top 3 eventos con más ingresos ---");
            eDao.obtenerTop3Ingresos();

            System.out.println("\n--- 2.h: Evento más caro en Madrid ---");
            eDao.obtenerMasCaroPorUbicacion("Madrid");

            System.out.println("\n--- 3.f: Gasto total por asistente ---");
            aDao.listarGastoTotal();

            System.out.println("\n--- 3.g: Edad media de los asistentes ---");
            aDao.obtenerEdadMedia();

            System.out.println("\n--- 3.h: Asistentes que no se han inscrito a ningún evento ---");
            aDao.obtenerSinInscripciones();

            System.out.println("\n=== FIN UD6 ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static int obtenerUltimoIdEvento(Connection conn) throws Exception {
        String sql = "SELECT MAX(id) FROM eventos";
        try (java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }
    private static int obtenerUltimoIdAsistente(Connection conn) throws Exception {
        String sql = "SELECT MAX(id) FROM asistentes";
        try (java.sql.Statement st = conn.createStatement();
             java.sql.ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    private static boolean existeInscripcion(Connection conn, int idAsistente, int idEvento) throws Exception {
        String sql = "SELECT COUNT(*) FROM inscripciones WHERE asistente_id = ? AND evento_id = ?";
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAsistente);
            ps.setInt(2, idEvento);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        }
        return false;
    }
}