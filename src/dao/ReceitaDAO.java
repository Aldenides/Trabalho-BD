package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Receita;
import DataBase.Conexao;

public class ReceitaDAO {
    
    public static boolean inserir(Receita receita) {
        String sql = "INSERT INTO Receita (codReceita, nomeReceita, dataCriacao, nomeChefe, codCategoria) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, receita.getCodReceita());
            stmt.setString(2, receita.getNomeReceita());
            stmt.setDate(3, receita.getDataCriacao());
            stmt.setString(4, receita.getNomeChefe());
            stmt.setInt(5, receita.getCodCategoria());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir receita: " + e.getMessage());
            return false;
        }
    }
    
    public static Receita buscar(int codReceita) {
        String sql = "SELECT * FROM Receita WHERE codReceita = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codReceita);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Receita(
                        rs.getInt("codReceita"),
                        rs.getString("nomeReceita"),
                        rs.getDate("dataCriacao"),
                        rs.getString("nomeChefe"),
                        rs.getInt("codCategoria")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar receita: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Receita> listarTodos() {
        List<Receita> receitas = new ArrayList<>();
        String sql = "SELECT * FROM Receita";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Receita receita = new Receita(
                    rs.getInt("codReceita"),
                    rs.getString("nomeReceita"),
                    rs.getDate("dataCriacao"),
                    rs.getString("nomeChefe"),
                    rs.getInt("codCategoria")
                );
                
                receitas.add(receita);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar receitas: " + e.getMessage());
        }
        
        return receitas;
    }
    
    public static boolean atualizar(Receita receita) {
        String sql = "UPDATE Receita SET nomeReceita = ?, dataCriacao = ?, nomeChefe = ?, codCategoria = ? " +
                     "WHERE codReceita = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, receita.getNomeReceita());
            stmt.setDate(2, receita.getDataCriacao());
            stmt.setString(3, receita.getNomeChefe());
            stmt.setInt(4, receita.getCodCategoria());
            stmt.setInt(5, receita.getCodReceita());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar receita: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codReceita) {
        String sql = "DELETE FROM Receita WHERE codReceita = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codReceita);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir receita: " + e.getMessage());
            return false;
        }
    }
} 