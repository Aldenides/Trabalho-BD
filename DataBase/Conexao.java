package  DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/NameDoBanco"; 
    private static final String USUARIO = "seuUser";  
    private static final String SENHA = "suaSenha";

    /**
     * Método para estabelecer uma conexão com o banco de dados PostgreSQL.
     *
     * @return Connection - objeto de conexão com o banco de dados.
     */
    public static Connection conectar() {
        Connection conexao = null;
        try {
            // Carrega o driver do PostgreSQL
            Class.forName("org.postgresql.Driver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }
}