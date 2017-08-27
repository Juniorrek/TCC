package br.com.tcc.Servlets;

import br.com.tcc.Facade.Facade;
import beans.Texto;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.rosuda.REngine.REXPMismatchException;

@WebServlet(name = "PesquisarServlet", urlPatterns = {"/PesquisarServlet"})
public class PesquisarServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, REXPMismatchException {
        String action = request.getParameter("action");
        if("analise".equals(action)){
            Facade f = new Facade();
            String[] arquivos = request.getParameterValues("arqs");
            List<Texto> lista;
            if(arquivos.length > 1){
                lista = f.manyArtigo(arquivos);
            }
            else{
                lista = f.soloArtigo(arquivos[0]);
            }
            request.setAttribute("Lista", lista);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/resultado.jsp");
            rd.forward(request, response);
        }
        File folder = new File("C:/Users/Orestes/Desktop/TCC");
        File[] listOfFiles = folder.listFiles();
        List<String> arqs = new ArrayList();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".pdf")) {
                 arqs.add(listOfFiles[i].getName());
            } 
        }
        request.setAttribute("Lista", arqs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pesquisa.jsp");
        rd.forward(request, response);
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
            processRequest(request, response);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(PesquisarServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            processRequest(request, response);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(PesquisarServlet.class.getName()).log(Level.SEVERE, null, ex);
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
