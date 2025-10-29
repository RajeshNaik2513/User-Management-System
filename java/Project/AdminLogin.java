package Project;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/adminlogin")
public class AdminLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("user");
        String password = request.getParameter("pwd");


        if ("admin".equals(user) && "admin".equals(password)) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("role", "admin"); // <-- set role as admin

            // Redirect to admin dashboard
            response.sendRedirect("admindashboard.html");
        } else {
            out.println("<script>");
            out.println("alert('Invalid Admin Credentials!');");
            out.println("window.location='adminlogin.html';");
            out.println("</script>");
        }
}
}