package ufrn.br.ufrn;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/cadastro.html")
public class FiltroCadastro implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpSession session = request.getSession();

        // Verifica se o usuário está logado e se é um administrador
        Boolean logado = (Boolean) session.getAttribute("logado");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        // Se não estiver logado ou não for um admin, redirecionar para a página inicial
        if (logado == null || !logado || isAdmin == null || !isAdmin) {
            response.sendRedirect("index.html?msg=Você não tem permissão para acessar esta página");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}






