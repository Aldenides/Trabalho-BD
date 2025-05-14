package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Restaurante;
import DataBase.Conexao;

public class RestauranteDAO {
    
    public static boolean inserir(Restaurante restaurante) {
        String sql = "INSERT INTO \"RESTAURANTES\" (\"Cod-rest\", \"Nome-rest\") " +
                     "VALUES (?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, restaurante.getCodRestaurante());
            stmt.setString(2, restaurante.getNome());
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir restaurante: " + e.getMessage());
            return false;
        }
    }
    
    public static Restaurante buscar(int codRestaurante) {
        String sql = "SELECT * FROM \"RESTAURANTES\" WHERE \"Cod-rest\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codRestaurante);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Restaurante(
                        rs.getInt("Cod-rest"),
                        rs.getString("Nome-rest"),
                        "", // Endereço não existe na tabela
                        "", // Telefone não existe na tabela
                        0,  // Capacidade não existe na tabela
                        ""  // Especialidade não existe na tabela
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar restaurante: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Restaurante> listarTodos() {
        List<Restaurante> restaurantes = new ArrayList<>();
        String sql = "SELECT * FROM \"RESTAURANTES\" ORDER BY \"Nome-rest\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Restaurante restaurante = new Restaurante(
                    rs.getInt("Cod-rest"),
                    rs.getString("Nome-rest"),
                    "", // Endereço não existe na tabela
                    "", // Telefone não existe na tabela
                    0,  // Capacidade não existe na tabela
                    ""  // Especialidade não existe na tabela
                );
                
                restaurantes.add(restaurante);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar restaurantes: " + e.getMessage());
        }
        
        return restaurantes;
    }
    
    public static boolean atualizar(Restaurante restaurante) {
        String sql = "UPDATE \"RESTAURANTES\" SET \"Nome-rest\" = ? WHERE \"Cod-rest\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, restaurante.getNome());
            stmt.setInt(2, restaurante.getCodRestaurante());
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar restaurante: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int codRestaurante) {
        String sql = "DELETE FROM \"RESTAURANTES\" WHERE \"Cod-rest\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codRestaurante);
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir restaurante: " + e.getMessage());
            return false;
        }
    }
    
    public static int gerarProximoCodigo() {
        String sql = "SELECT MAX(\"Cod-rest\") FROM \"RESTAURANTES\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                int maxCodigo = rs.getInt(1);
                return maxCodigo + 1;
            }
            return 1; // Código inicial padrão
        } catch (SQLException e) {
            System.out.println("Erro ao gerar próximo código: " + e.getMessage());
            return 1; // Código inicial em caso de erro
        }
    }
} 