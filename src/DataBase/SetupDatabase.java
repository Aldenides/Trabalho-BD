package DataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe utilitária para configurar o banco de dados
 */
public class SetupDatabase {
    
    /**
     * Executa o script SQL para criar as tabelas e dados iniciais
     * @return true se o script foi executado com sucesso
     */
    public static boolean executarScriptSQL() {
        String caminho = "sql/setup_database.sql";
        StringBuilder script = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                script.append(linha).append("\n");
            }
        } catch (IOException e) {
            System.err.println("[ERRO] Erro ao ler o arquivo de script SQL: " + e.getMessage());
            return false;
        }
        
        String[] comandos = script.toString().split(";");
        
        try (Connection conn = Conexao.getConexao()) {
            try (Statement stmt = conn.createStatement()) {
                for (String comando : comandos) {
                    if (!comando.trim().isEmpty()) {
                        stmt.execute(comando + ";");
                    }
                }
                conn.commit();
                System.out.println("[INFO] Script SQL executado com sucesso!");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("[ERRO] Erro ao executar script SQL: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Erro ao obter conexão: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Método principal para testar a conexão e executar o script
     */
    public static void main(String[] args) {
        try {
            // Testa a conexão
            if (Conexao.testarConexao()) {
                System.out.println("[INFO] Teste de conexão bem-sucedido!");
                
                // Executa o script SQL
                if (executarScriptSQL()) {
                    System.out.println("[INFO] Banco de dados configurado com sucesso!");
                } else {
                    System.err.println("[ERRO] Falha ao configurar o banco de dados.");
                }
            } else {
                System.err.println("[ERRO] Falha no teste de conexão com o PostgreSQL.");
            }
        } catch (Exception e) {
            System.err.println("[ERRO] Exceção ao configurar banco de dados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fecha a conexão
            Conexao.fecharConexao();
        }
    }
} 