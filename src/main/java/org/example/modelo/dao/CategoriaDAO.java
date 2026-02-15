package org.example.modelo.dao;

import org.example.modelo.entidades.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private static final String SQL_INSERT = "INSERT INTO Categoria (nombreCategoria, descripcionCategoria) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE Categoria SET nombreCategoria = ?, descripcionCategoria = ? WHERE idCategoria = ?";
    private static final String SQL_DELETE = "DELETE FROM Categoria WHERE idCategoria = ?";
    private static final String SQL_SELECT = "SELECT * FROM Categoria WHERE idCategoria = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM Categoria";

    private Connection con;

    private void conectar() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/InventarioEliminar";
        String user = "root";
        String password = "TU_CONTRASENA";

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertar(Categoria c) throws SQLException {
        conectar();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, c.getNombreCategoria());
            ps.setString(2, c.getDescripcionCategoria());
            ps.executeUpdate();
        } finally {
            cerrarRecursos(ps);
        }
    }

    public void actualizar(Categoria c) throws SQLException {
        conectar();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, c.getNombreCategoria());
            ps.setString(2, c.getDescripcionCategoria());
            ps.setInt(3, c.getIdCategoria());
            ps.executeUpdate();
        } finally {
            cerrarRecursos(ps);
        }
    }

    // --- MÉTODO DELETE ---
    public void eliminar(int id) throws SQLException {
        conectar();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            cerrarRecursos(ps);
        }
    }

    // --- MÉTODO SELECT (Uno solo por ID) ---
    public Categoria seleccionarUno(int id) throws SQLException {
        conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Categoria c = null;
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = Categoria.builder()
                        .idCategoria(rs.getInt("idCategoria"))
                        .nombreCategoria(rs.getString("nombreCategoria"))
                        .descripcionCategoria(rs.getString("descripcionCategoria"))
                        .build();
            }
        } finally {
            if (rs != null) rs.close();
            cerrarRecursos(ps);
        }
        return c;
    }

    public List<Categoria> listarTodo() throws SQLException {
        conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Categoria> lista = new ArrayList<>();
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria c = Categoria.builder()
                        .idCategoria(rs.getInt("idCategoria"))
                        .nombreCategoria(rs.getString("nombreCategoria"))
                        .descripcionCategoria(rs.getString("descripcionCategoria"))
                        .build();
                lista.add(c);
            }
        } finally {
            if (rs != null) rs.close();
            cerrarRecursos(ps);
        }
        return lista;
    }

    private void cerrarRecursos(PreparedStatement ps) throws SQLException {
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    // --- EJEMPLO DE USO COMPLETO EN EL MAIN ---
    public static void main(String[] args) {
        CategoriaDAO dao = new CategoriaDAO();
        try {
            // 1. Insertar algo nuevo para la prueba
            Categoria temporal = Categoria.builder()
                    .nombreCategoria("Prueba Borrado")
                    .descripcionCategoria("Esta categoría será eliminada")
                    .build();
            dao.insertar(temporal);
            System.out.println("1. Categoría de prueba insertada.");

            // 2. Ver todas para encontrar el ID de la que acabamos de insertar
            List<Categoria> lista = dao.listarTodo();
            int idABorrar = lista.get(lista.size() - 1).getIdCategoria(); // Tomamos la última

            // 3. SELECT: Buscar esa categoría específica por su ID
            Categoria encontrada = dao.seleccionarUno(idABorrar);
            System.out.println("2. Seleccionamos la categoría: " + encontrada.getNombreCategoria());

            // 4. DELETE: Borrarla
            dao.eliminar(idABorrar);
            System.out.println("3. Categoría con ID " + idABorrar + " eliminada.");

            // 5. Final: Listar todo para confirmar que ya no existe
            System.out.println("--- Lista Final de Categorías ---");
            dao.listarTodo().forEach(cat -> System.out.println("ID: " + cat.getIdCategoria() + " - " + cat.getNombreCategoria()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}