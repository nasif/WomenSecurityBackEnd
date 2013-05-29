/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity;

import com.tavant.mobile.womensecurity.entity.beans.UserdataFacadeLocal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.tavant.mobile.womensecurity.entity.Userdata;

/**
 *
 * @author nasif
 */
public class UserData extends HttpServlet {
    
    @EJB
    private UserdataFacadeLocal userdataFacade;
    private Userdata user=null;

    
    
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuffer buffer=new StringBuffer();
        BufferedReader reader=null;
        String line=null;
        JSONObject object=null;
        
        try {  
           reader=request.getReader();
           while((line=reader.readLine())!=null){
            buffer.append(line);
           }
           object=new JSONObject().fromObject(buffer.toString());
           user=new Userdata();
           user.setUserid(object.get("userid").toString());
           user.setIdtype(object.get("idtype").toString());
           user.setPhone(object.getString("phone").toString());
           user.setEmail(object.getString("email").toString());
           user.setEmail(object.getString("email").toString());

           
           
           //System.out.println("my json data"+buffer.toString());
            
        } finally {            
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
