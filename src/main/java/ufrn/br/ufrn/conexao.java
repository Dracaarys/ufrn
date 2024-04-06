package ufrn.br.ufrn;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    public static Connection getConnection() throws SQLException, URISyntaxException {
        String dbUri = "localhost"; // Host do banco de dadosa
        String dbPort = "5433"; // Porta do banco de dados
        String dbName = "projetopw"; // Nome do banco de dados

        String username = "postgres"; // Nome de usuário do banco de dados
        String password = "1234"; // Senha do banco de dados
        String dbUrl = "jdbc:postgresql://" + dbUri + ':' + dbPort + "/" + dbName + "?serverTimezone=UTC";

        System.out.println(dbUrl); // Isso é opcional, apenas para debug

        return DriverManager.getConnection(dbUrl, username, password);
    }
}
