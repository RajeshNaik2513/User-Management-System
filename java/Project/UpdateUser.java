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

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh");

            PreparedStatement ps = con.prepareStatement(
                    "UPDATE user_data SET frist_name=?, last_name=?, email=?, mobile=? WHERE id=?");

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, mobile);
            ps.setInt(5, id);

            ps.executeUpdate();
            con.close();

            response.sendRedirect("updateuser.html?success=true");

        } catch (Exception e) {
            System.out.println(e);
            response.sendRedirect("updateuser.html?error=true");
        }
    }
}
