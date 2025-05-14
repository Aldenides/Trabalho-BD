package DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para debug de SQL e verificação de tabelas
 */
public class SQLDebugger {
    
    /**
     * Método principal - execute para ver informações do banco de dados
     */
    public static void main(String[] args) {
        System.out.println("=== SQL Debugger ===");
        
        try {
            // Verificar conexão
            System.out.println("\n=== Testando conexão com o banco ===");
            if (Conexao.testarConexao()) {
                System.out.println("Conexão com banco de dados OK!");
            } else {
                System.out.println("FALHA na conexão com o banco de dados!");
                return;
            }
            
            // Listar tabelas disponíveis
            System.out.println("\n=== Tabelas disponíveis ===");
            List<String> tabelas = listarTabelas();
            for (String tabela : tabelas) {
                System.out.println(" - " + tabela);
            }
            
            // Verificar estrutura das tabelas mais importantes
            System.out.println("\n=== Estrutura das tabelas ===");
            String[] tabelasVerificar = {
                "RECEITAS", 
                "CATEGORIA-RECEITA", 
                "INGREDIENTES", 
                "INGREDIENTES-RECEITAS", 
                "RESTAURANTES",
                "COZINHEIROS"
            };
            
            for (String tabela : tabelasVerificar) {
                System.out.println("\n== Estrutura da tabela " + tabela + " ==");
                descreverTabela(tabela);
            }
            
            // Verificar contagem de registros nas tabelas
            System.out.println("\n=== Contagem de registros ===");
            for (String tabela : tabelasVerificar) {
                int count = contarRegistros(tabela);
                System.out.println(" - " + tabela + ": " + count + " registro(s)");
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fechar conexão ao finalizar
            Conexao.fecharConexao();
        }
    }
    
    /**
     * Lista todas as tabelas disponíveis no banco de dados
     */
    public static List<String> listarTabelas() throws SQLException {
        List<String> tabelas = new ArrayList<>();
        
        try (Connection conn = Conexao.getConexao()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
            
            while (rs.next()) {
                String nomeTabela = rs.getString("TABLE_NAME");
                tabelas.add(nomeTabela);
            }
        }
        
        return tabelas;
    }
    
    /**
     * Descreve a estrutura de uma tabela (colunas, tipos, etc)
     */
    public static void descreverTabela(String nomeTabela) throws SQLException {
        try (Connection conn = Conexao.getConexao()) {
            DatabaseMetaData metaData = conn.getMetaData();
            
            // Obter informações das colunas
            try (ResultSet rs = metaData.getColumns(null, null, nomeTabela, null)) {
                System.out.println("Colunas encontradas:");
                
                while (rs.next()) {
                    String colName = rs.getString("COLUMN_NAME");
                    String colType = rs.getString("TYPE_NAME");
                    int colSize = rs.getInt("COLUMN_SIZE");
                    boolean nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                    
                    System.out.printf(" - %-25s | %-10s | Tamanho: %-5d | %s%n", 
                            colName, colType, colSize, 
                            nullable ? "Nullable" : "NOT NULL");
                }
            }
            
            // Tentar obter informações de chaves primárias
            try (ResultSet rs = metaData.getPrimaryKeys(null, null, nomeTabela)) {
                while (rs.next()) {
                    String colName = rs.getString("COLUMN_NAME");
                    System.out.println(" * Chave primária: " + colName);
                }
            }
            
            // Tentar obter informações de chaves estrangeiras
            try (ResultSet rs = metaData.getImportedKeys(null, null, nomeTabela)) {
                while (rs.next()) {
                    String fkColName = rs.getString("FKCOLUMN_NAME");
                    String pkTableName = rs.getString("PKTABLE_NAME");
                    String pkColName = rs.getString("PKCOLUMN_NAME");
                    
                    System.out.printf(" * FK: %s -> %s.%s%n", 
                            fkColName, pkTableName, pkColName);
                }
            } catch (SQLException e) {
                // Algumas implementações de drivers podem não suportar isso
                System.out.println(" * Não foi possível obter informações de chaves estrangeiras");
            }
        }
    }
    
    /**
     * Conta quantos registros existem em uma tabela
     */
    public static int contarRegistros(String nomeTabela) throws SQLException {
        String sql = "SELECT COUNT(*) FROM \"" + nomeTabela + "\"";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        
        return 0;
    }
    
    /**
     * Executa uma consulta SQL de teste e exibe os resultados
     */
    public static void executarConsultaTeste(String sql) throws SQLException {
        System.out.println("Executando consulta: " + sql);
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int colCount = metaData.getColumnCount();
            
            // Exibir cabeçalhos das colunas
            for (int i = 1; i <= colCount; i++) {
                System.out.printf("%-25s", metaData.getColumnName(i));
            }
            System.out.println();
            
            // Exibir separador
            for (int i = 1; i <= colCount; i++) {
                System.out.print("-------------------------");
            }
            System.out.println();
            
            // Exibir dados
            while (rs.next()) {
                for (int i = 1; i <= colCount; i++) {
                    System.out.printf("%-25s", rs.getString(i));
                }
                System.out.println();
            }
        }
    }
} 