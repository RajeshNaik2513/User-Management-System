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
        out.println("<title>View Users | User Management</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");

        out.println("<style>");
        out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Inter',sans-serif;}");

        out.println("body{background:url('https://res.cloudinary.com/dpxrmf3vc/image/upload/v1761115864/WhatsApp_Image_2025-10-22_at_12.02.17_58a4599f_gj5gws.jpg') no-repeat center center/cover;");
        out.println("display:flex;justify-content:center;align-items:center;min-height:100vh;");
        out.println("overflow:hidden;position:relative;color:#fff;}");
        out.println("body::before{content:'';position:absolute;inset:0;background:rgba(0,0,0,0.40);backdrop-filter:blur(3px);}");


        // ✅ Dashboard Container (Same as updateuser.html)
        out.println(".dashboard-container{display:flex;width:90%;max-width:1300px;height:85vh;border-radius:24px;");
        out.println("background:rgba(10,20,35,0.30);border:1.5px solid rgba(0,198,255,0.4);overflow:hidden;");
        out.println("position:relative;z-index:2;}");

        // ✅ Sidebar (Same as updateuser.html)
        out.println(".sidebar{width:230px;display:flex;flex-direction:column;padding:24px;background:rgba(10,25,40,0.40);");
        out.println("border-right:1px solid rgba(0,198,255,0.4);} ");
        out.println(".sidebar-logo{display:flex;align-items:center;gap:10px;margin-bottom:40px;color:#00c7ff;font-weight:700;font-size:1.3rem;}");
        out.println(".sidebar-logo img{width:42px;height:42px;border-radius:50%;border:2px solid #00b4ff;}");

        out.println(".sidebar-nav ul{list-style:none;}");
        out.println(".sidebar-nav li{margin-bottom:10px;}");
        out.println(".sidebar-nav a{display:flex;align-items:center;gap:10px;padding:12px;text-decoration:none;color:#bcd9ea;border-radius:10px;}");
        out.println(".sidebar-nav a:hover,.sidebar-nav a.active{background:rgba(0,198,255,0.2);color:#fff;}");

        out.println(".sidebar-footer{margin-top:auto;}");
        out.println(".sidebar-footer button{width:100%;padding:12px;border:none;border-radius:25px;");
        out.println("background:linear-gradient(135deg,#ff416c,#ff4b2b);cursor:pointer;color:#fff;}");

        // ✅ Main Content (table wrapper like card style)
        out.println(".main-content{flex-grow:1;padding:40px;overflow-y:auto;}");
        out.println(".table-box{width:90%;margin:auto;padding:25px;border-radius:20px;background:rgba(255,255,255,0.12);backdrop-filter:blur(12px);box-shadow:0 0 20px rgba(0,0,0,0.35);} ");
        out.println(".main-content h2{text-align:center;margin-bottom:15px;color:#00dfff;}");

        out.println("table{width:100%;border-collapse:collapse;border-radius:10px;overflow:hidden;}");
        out.println("th,td{padding:12px;text-align:center;font-size:15px;border-bottom:1px solid rgba(255,255,255,0.2);} ");
        out.println("th{background:rgba(255,255,255,0.3);color:#032252;font-weight:600;}");
        out.println("tr:hover{background:rgba(255,255,255,0.15);} ");
        out.println(".back-btn{text-decoration:none;display:flex;justify-content:center;margin-top:20px;padding:12px;background:linear-gradient(135deg,#00c6ff,#0072ff);color:white;border-radius:8px;width:180px;margin-left:auto;margin-right:auto;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='dashboard-container'>");

        // ✅ Sidebar (Same Flow)
        out.println("<aside class='sidebar'>");
        out.println("<div class='sidebar-logo'><img src='https://cdn-icons-png.flaticon.com/512/3135/3135715.png'><span>User</span></div>");
        out.println("<nav class='sidebar-nav'><ul>");
        out.println("<li><a href=\"userdashboard.html\">Dashboard</a></li>");
        out.println("<li><a href='register.html'>Insert Data</a></li>");
        out.println("<li><a class='active' href='viewalluser'>View Users</a></li>");
        out.println("<li><a href='updateuser.html'>Update User</a></li>");
        out.println("</ul></nav>");
        out.println("<div class='sidebar-footer'><button onclick=\"window.location.href='home.html'\">Logout</button></div>");
        out.println("</aside>");

        // ✅ Main Content
        out.println("<main class='main-content'>");
        out.println("<div class='table-box'>");
        out.println("<h2>Registered Users</h2>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID</th><th>First Name</th><th>Last Name</th><th>Email</th><th>Mobile</th><th>Created At</th>");
        out.println("</tr>");

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh"
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM user_data ORDER BY ID");

            while (rs.next()) {
                String createdAt = (rs.getTimestamp("CREATED_AT") != null)
                        ? rs.getTimestamp("CREATED_AT").toString().substring(0, 19)
                        : "—";

                out.println("<tr>");
                out.println("<td>"+rs.getInt("ID")+"</td>");
                out.println("<td>"+rs.getString("FRIST_NAME")+"</td>");
                out.println("<td>"+rs.getString("LAST_NAME")+"</td>");
                out.println("<td>"+rs.getString("EMAIL")+"</td>");
                out.println("<td>"+rs.getString("MOBILE")+"</td>");
                out.println("<td>"+createdAt+"</td>");
                out.println("</tr>");
            }

            rs.close();
            st.close();
            con.close();
        }
        catch (Exception e) {
            out.println("<tr><td colspan='6' style='color:red;'>ERROR: " + e.getMessage() + "</td></tr>");
        }

        out.println("</table>");
        out.println("</div>");

        out.println("<a href='userdashboard.html' class='back-btn'>Back to Dashboard</a>");
        out.println("</main>");

        out.println("</div>"); // dashboard-container
        out.println("</body>");
        out.println("</html>");
    }
}
