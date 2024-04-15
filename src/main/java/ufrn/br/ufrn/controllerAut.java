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
            response.sendRedirect("doListar");
        } else {
            // Login falhou para ambos os tipos de usuário
            response.sendRedirect("index.html?msg=Login falhou");
        }
    }
    @RequestMapping(value = "/formulario", method = RequestMethod.GET)
    public void exibirFormulario(HttpServletResponse response) throws IOException {
        response.sendRedirect("/criarconta");
    }

    @RequestMapping(value = "/criarconta", method = RequestMethod.GET)
    public void CriarAcc(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h2>Criar Conta</h2>");
        response.getWriter().println("<form action='/criarconta' method='post'>");
        response.getWriter().println("Nome: <input type='text' name='nome'><br>");
        response.getWriter().println("Email: <input type='text' name='email'><br>");
        response.getWriter().println("Senha: <input type='password' name='senha'><br>");
        response.getWriter().println("<input type='submit' value='Criar Conta'>");
        response.getWriter().println("</form>");
        response.getWriter().println("</body></html>");
    }
    @RequestMapping(value = "/criarconta", method = RequestMethod.POST)
    public void CriarAcc(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        usuarioDao dao = new usuarioDao();
        boolean sucesso = dao.cadastrarUsuario(nome, email, senha);

        if (sucesso) {
            response.getWriter().println("Conta criada com sucesso!");
        } else {
            response.getWriter().println("Erro ao criar a conta. Por favor, tente novamente.");
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