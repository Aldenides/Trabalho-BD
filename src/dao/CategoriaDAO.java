package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import DataBase.Conexao;

public class CategoriaDAO {
    
    public static boolean inserir(Categoria categoria) {
        String sql = "INSERT INTO Categoria (codCategoria, descricaoCategoria) VALUES (?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoria.getCodCategoria());
            stmt.setString(2, categoria.getDescricaoCategoria());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
            return false;
        }
    }
    
    public static Categoria buscar(int codCategoria) {
        String sql = "SELECT * FROM Categoria WHERE codCategoria = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codCategoria);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                        rs.getInt("codCategoria"),
                        rs.getString("descricaoCategoria")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Categoria> listarTodos() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getInt("codCategoria"),
                    rs.getString("descricaoCategoria")
                );
                
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        }
        
        return categorias;
    }
    
    public static boolean atualizar(Categoria categoria) {
        String sql = "UPDATE Categoria SET descricaoCategoria = ? WHERE codCategoria = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria.getDescricaoCategoria());
            stmt.setInt(2, categoria.getCodCategoria());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codCategoria) {
        String sql = "DELETE FROM Categoria WHERE codCategoria = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codCategoria);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir categoria: " + e.getMessage());
            return false;
        }
    }
} 