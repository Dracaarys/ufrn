package ufrn.br.ufrn;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class usuarioDao {
    
    public boolean VerificarUsuario(String email, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
    
        try {
            connection = conexao.getConnection();
            String sql = "SELECT * FROM clientes WHERE email = ? AND senha = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
    
            return resultSet.next(); 
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace(); 
            return false; // 
        } finally {
            
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
    }
    
}
