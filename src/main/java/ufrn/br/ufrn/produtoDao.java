package ufrn.br.ufrn;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class produtoDao {
    // Método para cadastrar um produto no banco de dados
    public void cadastrarProduto(produtos produto) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conexao.getConnection();
            String sql = "INSERT INTO produtos (nome, descricao, preco) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setFloat(3, produto.getPreco());
            statement.executeUpdate();
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para listar todos os produtos cadastrados
    public List<produtos> listarTodosProdutos() {
        List<produtos> produtos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = conexao.getConnection();
            String sql = "SELECT * FROM produtos";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                float preco = resultSet.getFloat("preco");
                produtos produto = new produtos(id, nome, descricao, preco);
                produtos.add(produto);
            }
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return produtos;
    }

    // Método para atualizar um produto no banco de dados
    public void atualizarProduto(produtos produto) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conexao.getConnection();
            String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getDescricao());
            statement.setFloat(3, produto.getPreco());
            statement.setInt(4, produto.getId());
            statement.executeUpdate();
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para remover um produto do banco de dados
    public void removerProduto(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = conexao.getConnection();
            String sql = "DELETE FROM produtos WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
