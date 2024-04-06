package ufrn.br.ufrn;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class controllerAut {

    usuarioDao uDao = new usuarioDao();

    @RequestMapping(value = "logar", method = RequestMethod.POST)
    public void Login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var email = request.getParameter("login");  // Renomeado para 'email'
        var password = request.getParameter("password");

        if (uDao.VerificarUsuario(email, password)) {  // Passando os parâmetros corretamente
            // Login bem-sucedido
            HttpSession session = request.getSession();
            session.setAttribute("logado", true);
            response.sendRedirect("cadastroform.html");
        } else {
            // Login falhou
            response.sendRedirect("index.html?msg=Login falhou");
        }
    }

    @RequestMapping("/logout")
    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect("index.html?msg=Usuario deslogado");
    }

    // Outros métodos...
}