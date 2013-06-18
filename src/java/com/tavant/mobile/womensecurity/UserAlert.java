/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
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
import com.tavant.mobile.womensecurity.entity.AlertMail;
import com.tavant.mobile.womensecurity.entity.Locationdata;
import com.tavant.mobile.womensecurity.entity.facade.LocationdataFacadeLocal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.sf.json.JSONArray;

/**
 *
 * @author nasif
 */
public class UserAlert extends HttpServlet {
    
    @EJB
    private LocationdataFacadeLocal locationdataFacade;


     @EJB
    private UserdataFacadeLocal userdataFacade;
     
     
    private AtomicInteger integer=null; 

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        integer=new AtomicInteger(1);
    }
     
    
     
     
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
        String userId=null;
        String userphone=null;
        String useremail=null;
        Userdata currentuser=null;
        Locationdata lcation=null;
        AlertMail mail=null;
        String outputString     =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>";
        outputString            +=  "<METHOD>UserAlert</METHOD>\n";
        try {
        reader=request.getReader();
           while((line=reader.readLine())!=null){
            buffer.append(line);
           }
        object=JSONObject.fromObject(buffer.toString()); 
        userId=object.getString("userid");
        currentuser=userdataFacade.findByUserId(userId);
        lcation=locationdataFacade.findByuserId(currentuser);
        userphone=currentuser.getPhone();
        useremail=currentuser.getEmail();
        JSONArray array=(JSONArray) object.get("phonenumber");
        

        list=new ArrayList<String>();
        for(int i=0;i<array.size();i++){
          Userdata user=  userdataFacade.findByUserPhoneNumber(array.getString(i));
          if(user!=null&&user.getGcmid()!=null){
              list.add(user.getGcmid()); 
              mail=new AlertMail(user.getEmail(),useremail);
          }      
        }
        if(list.size()>0){
             Message.Builder builder=new Message.Builder();
             builder.collapseKey("collapse_key"+integer.incrementAndGet());
             builder.timeToLive(259200);  // 3days msg will be in server
             builder.delayWhileIdle(false);
             builder.addData("msg", "I am in danger please help me");
             builder.addData("tel", userphone);
             builder.addData("lat", lcation.getLatitude());
             builder.addData("long", lcation.getLongitude());
             Message msg=builder.build();
             Sender sender=new Sender(GCM_API_KEY);
             MulticastResult mresult=sender.sendNoRetry(msg, list);
             List<Result>results=mresult.getResults();
             for(Result result:results){
               System.out.println("message id"+result.getMessageId());
             }
             System.out.println("succes is"+mresult.getSuccess()+"Total is"+mresult.getTotal());
          } 
        outputString      +=  "\n<SS>TRUE</SS>";
        outputString      +=  "\n<MSG>Done</MSG></ROOT>"; 
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
