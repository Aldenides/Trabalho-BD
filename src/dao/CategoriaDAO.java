package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import DataBase.Conexao;

public class CategoriaDAO {
    
    public static boolean inserir(Categoria categoria) {
        String sql = "INSERT INTO \"CATEGORIA-RECEITA\" (\"Cod-cat-rec\", \"Desc-cat-rec\") VALUES (?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, Integer.parseInt(categoria.getNome()));
            stmt.setString(2, categoria.getDescricao());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
            return false;
        }
    }
    
    public static Categoria buscarPorNome(String nome) {
        String sql = "SELECT * FROM \"CATEGORIA-RECEITA\" WHERE \"Cod-cat-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, Integer.parseInt(nome));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                        String.valueOf(rs.getInt("Cod-cat-rec")),
                        rs.getString("Desc-cat-rec")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Categoria> listarTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM \"CATEGORIA-RECEITA\"";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    String.valueOf(rs.getInt("Cod-cat-rec")),
                    rs.getString("Desc-cat-rec")
                );
                
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        }
        
        return categorias;
    }
    
    public static boolean atualizar(Categoria categoria, String nomeAntigo) {
        String sql = "UPDATE \"CATEGORIA-RECEITA\" SET \"Desc-cat-rec\" = ? WHERE \"Cod-cat-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, categoria.getDescricao());
            stmt.setInt(2, Integer.parseInt(nomeAntigo));
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(String nome) {
        String sql = "DELETE FROM \"CATEGORIA-RECEITA\" WHERE \"Cod-cat-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, Integer.parseInt(nome));
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir categoria: " + e.getMessage());
            return false;
        }
    }
} 