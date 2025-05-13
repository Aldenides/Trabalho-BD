package dao;

import DataBase.Conexao;
import model.Ingrediente;
import model.IngredienteReceita;
import model.Receita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredienteReceitaDAO {

    public static boolean inserir(IngredienteReceita ingredienteReceita) {
        String sql = "INSERT INTO \"INGREDIENTES-RECEITAS\" (\"Cod-rec-ingrec\", \"cod-ing-ingrec\", \"Quant-ingrec\", \"Med-ingrec\") VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ingredienteReceita.getReceita().getCodReceita());
            stmt.setInt(2, ingredienteReceita.getIngrediente().getCodIngrediente());
            stmt.setDouble(3, ingredienteReceita.getQuantidade());
            stmt.setString(4, ingredienteReceita.getUnidadeMedida());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir ingrediente na receita: " + e.getMessage());
            return false;
        }
    }
    
    public static IngredienteReceita buscar(int codReceita, int codIngrediente) {
        String sql = "SELECT ir.*, r.\"Nome-rec\" as receita_nome, r.\"Dt-criacao-rec\", " +
                     "i.\"Nome-ingred\" " +
                     "FROM \"INGREDIENTES-RECEITAS\" ir " +
                     "JOIN \"RECEITAS\" r ON ir.\"Cod-rec-ingrec\" = r.\"Cod-rec\" " +
                     "JOIN \"INGREDIENTES\" i ON ir.\"cod-ing-ingrec\" = i.\"Cod-ingred\" " +
                     "WHERE ir.\"Cod-rec-ingrec\" = ? AND ir.\"cod-ing-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            stmt.setInt(2, codIngrediente);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Criar Ingrediente
                    Ingrediente ingrediente = new Ingrediente(
                            rs.getInt("cod-ing-ingrec"),
                            rs.getString("Nome-ingred"),
                            ""  // Descrição não existe na tabela
                    );
                    
                    // Criar Receita simplificada (sem objetos relacionados para evitar recursão)
                    Receita receita = new Receita(
                            rs.getInt("Cod-rec-ingrec"),
                            rs.getString("receita_nome"),
                            rs.getDate("Dt-criacao-rec"),
                            0,  // Tempo de preparo, indisponível nesta consulta
                            0,  // Rendimento, indisponível nesta consulta
                            "", // Modo de preparo, indisponível nesta consulta
                            null, // cozinheiro
                            null  // categoria
                    );
                    
                    // Criar IngredienteReceita
                    return new IngredienteReceita(
                            receita,
                            ingrediente,
                            rs.getDouble("Quant-ingrec"),
                            rs.getString("Med-ingrec")
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
        String sql = "SELECT ir.*, i.\"Nome-ingred\" " +
                     "FROM \"INGREDIENTES-RECEITAS\" ir " +
                     "JOIN \"INGREDIENTES\" i ON ir.\"cod-ing-ingrec\" = i.\"Cod-ingred\" " +
                     "WHERE ir.\"Cod-rec-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Buscar a receita uma única vez
                Receita receita = ReceitaDAO.buscar(codReceita);
                
                if (receita != null) {
                    while (rs.next()) {
                        // Criar Ingrediente
                        Ingrediente ingrediente = new Ingrediente(
                                rs.getInt("cod-ing-ingrec"),
                                rs.getString("Nome-ingred"),
                                ""  // Descrição não existe na tabela
                        );
                        
                        // Criar IngredienteReceita
                        IngredienteReceita ingredienteReceita = new IngredienteReceita(
                                receita,
                                ingrediente,
                                rs.getDouble("Quant-ingrec"),
                                rs.getString("Med-ingrec")
                        );
                        
                        ingredientesReceita.add(ingredienteReceita);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ingredientes da receita: " + e.getMessage());
        }
        
        return ingredientesReceita;
    }
    
    public static boolean atualizar(IngredienteReceita ingredienteReceita) {
        String sql = "UPDATE \"INGREDIENTES-RECEITAS\" SET \"Quant-ingrec\" = ?, \"Med-ingrec\" = ? " +
                     "WHERE \"Cod-rec-ingrec\" = ? AND \"cod-ing-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, ingredienteReceita.getQuantidade());
            stmt.setString(2, ingredienteReceita.getUnidadeMedida());
            stmt.setInt(3, ingredienteReceita.getReceita().getCodReceita());
            stmt.setInt(4, ingredienteReceita.getIngrediente().getCodIngrediente());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar ingrediente da receita: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codReceita, int codIngrediente) {
        String sql = "DELETE FROM \"INGREDIENTES-RECEITAS\" WHERE \"Cod-rec-ingrec\" = ? AND \"cod-ing-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            stmt.setInt(2, codIngrediente);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir ingrediente da receita: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluirPorReceita(int codReceita) {
        String sql = "DELETE FROM \"INGREDIENTES-RECEITAS\" WHERE \"Cod-rec-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir ingredientes da receita: " + e.getMessage());
            return false;
        }
    }
} 