package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Editor;
import DataBase.Conexao;

public class EditorDAO {
    
    public static boolean inserir(Editor editor) {
        String sql = "INSERT INTO \"EDITORES\" (\"Cpf-edit\", \"Nome-edit\", \"Dt-contrato-edit\", \"Salario-edit\") " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, editor.getCpf());
            stmt.setString(2, editor.getNome());
            stmt.setDate(3, editor.getDtContrato());
            
            if (editor.getSalario() != null) {
                stmt.setDouble(4, editor.getSalario());
            } else {
                stmt.setNull(4, Types.DECIMAL);
            }
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir editor: " + e.getMessage());
            return false;
        }
    }
    
    public static Editor buscar(long cpf) {
        String sql = "SELECT * FROM \"EDITORES\" WHERE \"Cpf-edit\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Editor(
                        rs.getLong("Cpf-edit"),
                        rs.getString("Nome-edit"),
                        rs.getDate("Dt-contrato-edit"),
                        rs.getObject("Salario-edit") != null ? rs.getDouble("Salario-edit") : null
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar editor: " + e.getMessage());
        }
        
        return null;
    }
    
    public static List<Editor> listarTodos() {
        List<Editor> editores = new ArrayList<>();
        String sql = "SELECT * FROM \"EDITORES\" ORDER BY \"Nome-edit\"";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Editor editor = new Editor(
                    rs.getLong("Cpf-edit"),
                    rs.getString("Nome-edit"),
                    rs.getDate("Dt-contrato-edit"),
                    rs.getObject("Salario-edit") != null ? rs.getDouble("Salario-edit") : null
                );
                
                editores.add(editor);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar editores: " + e.getMessage());
        }
        
        return editores;
    }
    
    public static boolean atualizar(Editor editor) {
        String sql = "UPDATE \"EDITORES\" SET \"Nome-edit\" = ?, \"Dt-contrato-edit\" = ?, " +
                     "\"Salario-edit\" = ? WHERE \"Cpf-edit\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, editor.getNome());
            stmt.setDate(2, editor.getDtContrato());
            
            if (editor.getSalario() != null) {
                stmt.setDouble(3, editor.getSalario());
            } else {
                stmt.setNull(3, Types.DECIMAL);
            }
            
            stmt.setLong(4, editor.getCpf());
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar editor: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean excluir(long cpf) {
        String sql = "DELETE FROM \"EDITORES\" WHERE \"Cpf-edit\" = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, cpf);
            
            int result = stmt.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir editor: " + e.getMessage());
            return false;
        }
    }
    
    public static long gerarProximoCPF() {
        String sql = "SELECT MAX(\"Cpf-edit\") FROM \"EDITORES\"";
        
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