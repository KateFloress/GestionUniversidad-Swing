package baseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class GestionBD {

    // 1. Validar usuario en VentanaLogeo
    public boolean buscarUsuarios(String usuario, String password, String cargo) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ? AND cargo = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, usuario);
            pst.setString(2, password);
            pst.setString(3, cargo);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
            return false;
        }
    }

    // 2. Obtener módulos que imparte un profesor concreto
    public ArrayList<String> obtenerModulosProfesor(String profesorUsuario) {
        ArrayList<String> lista = new ArrayList<>();
        String sql = "SELECT nombre_modulo FROM modulos WHERE profesor_usuario = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, profesorUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("nombre_modulo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. Obtener alumnos matriculados en un módulo para VentanaProfe
    public DefaultTableModel obtenerAlumnosPorModulo(String nombreModulo) {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID Matrícula", "Alumnos", "Notas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = "SELECT m.id_matricula, m.alumno_usuario, m.nota " +
                     "FROM matriculas m JOIN modulos mo ON m.id_modulo = mo.id_modulo " +
                     "WHERE mo.nombre_modulo = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, nombreModulo);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object nota = rs.getObject("nota") != null ? rs.getDouble("nota") : "Sin calificar";
                    modelo.addRow(new Object[]{
                        rs.getInt("id_matricula"),
                        rs.getString("alumno_usuario"),
                        nota
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }

    // 4. Guardar o actualizar la nota de un alumno
    public boolean actualizarNota(int idMatricula, double nuevaNota) {
        String sql = "UPDATE matriculas SET nota = ? WHERE id_matricula = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setDouble(1, nuevaNota);
            pst.setInt(2, idMatricula);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Eliminar la nota de un alumno (dejarla en NULL)
    public boolean eliminarNota(int idMatricula) {
        String sql = "UPDATE matriculas SET nota = NULL WHERE id_matricula = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, idMatricula);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Obtener módulos en los que está matriculado un alumno
    public ArrayList<String> obtenerModulosAlumno(String alumnoUsuario) {
        ArrayList<String> lista = new ArrayList<>();
        String sql = "SELECT mo.nombre_modulo FROM matriculas m " +
                     "JOIN modulos mo ON m.id_modulo = mo.id_modulo " +
                     "WHERE m.alumno_usuario = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, alumnoUsuario);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("nombre_modulo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 7. Obtener las notas de un alumno para VentanAlum
    public DefaultTableModel obtenerNotasAlumno(String alumnoUsuario, String filtroModulo) {
        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Módulo", "Profesor/a", "Nota"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = "SELECT mo.nombre_modulo, mo.profesor_usuario, m.nota " +
                     "FROM matriculas m JOIN modulos mo ON m.id_modulo = mo.id_modulo " +
                     "WHERE m.alumno_usuario = ?";

        if (!filtroModulo.equals("Todos los módulos")) {
            sql += " AND mo.nombre_modulo = ?";
        }

        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, alumnoUsuario);
            if (!filtroModulo.equals("Todos los módulos")) {
                pst.setString(2, filtroModulo);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Object nota = rs.getObject("nota") != null ? rs.getDouble("nota") : "Pendiente";
                    modelo.addRow(new Object[]{
                        rs.getString("nombre_modulo"),
                        rs.getString("profesor_usuario"),
                        nota
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }
}
