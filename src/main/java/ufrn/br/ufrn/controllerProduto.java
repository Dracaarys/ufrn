package ufrn.br.ufrn;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class controllerProduto {



    produtoDao pDao = new produtoDao();


    @RequestMapping(value = "cadastroMercadoria", method = RequestMethod.POST)
    public void CadastroProduto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        float preco = 0.0f; // Valor padrão caso não seja possível converter
        int estoque = 0; // Valor padrão caso não seja possível converter

        String precoParam = request.getParameter("preco");
        if (precoParam != null && !precoParam.isEmpty()) {
            try {
                preco = Float.parseFloat(precoParam);
            } catch (NumberFormatException e) {
            }
        }

        String estoqueParam = request.getParameter("estoque");
        if (estoqueParam != null && !estoqueParam.isEmpty()) {
            try {
                estoque = Integer.parseInt(estoqueParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        produtos produto = new produtos(null, nome, descricao, preco, estoque);
        pDao.cadastrarProduto(produto);

        response.sendRedirect("cadastro.html?msg=cadastro concluido");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/doListar")
    public void listarMercadoria(HttpServletRequest request, HttpServletResponse response) throws IOException {


        var dao = new produtoDao();
        var writer = response.getWriter();

        String browser = request.getHeader("pipoca");


        var listarMercadoria = dao.listarTodosProdutos();
        response.setContentType("text/HTML");

        HttpSession session = request.getSession();
        Boolean logado = (Boolean) session.getAttribute("logado");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        if (logado == null || !logado) {
            response.sendRedirect("index.html?msg=Você não tem permissão para acessar esta página");
            return;
        }

        for (var t1 : listarMercadoria){
            writer.println("<p>"+"nome:" +t1.getNome() + "</p>");
            writer.println("<p>"+"Descrição:" +t1.getDescricao() + "</p>");
            writer.println("<p>"+"Preço:" + t1.getPreco() + "</p> ");
            writer.println("<p>"+"Estoque:"+ t1.getEstoque() + "</p> ");

            if (isAdmin == null || !isAdmin) {
                if (t1.getEstoque() < 1) {
                    writer.println("<td>Produto Indisponível</td>");
                } else {
                    writer.println("<td><a href='/acaoCarrinho?comando=add&id=" + t1.getId() + "'>Adicionar no carrinho</a></td>");
                }
            }


        }
        if (isAdmin == null || !isAdmin){
            writer.println("<a href=\"/doListarCarrinho\">ver carrinho</a>");
        }
        writer.println("<a href=\"/logout\">Logout</a>");
        writer.println("</html>" +
                "</body>");
    }


}
