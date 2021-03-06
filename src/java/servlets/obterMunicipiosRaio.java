/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import dao.GeometriaDAO;
import dao.ViewBoxDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mapa.Mapa;

/**
 *
 * @author Fernando
 */
public class obterMunicipiosRaio extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            GeometriaDAO geometriaDao = new GeometriaDAO();
            ViewBoxDAO viewBoxDAO = new ViewBoxDAO();
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            int raioKm = Integer.parseInt(request.getParameter("raio"));
            String nomeGeometria = "Cidades: em um raio de "+raioKm+" Km "+"do ponto: "+latitude+" "+longitude; 
            Mapa mapa = geometriaDao.getMapaPeloRaioDeUmPonto(latitude, longitude, raioKm, nomeGeometria);
            ArrayList<Mapa> mapas = geometriaDao.getMapasPeloRaioDeUmPonto(latitude, longitude, raioKm, nomeGeometria);
            String viewBox = viewBoxDAO.getTamanhoViewBox(mapa.getGeometria());
           
            request.setAttribute("geometria", mapa.getGeometriaSVG());
            request.setAttribute("nomeGeometria", nomeGeometria);
            request.setAttribute("viewBox", viewBox);
            request.setAttribute("mapas", mapas);
            request.setAttribute("desenharGeometria", true);
            request.setAttribute("raio", raioKm);
            request.setAttribute("latitude", latitude);
            request.setAttribute("longitude", longitude);
            request.getServletContext().getRequestDispatcher("/consultas.jsp").forward(request, response);
            
        } catch (Exception ex) {
            request.setAttribute("erro", true);
            request.getServletContext().getRequestDispatcher("/consultas.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
