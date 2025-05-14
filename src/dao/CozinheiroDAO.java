package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cozinheiro;
import DataBase.Conexao;

public class CozinheiroDAO {
    
    public static boolean inserir(Cozinheiro cozinheiro) {
        String sql = "INSERT INTO \"COZINHEIROS\" (\"cpf-coz\", \"Nome-coz\", \"Nome-fantasia\", \"Dt-contrato-coz\", \"salario-coz\") " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cozinheiro.getCpf());
            stmt.setString(2, cozinheiro.getNome());
            stmt.setString(3, cozinheiro.getNomeFantasia());
            stmt.setDate(4, cozinheiro.getDtContrato());
            
            if (cozinheiro.getSalario() != null) {
                stmt.setDouble(5, cozinheiro.getSalario());
            } else {
                stmt.setNull(5, Types.DECIMAL);
            }
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cozinheiro: " + e.getMessage());
            return false;
        }
    }
    
    public static Cozinheiro buscarPorNomeFantasia(String nomeFantasia) {
        String sql = "SELECT * FROM \"COZINHEIROS\" WHERE \"Nome-fantasia\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeFantasia);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cozinheiro(
                        rs.getInt("cpf-coz"),
                        rs.getString("Nome-coz"),
                        rs.getString("Nome-fantasia"),
                        rs.getDate("Dt-contrato-coz"),
                        rs.getObject("salario-coz") != null ? rs.getDouble("salario-coz") : null
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cozinheiro: " + e.getMessage());
        }
        
        return null;
    }
    
    public static Cozinheiro buscar(int cpf) {
        String sql = "SELECT * FROM \"COZINHEIROS\" WHERE \"cpf-coz\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cozinheiro(
                        rs.getInt("cpf-coz"),
                        rs.getString("Nome-coz"),
                        rs.getString("Nome-fantasia"),
                        rs.getDate("Dt-contrato-coz"),
                        rs.getObject("salario-coz") != null ? rs.getDouble("salario-coz") : null
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
        String sql = "SELECT * FROM \"COZINHEIROS\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cozinheiro cozinheiro = new Cozinheiro(
                    rs.getInt("cpf-coz"),
                    rs.getString("Nome-coz"),
                    rs.getString("Nome-fantasia"),
                    rs.getDate("Dt-contrato-coz"),
                    rs.getObject("salario-coz") != null ? rs.getDouble("salario-coz") : null
                );
                
                cozinheiros.add(cozinheiro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar cozinheiros: " + e.getMessage());
        }
        
        return cozinheiros;
    }
    
    public static boolean atualizar(Cozinheiro cozinheiro) {
        String sql = "UPDATE \"COZINHEIROS\" SET \"Nome-coz\" = ?, \"Nome-fantasia\" = ?, " +
                     "\"Dt-contrato-coz\" = ?, \"salario-coz\" = ? WHERE \"cpf-coz\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cozinheiro.getNome());
            stmt.setString(2, cozinheiro.getNomeFantasia());
            stmt.setDate(3, cozinheiro.getDtContrato());
            
            if (cozinheiro.getSalario() != null) {
                stmt.setDouble(4, cozinheiro.getSalario());
            } else {
                stmt.setNull(4, Types.DECIMAL);
            }
            
            stmt.setInt(5, cozinheiro.getCpf());
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cozinheiro: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(int cpf) {
        String sql = "DELETE FROM \"COZINHEIROS\" WHERE \"cpf-coz\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cpf);
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cozinheiro: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluirPorNomeFantasia(String nomeFantasia) {
        String sql = "DELETE FROM \"COZINHEIROS\" WHERE \"Nome-fantasia\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeFantasia);
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cozinheiro: " + e.getMessage());
            return false;
        }
    }
}