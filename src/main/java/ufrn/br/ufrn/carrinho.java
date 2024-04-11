package ufrn.br.ufrn;

import java.util.ArrayList;

public class carrinho {
    private ArrayList<produtos> produtos;

    public carrinho(ArrayList<produtos> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<produtos> produto) {
        this.produtos = produto;
    }

    public produtos getProduto(int id) {
        for (produtos p : produtos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // Retorna null se nenhum produto com o ID fornecido for encontrado
    }

    public void removeProduto(int id) {
        produtos p = getProduto(id);
        if (p != null) {
            produtos.remove(p);
        } else {
            System.out.println("Produto com ID " + id + " não encontrado.");
        }
    }

    public void addProduto(produtos p) {
        produtos.add(p);
    }
}