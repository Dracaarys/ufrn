package ufrn.br.ufrn;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class carrinhoController {

    private final produtoDao produtoDao;

    public carrinhoController() {
        this.produtoDao = new produtoDao();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/doListarCarrinho")
    public void listarCarrinho(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Integer> carrinho = getCarrinhoFromCookie(request);
        List<produtos> listarMercadoria = produtoDao.listarTodosProdutos();

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html><body>");

        // Variável para armazenar o valor total do carrinho
        double totalCarrinho = 0.0;

        // Mostrar carrinho
        writer.println("<h2>Carrinho</h2>");
        writer.println("<ul>");
        for (Integer id : carrinho) {
            produtos produto = produtoDao.getProduto(id);
            if (produto != null) {
                // Adiciona o preço do produto ao valor total do carrinho
                totalCarrinho += produto.getPreco();
                writer.println("<li>" + produto.getNome() + " - R$ " + produto.getPreco() +
                        " <a href='/acaoCarrinho?comando=remove&id=" + produto.getId() + "'>Remover</a></li>");
            }
        }
        writer.println("</ul>");

        // Exibe o valor total do carrinho
        writer.println("<p>Total do Carrinho: R$ " + totalCarrinho + "</p>");

        writer.println("<a href=\"/doListar\">voltar</a>");
        writer.println("</body></html>");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/acaoCarrinho")
    public String acaoCarrinho(HttpServletRequest request, HttpServletResponse response) {
        String comando = request.getParameter("comando");
        int id = Integer.parseInt(request.getParameter("id"));
        List<Integer> carrinho = getCarrinhoFromCookie(request);
        if (carrinho == null) {
            carrinho = new ArrayList<>();
        }

        if ("add".equals(comando)) {
            carrinho.add(id);
        } else if ("remove".equals(comando)) {
            carrinho.remove(Integer.valueOf(id));
        }

        saveCarrinhoToCookie(request, response, carrinho);
        return "redirect:/doListarCarrinho";
    }

    private List<Integer> getCarrinhoFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("carrinho")) {
                    String cookieValue = cookie.getValue();
                    if (cookieValue != null && !cookieValue.isEmpty()) {
                        return Arrays.stream(cookieValue.split("-"))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());
                    }
                    break; // found our carrinho cookie, no need to keep looping
                }
            }
        }
        return new ArrayList<>();
    }

    private void saveCarrinhoToCookie(HttpServletRequest request, HttpServletResponse response, List<Integer> carrinho) {
        String cookieValue = carrinho.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("-"));

        Cookie cookie = new Cookie("carrinho", cookieValue);
        cookie.setMaxAge(48 * 60 * 60); // 48 horas em segundos
        cookie.setPath("/");
        cookie.setHttpOnly(true); // Para evitar acesso via JavaScript
        response.addCookie(cookie);
    }
}
//precisa acabar o restoo