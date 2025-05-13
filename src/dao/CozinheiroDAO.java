package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cozinheiro;
import DataBase.Conexao;

public class CozinheiroDAO {
    
    public static boolean inserir(Cozinheiro cozinheiro) {
        String sql = "INSERT INTO Cozinheiro (nome, nomeFantasia, dtContrato) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cozinheiro.getNome());
            stmt.setString(2, cozinheiro.getNomeFantasia());
            stmt.setDate(3, cozinheiro.getDtContrato());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cozinheiro: " + e.getMessage());
            return false;
        }
    }
    
    public static Cozinheiro buscarPorNomeFantasia(String nomeFantasia) {
        String sql = "SELECT * FROM Cozinheiro WHERE nomeFantasia = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeFantasia);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cozinheiro(
                        rs.getString("nome"),
                        rs.getString("nomeFantasia"),
                        rs.getDate("dtContrato")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cozinheiro: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Cozinheiro> listarTodos() {
        List<Cozinheiro> cozinheiros = new ArrayList<>();
        String sql = "SELECT * FROM Cozinheiro";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cozinheiro cozinheiro = new Cozinheiro(
                    rs.getString("nome"),
                    rs.getString("nomeFantasia"),
                    rs.getDate("dtContrato")
                );
                
                cozinheiros.add(cozinheiro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar cozinheiros: " + e.getMessage());
        }
        
        return cozinheiros;
    }
    
    public static boolean atualizar(Cozinheiro cozinheiro) {
        String sql = "UPDATE Cozinheiro SET nome = ?, dtContrato = ? WHERE nomeFantasia = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cozinheiro.getNome());
            stmt.setDate(2, cozinheiro.getDtContrato());
            stmt.setString(3, cozinheiro.getNomeFantasia());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cozinheiro: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluirPorNomeFantasia(String nomeFantasia) {
        String sql = "DELETE FROM Cozinheiro WHERE nomeFantasia = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeFantasia);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cozinheiro: " + e.getMessage());
            return false;
        }
    }
}