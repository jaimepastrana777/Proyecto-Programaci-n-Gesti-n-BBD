package Dao;

import Model.Asistente;
import Model.Evento;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoDao {
    private Connection conn;

    public EventoDao(Connection conn) {
        this.conn = conn;
    }
    public void insertar(Evento e) throws SQLException {
        String sql = "INSERT INTO eventos (nombre, ubicacion, fecha, precio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getUbicacion());
            ps.setDate(3, Date.valueOf(e.getFecha()));
            ps.setDouble(4, e.getPrecio());
            ps.executeUpdate();
            System.out.println("Evento insertado: " + e.getNombre());
        }
    }
    public void actualizar(Evento e) throws SQLException {
        String sql = "UPDATE eventos SET nombre=?, ubicacion=?, fecha=?, precio=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getUbicacion());
            ps.setDate(3, Date.valueOf(e.getFecha()));
            ps.setDouble(4, e.getPrecio());
            ps.setInt(5, e.getId());
            ps.executeUpdate();
            System.out.println("Evento actualizado con ID: " + e.getId());
        }
    }
    public void borrar(int id) throws SQLException {
        String sqlInscripciones = "DELETE FROM inscripciones WHERE evento_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlInscripciones)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        String sql = "DELETE FROM eventos WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Evento borrado con ID: " + id);
        }
    }

    public List<Object[]> listarConAsistentes() throws SQLException {
        String sql = "SELECT e.nombre, COUNT(i.asistente_id) AS total_asistentes " +
                "FROM eventos e LEFT JOIN inscripciones i ON e.id = i.evento_id " +
                "GROUP BY e.id, e.nombre";
        List<Object[]> resultado = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int total = rs.getInt("total_asistentes");
                resultado.add(new Object[]{nombre, total});
                System.out.println("Evento: " + nombre + " | Total Asistentes: " + total);
            }
        }
        return resultado;
    }
    public List<Asistente> obtenerAsistentesPorEvento(int idEvento) throws SQLException {
        String sql = "SELECT a.id, a.nombre, a.email, a.edad " +
                "FROM asistentes a JOIN inscripciones i ON a.id = i.asistente_id " +
                "WHERE i.evento_id = ?";
        List<Asistente> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Asistente a = new Asistente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getInt("edad")
                );
                lista.add(a);
                System.out.println("Asistente en evento " + idEvento + ": " + a);
            }
        }
        return lista;
    }
    public List<Evento> obtenerEventosConMasDe2Asistentes() throws SQLException {
        String sql = "SELECT e.id, e.nombre, e.ubicacion, e.fecha, e.precio " +
                "FROM eventos e JOIN inscripciones i ON e.id = i.evento_id " +
                "GROUP BY e.id, e.nombre, e.ubicacion, e.fecha, e.precio " +
                "HAVING COUNT(i.asistente_id) > 2";
        List<Evento> lista = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Evento e = new Evento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("ubicacion"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("precio")
                );
                lista.add(e);
                System.out.println("Evento con más de 2 asistentes: " + e);
            }
        }
        return lista;
    }
    public List<Object[]> obtenerTop3Ingresos() throws SQLException {
        String sql = "SELECT e.nombre, (e.precio * COUNT(i.asistente_id)) AS ingresos " +
                "FROM eventos e JOIN inscripciones i ON e.id = i.evento_id " +
                "GROUP BY e.id, e.nombre, e.precio " +
                "ORDER BY ingresos DESC LIMIT 3";
        List<Object[]> resultado = new ArrayList<>();
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double ingresos = rs.getDouble("ingresos");
                resultado.add(new Object[]{nombre, ingresos});
                System.out.println("Top Ingreso: " + nombre + " -> " + ingresos + "€");
            }
        }
        return resultado;
    }
    public Evento obtenerMasCaroPorUbicacion(String ubicacion) throws SQLException {
        String sql = "SELECT id, nombre, ubicacion, fecha, precio " +
                "FROM eventos WHERE ubicacion = ? ORDER BY precio DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ubicacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Evento e = new Evento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("ubicacion"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("precio")
                );
                System.out.println("Evento más caro en " + ubicacion + ": " + e);
                return e;
            }
        }
        return null;
    }
}