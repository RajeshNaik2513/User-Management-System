import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/insertuser")
public class InsertUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // ✅ 1. Get form data (names match your register.html form)
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");

        // ✅ 2. JDBC connection
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "yourpassword");

            // ✅ 3. Match column names exactly as in your Oracle table
            // (If your column name is FRIST_NAME — keep it; if you renamed it to FIRST_NAME, use that)
            String sql = "INSERT INTO user_data (ID, FRIST_NAME, LAST_NAME, PASSWORD, EMAIL, MOBILE, CREATED_AT) VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setString(5, email);
            ps.setString(6, mobile);
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<script>alert('User Registered Successfully!'); window.location='viewalluser';</script>");
            } else {
                out.println("<script>alert('Registration Failed!'); window.location='register.html';</script>");
            }

        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
    }
}
