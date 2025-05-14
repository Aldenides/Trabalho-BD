package dao;

import DataBase.Conexao;
import model.Ingrediente;
import model.IngredienteReceita;
import model.Receita;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            
            // Verificar se as referências não são nulas
            if (ingredienteReceita.getReceita() == null) {
                System.out.println("Erro: A receita não pode ser nula!");
                return false;
            }
            
            if (ingredienteReceita.getIngrediente() == null) {
                System.out.println("Erro: O ingrediente não pode ser nulo!");
                return false;
            }
            
            // Validar que a quantidade não seja negativa
            if (ingredienteReceita.getQuantidade() < 0) {
                System.out.println("Erro: A quantidade não pode ser negativa!");
                return false;
            }
            
            // Validar que a quantidade esteja dentro do limite numeric(4,2)
            if (ingredienteReceita.getQuantidade() >= 100) {
                System.out.println("Erro: A quantidade deve ser menor que 100!");
                return false;
            }
            
            stmt.setInt(1, ingredienteReceita.getReceita().getCodReceita());
            stmt.setInt(2, ingredienteReceita.getIngrediente().getCodIngrediente());
            
            try {
                // Converter para BigDecimal com escala 2 (duas casas decimais)
                BigDecimal quantidade = BigDecimal.valueOf(ingredienteReceita.getQuantidade())
                                           .setScale(2, RoundingMode.HALF_UP);
                stmt.setBigDecimal(3, quantidade);
            } catch (Exception e) {
                System.out.println("Erro ao converter quantidade para o formato do banco: " + e.getMessage());
                return false;
            }
            
            stmt.setString(4, ingredienteReceita.getUnidadeMedida());
            
            System.out.println("Executando SQL: " + sql);
            System.out.println("Parâmetros: CodReceita=" + ingredienteReceita.getReceita().getCodReceita() + 
                             ", CodIngrediente=" + ingredienteReceita.getIngrediente().getCodIngrediente() + 
                             ", Quantidade=" + ingredienteReceita.getQuantidade() + 
                             ", Unidade=" + ingredienteReceita.getUnidadeMedida());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Ingrediente na receita inserido com sucesso!");
                return true;
            } else {
                System.out.println("Nenhuma linha afetada ao inserir ingrediente na receita.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir ingrediente na receita: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Erro não esperado ao inserir ingrediente na receita: " + e.getMessage());
            e.printStackTrace();
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
            
            System.out.println("Executando SQL: " + sql);
            System.out.println("Parâmetros: CodReceita=" + codReceita + ", CodIngrediente=" + codIngrediente);
            
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
                } else {
                    System.out.println("Nenhum ingrediente encontrado para a receita " + codReceita + " e ingrediente " + codIngrediente);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ingrediente da receita: " + e.getMessage());
            e.printStackTrace();
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
            
            System.out.println("Executando SQL: " + sql);
            System.out.println("Parâmetro: CodReceita=" + codReceita);
            
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
                    
                    System.out.println("Encontrados " + ingredientesReceita.size() + " ingredientes para a receita " + codReceita);
                } else {
                    System.out.println("Receita " + codReceita + " não encontrada.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ingredientes da receita: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ingredientesReceita;
    }
    
    public static boolean atualizar(IngredienteReceita ingredienteReceita) {
        String sql = "UPDATE \"INGREDIENTES-RECEITAS\" SET \"Quant-ingrec\" = ?, \"Med-ingrec\" = ? " +
                     "WHERE \"Cod-rec-ingrec\" = ? AND \"cod-ing-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Verificar se as referências não são nulas
            if (ingredienteReceita.getReceita() == null) {
                System.out.println("Erro: A receita não pode ser nula!");
                return false;
            }
            
            if (ingredienteReceita.getIngrediente() == null) {
                System.out.println("Erro: O ingrediente não pode ser nulo!");
                return false;
            }
            
            // Validar que a quantidade não seja negativa
            if (ingredienteReceita.getQuantidade() < 0) {
                System.out.println("Erro: A quantidade não pode ser negativa!");
                return false;
            }
            
            // Validar que a quantidade esteja dentro do limite numeric(4,2)
            if (ingredienteReceita.getQuantidade() >= 100) {
                System.out.println("Erro: A quantidade deve ser menor que 100!");
                return false;
            }
            
            try {
                // Converter para BigDecimal com escala 2 (duas casas decimais)
                BigDecimal quantidade = BigDecimal.valueOf(ingredienteReceita.getQuantidade())
                                           .setScale(2, RoundingMode.HALF_UP);
                stmt.setBigDecimal(1, quantidade);
            } catch (Exception e) {
                System.out.println("Erro ao converter quantidade para o formato do banco: " + e.getMessage());
                return false;
            }
            
            stmt.setString(2, ingredienteReceita.getUnidadeMedida());
            stmt.setInt(3, ingredienteReceita.getReceita().getCodReceita());
            stmt.setInt(4, ingredienteReceita.getIngrediente().getCodIngrediente());
            
            System.out.println("Executando SQL: " + sql);
            System.out.println("Parâmetros: Quantidade=" + ingredienteReceita.getQuantidade() + 
                             ", Unidade=" + ingredienteReceita.getUnidadeMedida() +
                             ", CodReceita=" + ingredienteReceita.getReceita().getCodReceita() + 
                             ", CodIngrediente=" + ingredienteReceita.getIngrediente().getCodIngrediente());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Ingrediente na receita atualizado com sucesso!");
                return true;
            } else {
                System.out.println("Nenhuma linha afetada ao atualizar ingrediente na receita. Verifique se a combinação de Receita e Ingrediente existe.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar ingrediente na receita: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Erro não esperado ao atualizar ingrediente na receita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean excluir(int codReceita, int codIngrediente) {
        String sql = "DELETE FROM \"INGREDIENTES-RECEITAS\" WHERE \"Cod-rec-ingrec\" = ? AND \"cod-ing-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            stmt.setInt(2, codIngrediente);
            
            System.out.println("Executando SQL: " + sql);
            System.out.println("Parâmetros: CodReceita=" + codReceita + ", CodIngrediente=" + codIngrediente);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Ingrediente na receita excluído com sucesso!");
                return true;
            } else {
                System.out.println("Nenhuma linha afetada ao excluir ingrediente na receita.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir ingrediente da receita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean excluirPorReceita(int codReceita) {
        String sql = "DELETE FROM \"INGREDIENTES-RECEITAS\" WHERE \"Cod-rec-ingrec\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codReceita);
            
            System.out.println("Executando SQL: " + sql);
            System.out.println("Parâmetro: CodReceita=" + codReceita);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("Excluídos " + linhasAfetadas + " ingredientes da receita " + codReceita);
                return true;
            } else {
                System.out.println("Nenhum ingrediente encontrado para excluir na receita " + codReceita);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir ingredientes da receita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
} 