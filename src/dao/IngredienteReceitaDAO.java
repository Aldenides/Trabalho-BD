package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.IngredienteReceita;
import DataBase.Conexao;

public class IngredienteReceitaDAO {
    
    public static boolean inserir(IngredienteReceita ingredienteReceita) {
        String sql = "INSERT INTO IngredienteReceita (codReceita, nomeFantasiaCo, codIngredientes, " +
                     "quantidadeIngredientett, meida) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ingredienteReceita.getCodReceita());
            stmt.setString(2, ingredienteReceita.getNomeFantasiaCo());
            stmt.setInt(3, ingredienteReceita.getCodIngredientes());
            stmt.setDouble(4, ingredienteReceita.getQuantidadeIngredientett());
            stmt.setString(5, ingredienteReceita.getMeida());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir ingrediente da receita: " + e.getMessage());
            return false;
        }
    }
    
    public static IngredienteReceita buscar(int codReceita, String nomeFantasiaCo, int codIngredientes) {
        String sql = "SELECT * FROM IngredienteReceita WHERE codReceita = ? AND nomeFantasiaCo = ? AND codIngredientes = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codReceita);
            stmt.setString(2, nomeFantasiaCo);
            stmt.setInt(3, codIngredientes);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new IngredienteReceita(
                        rs.getInt("codReceita"),
                        rs.getString("nomeFantasiaCo"),
                        rs.getInt("codIngredientes"),
                        rs.getDouble("quantidadeIngredientett"),
                        rs.getString("meida")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ingrediente da receita: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<IngredienteReceita> listarPorReceita(int codReceita) {
        List<IngredienteReceita> ingredientesReceita = new ArrayList<>();
        String sql = "SELECT * FROM IngredienteReceita WHERE codReceita = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codReceita);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    IngredienteReceita ingredienteReceita = new IngredienteReceita(
                        rs.getInt("codReceita"),
                        rs.getString("nomeFantasiaCo"),
                        rs.getInt("codIngredientes"),
                        rs.getDouble("quantidadeIngredientett"),
                        rs.getString("meida")
                    );
                    
                    ingredientesReceita.add(ingredienteReceita);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ingredientes da receita: " + e.getMessage());
        }
        
        return ingredientesReceita;
    }
    
    public static boolean atualizar(IngredienteReceita ingredienteReceita) {
        String sql = "UPDATE IngredienteReceita SET quantidadeIngredientett = ?, meida = ? " +
                     "WHERE codReceita = ? AND nomeFantasiaCo = ? AND codIngredientes = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, ingredienteReceita.getQuantidadeIngredientett());
            stmt.setString(2, ingredienteReceita.getMeida());
            stmt.setInt(3, ingredienteReceita.getCodReceita());
            stmt.setString(4, ingredienteReceita.getNomeFantasiaCo());
            stmt.setInt(5, ingredienteReceita.getCodIngredientes());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar ingrediente da receita: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codReceita, String nomeFantasiaCo, int codIngredientes) {
        String sql = "DELETE FROM IngredienteReceita WHERE codReceita = ? AND nomeFantasiaCo = ? AND codIngredientes = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codReceita);
            stmt.setString(2, nomeFantasiaCo);
            stmt.setInt(3, codIngredientes);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir ingrediente da receita: " + e.getMessage());
            return false;
        }
    }
} 