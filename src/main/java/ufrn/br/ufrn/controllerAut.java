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
    adminDao admDao = new adminDao();

    @RequestMapping(value = "logar", method = RequestMethod.POST)
    public void Login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var email = request.getParameter("login");  // Renomeado para 'email'
        var password = request.getParameter("password");


        if (admDao.VerificarAdmin(email, password)) {
            // Login de administrador bem-sucedido
            HttpSession session = request.getSession();
            session.setAttribute("logado", true);
            session.setAttribute("isAdmin", true);
            response.sendRedirect("cadastro.html");
        } else if (uDao.VerificarUsuario(email, password)) {
            // Login de usuário comum bem-sucedido
            HttpSession session = request.getSession();
            session.setAttribute("logado", true);
            response.sendRedirect("listaProdutos.html");
        } else {
            // Login falhou para ambos os tipos de usuário
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