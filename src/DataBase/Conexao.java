package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";
    private static Connection conexao;

    /**
     * Obtém uma conexão ativa com o banco de dados
     * @return Connection objeto de conexão
     * @throws SQLException se ocorrer erro na conexão
     * @throws ClassNotFoundException 
     */
public static Connection getConexao() throws SQLException {
    if (conexao == null || conexao.isClosed()) {
        try {
            // Garante que o driver seja carregado explicitamente
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", USUARIO);
            props.setProperty("password", SENHA);
            props.setProperty("ssl", "false");
            props.setProperty("tcpKeepAlive", "true");

            conexao = DriverManager.getConnection(URL, props);
            conexao.setAutoCommit(false);
            System.out.println("[INFO] Nova conexão estabelecida com PostgreSQL");
        } catch (ClassNotFoundException e) {
            System.err.println("[ERRO] Driver PostgreSQL não encontrado: " + e.getMessage());
            throw new SQLException("Driver PostgreSQL não encontrado", e); // encapsula
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao obter conexão: " + e.getMessage());
            throw new SQLException("Falha ao conectar ao banco de dados", e);
        }
    }
    return conexao;
}

    /**
     * Fecha a conexão atual
     */
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("[INFO] Conexão fechada com sucesso");
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Ao fechar conexão: " + e.getMessage());
        } finally {
            conexao = null;  // Garante que a referência seja limpa
        }
    }

    /**
     * Testa a conexão com o banco
     * @return true se a conexão for válida
     */
    public static boolean testarConexao() {
        try (Connection conn = getConexao()) {
            return conn != null && conn.isValid(2);  // Testa com timeout de 2 segundos
        } catch (SQLException e) {
            System.err.println("[ERRO] Falha ao testar conexão: " + e.getMessage());
            return false;
        }
    }

    /**
     * Método alternativo para compatibilidade
     * @deprecated Prefira usar getConexao()
     */
    @Deprecated
    public static Connection conectar() throws SQLException {
        return getConexao();
    }
}