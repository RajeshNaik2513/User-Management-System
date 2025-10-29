package Project;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/deleteuser")
public class DeleteUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("admin")) {
            response.getWriter().println("<h2 style='color:red;text-align:center;'>Access Denied ❌</h2>");
            return;
        }

        String uidStr = request.getParameter("uid");
        if (uidStr == null || uidStr.isEmpty()) {
            response.getWriter().println("<h3 style='color:red;text-align:center;'>Error: User ID is missing!</h3>");
            return;
        }

        try {
            int uid = Integer.parseInt(uidStr);

            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh"
            );

            // --- Get user info for audit ---
            PreparedStatement psSelect = con.prepareStatement("SELECT first_name, last_name FROM user_data WHERE uid=?");
            psSelect.setInt(1, uid);
            ResultSet rs = psSelect.executeQuery();
            String fname = "";
            String lname = "";
            if (rs.next()) {
                fname = rs.getString("first_name");
                lname = rs.getString("last_name");
            }

            // --- Delete user ---
            PreparedStatement ps = con.prepareStatement("DELETE FROM user_data WHERE uid=?");
            ps.setInt(1, uid);
            ps.executeUpdate();

            // --- Log deletion ---
            PreparedStatement psAudit = con.prepareStatement(
                    "INSERT INTO user_audit(user_id, frist_name, last_name, action_type) VALUES (?, ?, ?, ?)"
            );
            psAudit.setInt(1, uid);
            psAudit.setString(2, fname);
            psAudit.setString(3, lname);
            psAudit.setString(4, "DELETE");
            psAudit.executeUpdate();

            con.close();

            response.sendRedirect("viewalluser");

        } catch (Exception e) {
            response.getWriter().println("<h3 style='color:red;text-align:center;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(response.getWriter());
        }
    }
}
