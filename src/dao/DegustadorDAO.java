package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Degustador;
import DataBase.Conexao;

public class DegustadorDAO {
    
    public static boolean inserir(Degustador degustador) {
        String sql = "INSERT INTO \"DEGUSTADORES\" (\"Cpf-deg\", \"Nome-deg\", \"Dt-contrato-deg\", \"Salario-deg\") " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, degustador.getCpf());
            stmt.setString(2, degustador.getNome());
            stmt.setDate(3, degustador.getDtContrato());
            
            if (degustador.getSalario() != null) {
                stmt.setDouble(4, degustador.getSalario());
            } else {
                stmt.setNull(4, Types.DECIMAL);
            }
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir degustador: " + e.getMessage());
            return false;
        }
    }
    
    public static Degustador buscar(long cpf) {
        String sql = "SELECT * FROM \"DEGUSTADORES\" WHERE \"Cpf-deg\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Degustador(
                        rs.getLong("Cpf-deg"),
                        rs.getString("Nome-deg"),
                        rs.getDate("Dt-contrato-deg"),
                        rs.getObject("Salario-deg") != null ? rs.getDouble("Salario-deg") : null
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar degustador: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Degustador> listarTodos() {
        List<Degustador> degustadores = new ArrayList<>();
        String sql = "SELECT * FROM \"DEGUSTADORES\" ORDER BY \"Nome-deg\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Degustador degustador = new Degustador(
                    rs.getLong("Cpf-deg"),
                    rs.getString("Nome-deg"),
                    rs.getDate("Dt-contrato-deg"),
                    rs.getObject("Salario-deg") != null ? rs.getDouble("Salario-deg") : null
                );
                
                degustadores.add(degustador);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar degustadores: " + e.getMessage());
        }
        
        return degustadores;
    }
    
    public static boolean atualizar(Degustador degustador) {
        String sql = "UPDATE \"DEGUSTADORES\" SET \"Nome-deg\" = ?, \"Dt-contrato-deg\" = ?, " +
                     "\"Salario-deg\" = ? WHERE \"Cpf-deg\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, degustador.getNome());
            stmt.setDate(2, degustador.getDtContrato());
            
            if (degustador.getSalario() != null) {
                stmt.setDouble(3, degustador.getSalario());
            } else {
                stmt.setNull(3, Types.DECIMAL);
            }
            
            stmt.setLong(4, degustador.getCpf());
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar degustador: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(long cpf) {
        String sql = "DELETE FROM \"DEGUSTADORES\" WHERE \"Cpf-deg\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, cpf);
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir degustador: " + e.getMessage());
            return false;
        }
    }
    
    public static long gerarProximoCPF() {
        String sql = "SELECT MAX(\"Cpf-deg\") FROM \"DEGUSTADORES\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                long maxCPF = rs.getLong(1);
                return maxCPF > 0 ? maxCPF + 1 : 10000000001L; // CPF inicial se não houver registros
            }
            return 10000000001L; // CPF inicial padrão
        } catch (SQLException e) {
            System.out.println("Erro ao gerar próximo CPF: " + e.getMessage());
            return 10000000001L; // CPF inicial em caso de erro
        }
    }
} 