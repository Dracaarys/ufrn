package ufrn.br.ufrn;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class controllerProduto {



    produtoDao pDao = new produtoDao();


    @RequestMapping(value = "cadastroMercadoria", method = RequestMethod.POST)
    public void CadastroProduto(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        float preco = Float.parseFloat(request.getParameter("preco"));
        int estoque = Integer.parseInt(request.getParameter("estoque"));

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


        for (var t1 : listarMercadoria){
            writer.println("<p>"+"nome:" +t1.getNome() + "</p>");
            writer.println("<p>"+"Descrição:" +t1.getDescricao() + "</p>");
            writer.println("<p>"+"Preço:" + t1.getPreco() + "</p> ");
            writer.println("<p>"+"Estoque:"+ t1.getEstoque() + "</p> ");
            writer.println("<td><a href='/acaoCarrinho?comando=add&id=" + t1.getId() + "'>Adicionar no carrinho</a></td>");
        }

        writer.println("<a href=\"/logout\">Logout</a>");
        writer.println("</html>" +
                "</body>");
    }

}
