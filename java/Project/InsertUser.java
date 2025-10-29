package Project;

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

        // ✅ Always use UTF-8 for web output
        response.setContentType("text/html;charset=UTF-8");

        // ✅ Get data from form (register.html)
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            // ✅ Load Oracle Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // ✅ Update DB username + password if different
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "system",
                    "rajesh"
            );

            // ✅ Use your exact column names (as per database desc)
            // ID, FRIST_NAME, LAST_NAME, PASSWORD, EMAIL, MOBILE, CREATED_AT
            String sql = "INSERT INTO user_data " +
                    "(ID, FRIST_NAME, LAST_NAME, PASSWORD, EMAIL, MOBILE, CREATED_AT) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setString(5, email);

            // ✅ MOBILE is NUMBER in Oracle, use setString (Oracle converts automatically)
            ps.setString(6, mobile);

            // ✅ Timestamp for Created Time
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            int result = ps.executeUpdate();

            if (result > 0) {
                // ✅ Success -> Redirect to insertuser.html (your success UI page)
                response.sendRedirect("insertuser.html");
            } else {
                // ❌ Failed -> Redirect back to register form
                response.sendRedirect("register.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
    }
}
