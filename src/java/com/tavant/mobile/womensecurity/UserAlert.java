/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.tavant.mobile.womensecurity.entity.Userdata;
import com.tavant.mobile.womensecurity.entity.facade.UserdataFacadeLocal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import com.google.android.gcm.server.Sender;

/**
 *
 * @author nasif
 */
public class UserAlert extends HttpServlet {

    
     @EJB
    private UserdataFacadeLocal userdataFacade;
    private Userdata user=null;
     
    private static final String GCM_API_KEY="AIzaSyCqiQHsxuKG8zsW0Gv8JuCRmR8CATzZPhA";
    
    
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
        ArrayList<String>list=null;
        
        String outputString     =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>";
        outputString            +=  "<METHOD>user</METHOD>\n";
        try {
        reader=request.getReader();
           while((line=reader.readLine())!=null){
            buffer.append(line);
           }
        object=JSONObject.fromObject(buffer.toString()); 
        String array[]=(String[]) object.get("phonenumber");
        Message message = new Message.Builder()
                 .collapseKey("collapse_key")
                 .timeToLive(3)
                 .delayWhileIdle(true)
                 .addData("msg1", "value1")
                 .addData("msg2", "value2")
                 .build();
        list=new ArrayList<String>();
        for(int i=0;i<array.length;i++){
          Userdata user=  userdataFacade.findByPhoneNumber(array[0]);
          String gcmID=user.getGcmid();
          if(gcmID!=null)
              list.add(gcmID);
          if(list.size()>0){
             Sender sender=new Sender(GCM_API_KEY);
             sender.sendNoRetry(message, list);
          }    
        }
        outputString      +=  "\n<SS>FALSE</SS>";
        outputString      +=  "\n<MSG>Invalid email address!</MSG></ROOT>"; 
        }catch(JSONException e){
         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);   
         outputString      +=  "\n<SS>FALSE</SS>";
         outputString      +=  "\n<MSG>invalidContent</MSG></ROOT>";  
         e.printStackTrace();
        }catch(Exception e){  
         outputString      +=  "\n<SS>FALSE</SS>";
         outputString      +=  "\n<MSG>unknownerror</MSG></ROOT>";  
         e.printStackTrace();
        } finally{
             out.print(outputString);
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
