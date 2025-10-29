package Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/userdashboard")
public class UserDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        try {
            // ✅ Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.OracleDriver");

            // ✅ Connect to Database
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh");

            // ✅ Prepare Query
            PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM user_data WHERE ID = ? AND PASSWORD = ?");

            pst.setInt(1, Integer.parseInt(id));
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // ✅ Successful login
                HttpSession session = request.getSession();
                session.setAttribute("userid", rs.getInt("ID"));
                session.setAttribute("firstname", rs.getString("FRIST_NAME"));
                session.setAttribute("lastname", rs.getString("LAST_NAME"));
                session.setAttribute("email", rs.getString("EMAIL"));
                session.setAttribute("mobile", rs.getLong("MOBILE"));

                // Redirect to dashboard page
                response.sendRedirect("userdashboard.html");
            } else {
                // ❌ Invalid login
                out.println("<!DOCTYPE html><html><head><title>Login Failed</title>");
                out.println("<style>");
                out.println("body{background:linear-gradient(135deg,#2a5298,#1e3c72);color:#fff;"
                        + "display:flex;flex-direction:column;justify-content:center;align-items:center;height:100vh;font-family:Poppins,sans-serif;}");
                out.println(".error-box{background:rgba(255,255,255,0.1);padding:30px;border-radius:15px;text-align:center;}");
                out.println(".error-box a{display:inline-block;margin-top:10px;padding:8px 25px;"
                        + "background:linear-gradient(135deg,#ff4b2b,#ff416c);color:#fff;text-decoration:none;border-radius:25px;}");
                out.println(".error-box a:hover{background:linear-gradient(135deg,#7ee8fa,#80ff72);color:#002b80;}");
                out.println("</style></head><body>");
                out.println("<div class='error-box'>");
                out.println("<h2>❌ Invalid Credentials</h2>");
                out.println("<p>Please check your ID and password.</p>");
                out.println("<a href='login.html'>Try Again</a>");
                out.println("</div></body></html>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2 style='color:red;text-align:center;margin-top:50px;'>Technical Error. Please try again later.</h2>");
            out.println("<div style='text-align:center;'><a href='login.html'>Back to Login</a></div>");
        }
    }
}
