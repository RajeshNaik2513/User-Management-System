package Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/edituser")
public class UpdateUserForm extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("admin")) {
            out.println("<h2 style='color:red;text-align:center;'>Access Denied ❌</h2>");
            out.println("<a href='home.html'>🏠 Back to Home</a>");
            return;
        }

        String uidStr = request.getParameter("uid");
        if (uidStr == null || uidStr.isEmpty()) {
            out.println("<h3 style='color:red;text-align:center;'>Error: User ID is missing!</h3>");
            out.println("<a href='viewalluser'>Back to Users</a>");
            return;
        }

        try {
            int uid = Integer.parseInt(uidStr);

            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh"
            );

            PreparedStatement ps = con.prepareStatement(
                    "SELECT uid, frist_name, last_name, email, mobile FROM user_data WHERE uid=?"
            );
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String fname = rs.getString("FRIST_NAME"); // match DB column
                String lname = rs.getString("LAST_NAME");
                String email = rs.getString("EMAIL");
                long mobile = rs.getLong("MOBILE");

                // --- Display editable form ---
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>Edit User</title>");
                out.println("<style>");
                out.println("body{font-family:Poppins,sans-serif;background:linear-gradient(135deg,#1e3c72,#2a5298);color:#fff;text-align:center;}");
                out.println("form{margin:50px auto;background:rgba(255,255,255,0.15);padding:30px;border-radius:25px;width:350px;}");
                out.println("input{width:90%;padding:10px;margin:10px 0;border-radius:10px;border:none;}");
                out.println("button{padding:10px 25px;border:none;border-radius:20px;background:#00c6ff;color:#fff;font-weight:600;cursor:pointer;}");
                out.println("button:hover{background:#7ee8fa;color:#002b80;}");
                out.println("a{display:block;margin-top:15px;color:#fff;text-decoration:none;}");
                out.println("</style></head><body>");

                out.println("<h2>Edit User</h2>");
                out.println("<form action='updateuser' method='post'>");
                out.println("<input type='hidden' name='uid' value='" + uid + "'>");
                out.println("<input type='text' name='first_name' value='" + fname + "' placeholder='First Name' required><br>");
                out.println("<input type='text' name='last_name' value='" + lname + "' placeholder='Last Name' required><br>");
                out.println("<input type='email' name='email' value='" + email + "' placeholder='Email' required><br>");
                out.println("<input type='text' name='mobile' value='" + mobile + "' placeholder='Mobile' required><br>");
                out.println("<button type='submit'>Update User</button>");
                out.println("</form>");
                out.println("<a href='viewalluser'>🏠 Back to Users</a>");
                out.println("</body></html>");
            } else {
                out.println("<h3 style='color:red;text-align:center;'>Error: User not found!</h3>");
                out.println("<a href='viewalluser'>Back to Users</a>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            out.println("<h3 style='color:red;text-align:center;'>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }
}
