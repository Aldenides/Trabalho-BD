package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Livro;
import DataBase.Conexao;

public class LivroDAO {
    
    public static boolean inserir(Livro livro) {
        String sql = "INSERT INTO \"LIVROS\" (\"ISBN\", \"Titulo-livro\") VALUES (?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            
            int linhasAfetadas = stmt.executeUpdate();
            conn.commit();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir livro: " + e.getMessage());
            return false;
        }
    }
    
    public static Livro buscar(int isbn) {
        String sql = "SELECT * FROM \"LIVROS\" WHERE \"ISBN\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, isbn);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livro(
                        rs.getInt("ISBN"),
                        rs.getString("Titulo-livro")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
        
        return null;
    }
    
    public static Livro buscarPorTitulo(String titulo) {
        String sql = "SELECT * FROM \"LIVROS\" WHERE \"Titulo-livro\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, titulo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Livro(
                        rs.getInt("ISBN"),
                        rs.getString("Titulo-livro")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro por título: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM \"LIVROS\" ORDER BY \"ISBN\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Livro livro = new Livro(
                    rs.getInt("ISBN"),
                    rs.getString("Titulo-livro")
                );
                
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
        
        return livros;
    }
    
    public static boolean atualizar(Livro livro) {
        String sql = "UPDATE \"LIVROS\" SET \"Titulo-livro\" = ? WHERE \"ISBN\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getIsbn());
            
            int linhasAfetadas = stmt.executeUpdate();
            conn.commit();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int isbn) {
        String sql = "DELETE FROM \"LIVROS\" WHERE \"ISBN\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, isbn);
            
            int linhasAfetadas = stmt.executeUpdate();
            conn.commit();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir livro: " + e.getMessage());
            return false;
        }
    }
    
    public static int gerarProximoISBN() {
        String sql = "SELECT MAX(\"ISBN\") FROM \"LIVROS\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int maxISBN = rs.getInt(1);
                return maxISBN + 1;
            }
            return 1; // Se não houver registros, começa com 1
        } catch (SQLException e) {
            System.out.println("Erro ao gerar próximo ISBN: " + e.getMessage());
            return 1; // Em caso de erro, retorna 1 como valor padrão
        }
    }
} 