package dao;

import DataBase.Conexao;
import java.sql.*;
import model.Cozinheiro;

public class CozinheiroDAO {
    public static boolean inserir(Cozinheiro cozinheiro) {
        String sql = "INSERT INTO \"COZINHEIROS\" (\"cpf-coz\", \"Nome-coz\", \"Nome-fantasia\", \"Dt-contrato-coz\", \"salario-coz\") VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, cozinheiro.getCpf());
            stmt.setString(2, cozinheiro.getNome());
            stmt.setString(3, cozinheiro.getNomeFantasia());
            stmt.setDate(4, cozinheiro.getDtContrato());
            
            if (cozinheiro.getSalario() != null) {
                stmt.setDouble(5, cozinheiro.getSalario());
            } else {
                stmt.setNull(5, Types.DECIMAL);
            }
            
            stmt.execute();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cozinheiro: " + e.getMessage());
            return false;
        }
    }
    
    // Outros métodos (atualizar, excluir, buscar)...
}