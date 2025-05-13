package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Ingrediente;
import DataBase.Conexao;

public class IngredienteDAO {
    
    public static boolean inserir(Ingrediente ingrediente) {
        String sql = "INSERT INTO \"INGREDIENTES\" (\"Cod-ingred\", \"Nome-ingred\") VALUES (?, ?)";
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, ingrediente.getCodIngrediente());
            stmt.setString(2, ingrediente.getNome());
            
            int linhasAfetadas = stmt.executeUpdate();
            conn.commit();
            
            System.out.println("DEBUG: Ingrediente inserido com sucesso. Linhas afetadas: " + linhasAfetadas);
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fazer rollback: " + ex.getMessage());
            }
            System.out.println("Erro ao inserir ingrediente: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    // Não fechamos a conexão aqui para evitar problemas com o pool de conexões
                }
            } catch (SQLException e) {
                System.out.println("Erro ao verificar estado da conexão: " + e.getMessage());
            }
        }
    }
    
    public static Ingrediente buscar(int codIngrediente) {
        String sql = "SELECT * FROM \"INGREDIENTES\" WHERE \"Cod-ingred\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codIngrediente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ingrediente(
                        rs.getInt("Cod-ingred"),
                        rs.getString("Nome-ingred"),
                        "" // Descrição não existe na tabela
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ingrediente: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Ingrediente> listarTodos() {
        List<Ingrediente> ingredientes = new ArrayList<>();
        String sql = "SELECT * FROM \"INGREDIENTES\"";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Ingrediente ingrediente = new Ingrediente(
                    rs.getInt("Cod-ingred"),
                    rs.getString("Nome-ingred"),
                    "" // Descrição não existe na tabela
                );
                
                ingredientes.add(ingrediente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ingredientes: " + e.getMessage());
        }
        
        return ingredientes;
    }
    
    public static boolean atualizar(Ingrediente ingrediente) {
        String sql = "UPDATE \"INGREDIENTES\" SET \"Nome-ingred\" = ? WHERE \"Cod-ingred\" = ?";
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, ingrediente.getNome());
            stmt.setInt(2, ingrediente.getCodIngrediente());
            
            int linhasAfetadas = stmt.executeUpdate();
            conn.commit();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fazer rollback: " + ex.getMessage());
            }
            System.out.println("Erro ao atualizar ingrediente: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codIngrediente) {
        String sql = "DELETE FROM \"INGREDIENTES\" WHERE \"Cod-ingred\" = ?";
        Connection conn = null;
        
        try {
            conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, codIngrediente);
            
            int linhasAfetadas = stmt.executeUpdate();
            conn.commit();
            
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fazer rollback: " + ex.getMessage());
            }
            System.out.println("Erro ao excluir ingrediente: " + e.getMessage());
            return false;
        }
    }
} 