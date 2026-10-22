package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import models.Membresia;


@WebServlet(name = "SvMembresia", urlPatterns = {"/SvMembresia"})
public class SvMembresia extends HttpServlet {

    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=ControlGimnasio;encrypt=true;trustServerCertificate=true";
    private final String user = "GymAppUser";
    private final String password = "password123";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SvMembresia</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SvMembresia at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Driver JDBC no encontrado", e);
        }

        List<Membresia> listaMembresias = new ArrayList<>();
        String sql = "SELECT id, nombre_persona, tipo_plan, costo_mensual, fecha_registro, fecha_fin, foto "
                   + "FROM Membresias ORDER BY fecha_fin";

        try (Connection con = DriverManager.getConnection(url, user, password); 
                Statement stmt = con.createStatement(); 
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaMembresias.add(new Membresia(
                        rs.getInt("id"),
                        rs.getString("nombre_persona"),
                        rs.getString("tipo_plan"),
                        rs.getInt("costo_mensual"),
                        rs.getDate("fecha_registro").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        rs.getString("foto")));
            }

            request.setAttribute("lista", listaMembresias);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al conectar con la base de datos", e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Driver JDBC no encontrado", e);
        }
        
        request.setCharacterEncoding("UTF-8");
        String nombrePersona = request.getParameter("nombrePersona");
        String tipoPlan = request.getParameter("tipoPlan");
        int costoMensual;
        LocalDate fechaRegistro;
        LocalDate fechaFin;
        String foto = request.getParameter("foto");
        
        try {
            costoMensual = Integer.parseInt(request.getParameter("costoMensual"));
            fechaRegistro = LocalDate.parse(request.getParameter("fechaRegistro"));
            fechaFin = LocalDate.parse(request.getParameter("fechaFin"));
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new ServletException("Costo o fechas con formato invalido", e);
        }
        
        if (fechaFin.isBefore(fechaRegistro)) {
            request.setAttribute("error", "La fecha de finalizacion no puede ser anterior a la de registro");
            doGet(request, response);
            return;
        }
        
        String sql = "INSERT INTO Membresias(nombre_persona, tipo_plan, costo_mensual, fecha_registro, fecha_fin, foto) "
                   + "VALUES(?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombrePersona);
            ps.setString(2, tipoPlan);
            ps.setInt(3, costoMensual);
            ps.setDate(4, java.sql.Date.valueOf(fechaRegistro));
            ps.setDate(5, java.sql.Date.valueOf(fechaFin));
            ps.setString(6, foto);
            ps.executeUpdate();

            response.sendRedirect("SvMembresia");

        } catch (SQLException e) {
            throw new ServletException("Error al guardar en la base de datos", e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
