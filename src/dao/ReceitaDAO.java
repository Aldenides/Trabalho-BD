package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Receita;
import DataBase.Conexao;
import model.Categoria;
import model.Cozinheiro;

public class ReceitaDAO {
    
    public static boolean inserir(Receita receita) {
        String sql = "INSERT INTO \"RECEITAS\" (\"Cod-rec\", \"Nome-rec\", \"Dt-criacao-rec\", \"C0d-categoria-rec\", \"Cpf-coz\") " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receita.getCodReceita());
            stmt.setString(2, receita.getNome());
            stmt.setDate(3, receita.getDataCriacao());
            stmt.setInt(4, Integer.parseInt(receita.getCategoria().getNome()));  // Código da categoria
            stmt.setLong(5, receita.getCozinheiro().getCpf());  // CPF do cozinheiro
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir receita: " + e.getMessage());
            return false;
        }
    }
    
    public static Receita buscar(int codReceita) {
        String sql = "SELECT r.*, c.\"Nome-coz\", c.\"Nome-fantasia\", c.\"Dt-contrato-coz\", c.\"salario-coz\", " +
                     "cat.\"Desc-cat-rec\" " +
                     "FROM \"RECEITAS\" r " +
                     "JOIN \"COZINHEIROS\" c ON r.\"Cpf-coz\" = c.\"cpf-coz\" " +
                     "JOIN \"CATEGORIA-RECEITA\" cat ON r.\"C0d-categoria-rec\" = cat.\"Cod-cat-rec\" " +
                     "WHERE r.\"Cod-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Criar o objeto Cozinheiro
                    Cozinheiro cozinheiro = new Cozinheiro(
                            rs.getLong("cpf-coz"),
                            rs.getString("Nome-coz"),
                            rs.getString("Nome-fantasia"),
                            rs.getDate("Dt-contrato-coz"),
                            rs.getObject("salario-coz") != null ? rs.getDouble("salario-coz") : null
                    );
                    
                    // Criar o objeto Categoria
                    Categoria categoria = new Categoria(
                            String.valueOf(rs.getInt("C0d-categoria-rec")),
                            rs.getString("Desc-cat-rec")
                    );
                    
                    // Criar e retornar o objeto Receita
                    return new Receita(
                            rs.getInt("Cod-rec"),
                            rs.getString("Nome-rec"),
                            rs.getDate("Dt-criacao-rec"),
                            0,  // tempoPreparo não existe na tabela
                            0,  // rendimento não existe na tabela
                            "",  // modoPreparo não existe na tabela
                            cozinheiro,
                            categoria
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar receita: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Receita> listarTodas() {
        List<Receita> receitas = new ArrayList<>();
        String sql = "SELECT r.*, c.\"Nome-coz\", c.\"Nome-fantasia\", c.\"Dt-contrato-coz\", c.\"salario-coz\", " +
                     "cat.\"Desc-cat-rec\" " +
                     "FROM \"RECEITAS\" r " +
                     "JOIN \"COZINHEIROS\" c ON r.\"Cpf-coz\" = c.\"cpf-coz\" " +
                     "JOIN \"CATEGORIA-RECEITA\" cat ON r.\"C0d-categoria-rec\" = cat.\"Cod-cat-rec\"";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Criar o objeto Cozinheiro
                Cozinheiro cozinheiro = new Cozinheiro(
                        rs.getLong("cpf-coz"),
                        rs.getString("Nome-coz"),
                        rs.getString("Nome-fantasia"),
                        rs.getDate("Dt-contrato-coz"),
                        rs.getObject("salario-coz") != null ? rs.getDouble("salario-coz") : null
                );
                
                // Criar o objeto Categoria
                Categoria categoria = new Categoria(
                        String.valueOf(rs.getInt("C0d-categoria-rec")),
                        rs.getString("Desc-cat-rec")
                );
                
                // Criar o objeto Receita e adicionar à lista
                Receita receita = new Receita(
                        rs.getInt("Cod-rec"),
                        rs.getString("Nome-rec"),
                        rs.getDate("Dt-criacao-rec"),
                        0,  // tempoPreparo não existe na tabela
                        0,  // rendimento não existe na tabela
                        "",  // modoPreparo não existe na tabela
                        cozinheiro,
                        categoria
                );
                
                receitas.add(receita);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar receitas: " + e.getMessage());
        }
        
        return receitas;
    }
    
    public static boolean atualizar(Receita receita) {
        String sql = "UPDATE \"RECEITAS\" SET \"Nome-rec\" = ?, \"Dt-criacao-rec\" = ?, " +
                     "\"C0d-categoria-rec\" = ?, \"Cpf-coz\" = ? " +
                     "WHERE \"Cod-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, receita.getNome());
            stmt.setDate(2, receita.getDataCriacao());
            stmt.setInt(3, Integer.parseInt(receita.getCategoria().getNome()));
            stmt.setLong(4, receita.getCozinheiro().getCpf());
            stmt.setInt(5, receita.getCodReceita());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar receita: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codReceita) {
        String sql = "DELETE FROM \"RECEITAS\" WHERE \"Cod-rec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir receita: " + e.getMessage());
            return false;
        }
    }
} 