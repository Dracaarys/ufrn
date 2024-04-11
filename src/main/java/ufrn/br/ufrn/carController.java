package ufrn.br.ufrn;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;


public class carController {

    private final produtoDao produtoDao;

    aaa
    public carController(ufrn.br.ufrn.produtoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    @RequestMapping(value = "/acaoCarrinho", method = RequestMethod.GET)
    public void acaoCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("comando");
        String prodId = request.getParameter("id");

        if (command != null && prodId != null) {
            int idProduto = Integer.parseInt(prodId); // Converte o ID para inteiro

            // Verifica se o carrinho já está na sessão
            HttpSession session = request.getSession();
            carrinho carrinho = (carrinho) session.getAttribute("carrinho");
            if (carrinho == null) {
                carrinho = new carrinho(new ArrayList<produtos>());
                session.setAttribute("carrinho", carrinho);
            }

            // Busca o produto no banco de dados
            produtos produto = produtoDao.getProduto(idProduto);

            if (produto != null) {
                if (command.equals("add")) {
                    carrinho.addProduto(produto);
                    System.out.println("Produto adicionado ao carrinho: " + produto.getNome());
                } else if (command.equals("remove")) {
                    carrinho.removeProduto(idProduto);
                    System.out.println("Produto removido do carrinho: " + produto.getNome());
                }
            } else {
                System.out.println("Produto não encontrado");
                response.getWriter().println("Produto não encontrado");
            }
        } else {
            System.out.println("Erro ao adicionar produto ao carrinho");
            response.getWriter().println("Erro ao adicionar produto ao carrinho");
        }

        response.sendRedirect(request.getContextPath() + "/verCarrinho");
    }

    @RequestMapping(value = "/verCarrinho", method = RequestMethod.GET)
    public void exibirCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        carrinho carrinho = (carrinho) session.getAttribute("carrinho");

        if (carrinho == null || carrinho.getProdutos().isEmpty()) {
            System.out.println("Carrinho Vazio");
        } else {
            System.out.println("Seu Carrinho:");
            for (produtos produto : carrinho.getProdutos()) {
                System.out.println("Nome: " + produto.getNome());
                System.out.println("Descrição: " + produto.getDescricao());
                System.out.println("Preço: " + produto.getPreco());
            }
        }

        response.getWriter().println("<h1>Carrinho</h1>");
    }
}


