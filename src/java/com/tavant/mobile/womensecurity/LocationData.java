/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity;

import com.tavant.mobile.womensecurity.entity.Locationdata;
import com.tavant.mobile.womensecurity.entity.Userdata;
import com.tavant.mobile.womensecurity.entity.facade.LocationdataFacadeLocal;
import com.tavant.mobile.womensecurity.entity.facade.UserdataFacadeLocal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 *
 * @author nasif
 */
public class LocationData extends HttpServlet {
    
    @EJB
    private LocationdataFacadeLocal locationdataFacade;
    @EJB
    private UserdataFacadeLocal userdataFacade;
    
    

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuffer buffer=new StringBuffer();
        BufferedReader reader=null;
        String line=null;
        JSONObject object=null;
        String outputString     =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>";
        outputString            +=  "<METHOD>user</METHOD>\n";
        String userid=null;
        String latitude=null;
        String longitude=null;
        try {
            reader=request.getReader();
           while((line=reader.readLine())!=null){
            buffer.append(line);
           }
           object=JSONObject.fromObject(buffer.toString());
           userid=object.getString("userid");
           latitude=object.getString("latitude");
           longitude=object.getString("latitude");
           if(userid==null||latitude==null||longitude==null){
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             outputString      +=  "\n<SS>FALSE</SS>";
             outputString      +=  "\n<MSG>Inavlid message</MSG></ROOT>";
           }else{
              Userdata userData= userdataFacade.findByUserId(userid);
              Integer id=userData.getId();
              Locationdata location=locationdataFacade.findById(id);
              if(location!=null){
                //update
              }else{
                 // insert
              }
              //fetch latest police numner here
           }
           
        }catch(JSONException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);   
            outputString      +=  "\n<SS>FALSE</SS>";
            outputString      +=  "\n<MSG>invalidContent</MSG></ROOT>";  
            e.printStackTrace();            
        }catch(Exception e){
            outputString      +=  "\n<SS>FALSE</SS>";
            outputString      +=  "\n<MSG>unknownerror</MSG></ROOT>";  
            e.printStackTrace(); 
        } finally {
            out.write(outputString);
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
