package Project;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/viewalluser")
public class ViewallUser extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>View All Users | User Management System</title>");
        out.println("<style>");
        out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Poppins',sans-serif;}");
        out.println("body{min-height:100vh;background:url('https://res.cloudinary.com/dpxrmf3vc/image/upload/v1761115864/WhatsApp_Image_2025-10-22_at_12.02.17_58a4599f_gj5gws.jpg') no-repeat center/cover;color:#fff;display:flex;flex-direction:column;align-items:center;justify-content:flex-start;padding:40px 20px;position:relative;perspective:1000px;}");
        out.println("body::before{content:'';position:absolute;inset:0;background:rgba(0,0,0,0.6);backdrop-filter:blur(3px);z-index:0;}");
        out.println(".container{position:relative;z-index:1;width:95%;max-width:1100px;background:rgba(255,255,255,0.08);border-radius:20px;box-shadow:0 0 30px rgba(0,198,255,0.4);padding:30px;overflow:hidden;animation:fadeIn 1.2s ease-out;}");
        out.println("@keyframes fadeIn{from{opacity:0;transform:translateY(20px);}to{opacity:1;transform:translateY(0);}}");
        out.println(".logo-wrapper{display:flex;flex-direction:column;align-items:center;margin-bottom:25px;}");
        out.println(".logo{width:110px;height:110px;border-radius:50%;object-fit:cover;border:3px solid #00c6ff;box-shadow:0 0 25px #00c6ff;animation:rotate3D 6s linear infinite, float 3s ease-in-out infinite;}");
        out.println("@keyframes rotate3D{0%{transform:rotateY(0deg);}100%{transform:rotateY(360deg);}}");
        out.println("@keyframes float{0%,100%{transform:translateY(0);}50%{transform:translateY(-12px);}}");
        out.println("h2{margin-top:15px;color:#aeefff;text-shadow:0 0 10px #00c6ff;font-size:1.8rem;}");
        out.println("table{width:100%;border-collapse:collapse;margin-top:20px;border-radius:15px;overflow:hidden;background:rgba(255,255,255,0.05);box-shadow:0 0 20px rgba(0,198,255,0.3);}");
        out.println("th,td{padding:14px;text-align:center;border-bottom:1px solid rgba(255,255,255,0.1);}");
        out.println("th{background:rgba(255,255,255,0.15);color:#00c6ff;text-transform:uppercase;letter-spacing:1px;}");
        out.println("tr:nth-child(even){background:rgba(255,255,255,0.05);}");
        out.println("tr:hover{background:rgba(255,255,255,0.1);transition:0.3s;}");
        out.println(".back-btn{display:inline-block;margin-top:25px;background:linear-gradient(135deg,#00c6ff,#0072ff);color:#fff;padding:12px 25px;border:none;border-radius:25px;cursor:pointer;font-weight:600;transition:0.3s;text-decoration:none;box-shadow:0 0 15px #00c6ff;}");
        out.println(".back-btn:hover{background:linear-gradient(135deg,#7ee8fa,#80ff72);box-shadow:0 0 25px #7ee8fa,0 0 40px #80ff72;transform:translateY(-2px);}");
        out.println("footer{margin-top:20px;color:#aeefff;font-size:0.9rem;text-align:center;text-shadow:0 0 10px #00c6ff;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='logo-wrapper'>");
        out.println("<img class='logo' src='https://res.cloudinary.com/dpxrmf3vc/image/upload/v1761117224/WhatsApp_Image_2025-10-22_at_12.42.36_4a0c1a3d_jcdyy4.jpg' alt='Logo'>");
        out.println("<h2>Registered Users</h2>");
        out.println("</div>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Mobile</th><th>Created At</th></tr>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM user_data");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("FRIST_NAME");
                String lastName = rs.getString("LAST_NAME");
                String email = rs.getString("EMAIL");
                long mobile = rs.getLong("MOBILE");
                Timestamp timestamp = rs.getTimestamp("CREATED_AT");

                String formattedTime = (timestamp != null)
                        ? timestamp.toString().substring(0, 19)
                        : "—";

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + firstName + "</td>");
                out.println("<td>" + lastName + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + mobile + "</td>");
                out.println("<td>" + formattedTime + "</td>");
                out.println("</tr>");
            }

            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='7' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
        }

        out.println("</table>");
        out.println("<a href='userdashboard.html' class='back-btn'> Back to Dashboard</a>");
        out.println("<footer>© 2025 User Management System | Designed with  by Rajesh Naik</footer>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

	}

}
