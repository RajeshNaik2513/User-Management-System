package Project;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/loginuser")
public class LoginUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "rajesh"
            );

            PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM user_data WHERE ID=? AND PASSWORD=?"
            );
            pst.setInt(1, Integer.parseInt(id));
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("userid", rs.getInt("ID"));
                session.setAttribute("firstname", rs.getString("FRIST_NAME"));
                response.sendRedirect("userdashboard.html");
            } else {
                // Display styled login page with error message
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>User Login</title>");
                out.println("<style>");
                // CSS same as your login.html with minor changes for error
                out.println("*{margin:0;padding:0;box-sizing:border-box;font-family:'Poppins',sans-serif;}");
                out.println("body{display:flex;justify-content:center;align-items:center;height:100vh;background:url('https://res.cloudinary.com/dpxrmf3vc/image/upload/v1761115864/WhatsApp_Image_2025-10-22_at_12.02.17_58a4599f_gj5gws.jpg') no-repeat center/cover;color:#fff;position:relative;perspective:1200px;}");
                out.println("body::before{content:'';position:absolute;inset:0;background:rgba(0,0,0,0.45);backdrop-filter:blur(2px);z-index:0;}");
                out.println(".login-container{position:relative; z-index:1; background:rgba(255,255,255,0.1); padding:50px 30px; border-radius:25px; width:90%; max-width:400px; text-align:center; box-shadow:0 0 30px rgba(0,198,255,0.3); backdrop-filter:blur(15px);}");
                out.println(".logo-wrapper{position:relative;width:120px;height:120px;margin:0 auto 25px auto;}");
                out.println(".logo-wrapper::after{content:'';position:absolute;bottom:-10px;left:50%;transform:translateX(-50%);width:80px;height:10px;background:rgba(0,255,255,0.2);border-radius:50%;filter:blur(8px);animation:shadowPulse 3s ease-in-out infinite;}");
                out.println(".logo{width:100%;height:100%;border-radius:50%;object-fit:cover;border:3px solid #00c6ff;box-shadow:0 0 25px #00c6ff;transform-style: preserve-3d;animation:rotate3D 6s linear infinite, float 3s ease-in-out infinite;}");
                out.println("@keyframes rotate3D {0% { transform: rotateY(0deg); } 100% { transform: rotateY(360deg); }}");
                out.println("@keyframes float {0%,100%{transform: translateY(0);}50%{transform: translateY(-15px);}}");
                out.println("@keyframes shadowPulse {0%,100%{transform:translateX(-50%) scaleX(1);}50%{transform:translateX(-50%) scaleX(1.3);}}");
                out.println(".login-container h2{margin-bottom:20px; color:#aeefff;text-shadow:0 0 10px #00c6ff;}");
                out.println(".error-msg{color:#ff4b5c;font-weight:600;margin-bottom:15px;}");
                out.println("form{display:flex; flex-direction:column;}");
                out.println("input{margin-bottom:20px;padding:12px;border-radius:8px;border:none;outline:none;font-size:1rem;background:rgba(255,255,255,0.15);color:#fff;box-shadow:0 0 8px rgba(0,198,255,0.3);transition:0.3s;}");
                out.println("input::placeholder{color:#b0e0ff;}");
                out.println("input:focus{background:rgba(255,255,255,0.25);box-shadow:0 0 15px #00ffff;}");
                out.println("input[type='submit']{background:linear-gradient(135deg,#00c6ff,#0072ff);color:#fff;cursor:pointer;font-weight:600;transition:0.3s;}");
                out.println("input[type='submit']:hover{background:linear-gradient(135deg,#7ee8fa,#80ff72);box-shadow:0 0 20px #00ffff;}");
                out.println(".links{margin-top:10px;}");
                out.println(".links a{color:#aeefff;text-decoration:none;margin:5px;display:inline-block;transition:0.3s;}");
                out.println(".links a:hover{color:#00ffff;}");
                out.println(".footer{margin-top:25px;font-size:0.85rem;color:#b8eaff;}");
                out.println(".footer .heart{ color:#00b4ff; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='login-container'>");
                out.println("<div class='logo-wrapper'><img class='logo' src='https://res.cloudinary.com/dpxrmf3vc/image/upload/v1761117224/WhatsApp_Image_2025-10-22_at_12.42.36_4a0c1a3d_jcdyy4.jpg' alt='Logo'></div>");
                out.println("<h2>User Login</h2>");
                out.println("<div class='error-msg'>Invalid ID or Password</div>");
                out.println("<form action='loginuser' method='post'>");
                out.println("<input type='text' name='id' placeholder='User ID' required>");
                out.println("<input type='password' name='password' placeholder='Password' required>");
                out.println("<input type='submit' value='Login'>");
                out.println("</form>");
                out.println("<div class='links'><a href='home.html'>⬅ Back to Home</a> | <a href='register.html'>Register Now</a></div>");
                out.println("<div class='footer'>© 2025 User Management System | Designed with <span class='heart'>💙</span> by Rajesh Naik</div>");
                out.println("</div></body></html>");
            }
            con.close();
        } catch(Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
            out.println("<a href='login.html'>Back to Login</a>");
        }
    }
}
