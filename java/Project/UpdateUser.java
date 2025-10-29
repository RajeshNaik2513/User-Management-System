package Project;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/updateuser")
public class UpdateUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
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
            String fname = request.getParameter("first_name");
            String lname = request.getParameter("last_name");
            String email = request.getParameter("email");
            long mobile = Long.parseLong(request.getParameter("mobile"));

            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh"
            );

            // --- Update user ---
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE user_data SET frist_name=?, last_name=?, email=?, mobile=? WHERE uid=?"
            );
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setLong(4, mobile);
            ps.setInt(5, uid);
            ps.executeUpdate();

            // --- Log action to user_audit ---
            PreparedStatement psAudit = con.prepareStatement(
                    "INSERT INTO user_audit(user_id, frist_name, last_name, action_type) VALUES (?, ?, ?, ?)"
            );
            psAudit.setInt(1, uid);
            psAudit.setString(2, fname);
            psAudit.setString(3, lname);
            psAudit.setString(4, "UPDATE");
            psAudit.executeUpdate();

            con.close();

            response.sendRedirect("viewalluser");

        } catch (Exception e) {
            response.getWriter().println("<h3 style='color:red;text-align:center;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(response.getWriter());
        }
    }
}
