package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Ingrediente;
import DataBase.Conexao;

public class IngredienteDAO {
    
    public static boolean inserir(Ingrediente ingrediente) {
        String sql = "INSERT INTO Ingrediente (codIngrediente, nomeIngrediente) VALUES (?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ingrediente.getCodIngrediente());
            stmt.setString(2, ingrediente.getNomeIngrediente());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir ingrediente: " + e.getMessage());
            return false;
        }
    }
    
    public static Ingrediente buscar(int codIngrediente) {
        String sql = "SELECT * FROM Ingrediente WHERE codIngrediente = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codIngrediente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ingrediente(
                        rs.getInt("codIngrediente"),
                        rs.getString("nomeIngrediente")
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
        String sql = "SELECT * FROM Ingrediente";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ingrediente ingrediente = new Ingrediente(
                    rs.getInt("codIngrediente"),
                    rs.getString("nomeIngrediente")
                );
                
                ingredientes.add(ingrediente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ingredientes: " + e.getMessage());
        }
        
        return ingredientes;
    }
    
    public static boolean atualizar(Ingrediente ingrediente) {
        String sql = "UPDATE Ingrediente SET nomeIngrediente = ? WHERE codIngrediente = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ingrediente.getNomeIngrediente());
            stmt.setInt(2, ingrediente.getCodIngrediente());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar ingrediente: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codIngrediente) {
        String sql = "DELETE FROM Ingrediente WHERE codIngrediente = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codIngrediente);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir ingrediente: " + e.getMessage());
            return false;
        }
    }
} 