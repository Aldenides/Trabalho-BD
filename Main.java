import DataBase.Conexao;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = Conexao.conectar();

        if (conn != null) {
            System.out.println("Conexão com o PostgreSQL estabelecida com sucesso!");
        } else {
            System.out.println("Falha na conexão com o banco de dados.");
        }
    }
}