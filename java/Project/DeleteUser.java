package Project;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteuser")
public class DeleteUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("admin")) {
            response.getWriter().println("<h2 style='color:red;text-align:center;'>Access Denied ❌</h2>");
            return;
        }

        String uidStr = request.getParameter("id");

        if (uidStr == null || uidStr.isEmpty()) {
            response.getWriter().println("<h3 style='color:red;text-align:center;'>User ID Missing!</h3>");
            return;
        }

        try {
            int uid = Integer.parseInt(uidStr);

            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh"
            );

            // Delete user
            PreparedStatement ps = con.prepareStatement("DELETE FROM user_data WHERE ID=?");
            ps.setInt(1, uid);
            int result = ps.executeUpdate();

            con.close();

            if (result > 0) {
                response.sendRedirect("deleteuser.html?deleted=true"); // show popup
            } else {
                response.getWriter().println("<h3 style='color:yellow;text-align:center;'>⚠ User Not Found!</h3>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3 style='color:red;text-align:center;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
