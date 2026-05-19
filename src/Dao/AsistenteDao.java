package Dao;
import Model.Asistente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenteDao {
    private Connection conn;

    public AsistenteDao(Connection conn) {
        this.conn = conn;
    }
    public void insertar(Asistente a) throws SQLException {
        String sql = "INSERT INTO asistentes (nombre, email, edad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getEmail());
            ps.setInt(3, a.getEdad());
            ps.executeUpdate();
            System.out.println("Asistente insertado: " + a.getNombre());
        }
    }
    public void actualizar(Asistente a) throws SQLException {
        String sql = "UPDATE asistentes SET nombre=?, email=?, edad=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getEmail());
            ps.setInt(3, a.getEdad());
            ps.setInt(4, a.getId());
            ps.executeUpdate();
            System.out.println("Asistente actualizado con ID: " + a.getId());
        }
    }

    public void borrar(int id) throws SQLException {
        String sqlInscripciones = "DELETE FROM inscripciones WHERE asistente_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlInscripciones)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        String sql = "DELETE FROM asistentes WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Asistente borrado con ID: " + id);
        }
    }
    public void inscribir(int idAsistente, int idEvento, String fecha) throws SQLException {
        String sql = "INSERT INTO inscripciones (asistente_id, evento_id, fecha_inscripcion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAsistente);
            ps.setInt(2, idEvento);
            ps.setDate(3, Date.valueOf(fecha));
            ps.executeUpdate();
            System.out.println("Inscripción añadida: Asistente " + idAsistente + " -> Evento " + idEvento);
        }
    }
    public void eliminarInscripcion(int idAsistente, int idEvento) throws SQLException {
        String sql = "DELETE FROM inscripciones WHERE asistente_id = ? AND evento_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAsistente);
            ps.setInt(2, idEvento);
            ps.executeUpdate();
            System.out.println("Inscripción eliminada: Asistente " + idAsistente + " -> Evento " + idEvento);
        }
    }
    public List<Object[]> listarGastoTotal() throws SQLException {
        String sql = "SELECT a.nombre, COALESCE(SUM(e.precio), 0) AS gasto_total " +
                "FROM asistentes a LEFT JOIN inscripciones i ON a.id = i.asistente_id " +
                "LEFT JOIN eventos e ON i.evento_id = e.id " +
                "GROUP BY a.id, a.nombre";
        List<Object[]> resultado = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double gasto = rs.getDouble("gasto_total");
                resultado.add(new Object[]{nombre, gasto});
                System.out.println("Asistente: " + nombre + " | Gasto total: " + gasto + "€");
            }
        }
        return resultado;
    }
    public double obtenerEdadMedia() throws SQLException {
        String sql = "SELECT AVG(edad) FROM asistentes";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                double media = rs.getDouble(1);
                System.out.println("Edad media de asistentes: " + media);
                return media;
            }
        }
        return 0;
    }
    public List<Asistente> obtenerSinInscripciones() throws SQLException {
        String sql = "SELECT id, nombre, email, edad FROM asistentes " +
                "WHERE id NOT IN (SELECT DISTINCT asistente_id FROM inscripciones)";
        List<Asistente> lista = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Asistente a = new Asistente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getInt("edad")
                );
                lista.add(a);
                System.out.println("Asistente sin inscripciones: " + a);
            }
        }
        return lista;
    }
}