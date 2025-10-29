package Project;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Getuser")
public class Getuser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if (idStr == null || idStr.trim().isEmpty()) {
            response.getWriter().write("ERROR:ID_MISSING");
            return;
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh"
            );

            PreparedStatement ps = con.prepareStatement(
                    "SELECT FRIST_NAME, LAST_NAME, EMAIL, MOBILE FROM user_data WHERE ID = ?"
            );

            ps.setInt(1, Integer.parseInt(idStr));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String userData =
                        rs.getString("FRIST_NAME") + "," +
                        rs.getString("LAST_NAME") + "," +
                        rs.getString("EMAIL") + "," +
                        rs.getString("MOBILE");

                response.getWriter().write(userData);
            } else {
                response.getWriter().write("NOT_FOUND");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace(); // <-- important to view exact error in console/logs
            response.getWriter().write("TECHNICAL_ERROR");
        }
    }
}
