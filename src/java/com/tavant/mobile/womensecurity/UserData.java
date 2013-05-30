/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity;

import com.tavant.mobile.womensecurity.entity.facade.UserdataFacadeLocal;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import net.sf.json.JSONException;

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
     * <code>PUT</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param iscreate boolean value for checking to create new user or edit existing user.
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response,boolean iscreate)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuffer buffer=new StringBuffer();
        BufferedReader reader=null;
        String line=null;
        JSONObject object=null;
        String outputString     =   "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT>";
        outputString            +=  "<METHOD>user</METHOD>\n";
        String userid=null;
        String idtype=null;
        String phone=null;
        String email=null;
        String gcmid=null;
        short apptype=-1;
        String osname=null;
        String authtoken=null;
        
        try {  
           reader=request.getReader();
           while((line=reader.readLine())!=null){
            buffer.append(line);
           }
           object=JSONObject.fromObject(buffer.toString());
           if(iscreate){  
           userid=object.getString("userid");    
           idtype=object.getString("idtype");
           phone=object.getString("phone");
           email=object.getString("email");
           gcmid=object.getString("gcmid").toString();
           apptype=Short.parseShort(object.getString("apptype"));
           osname=object.getString("osname");
           authtoken=object.getString("authtoken");
           if(userid==null||phone==null||apptype==-1||osname==null){
                  outputString      +=  "\n<SS>FALSE</SS>";
                  outputString      +=  "\n<MSG>mandatory field cannot be empty</MSG></ROOT>";
           } 
           else try {
               if(!isValidEmailAddress(email)){
                 outputString      +=  "\n<SS>FALSE</SS>";
                 outputString      +=  "\n<MSG>Invalid email address!</MSG></ROOT>";
          }else{    
          user=new Userdata();
          user.setUserid(userid);
          user.setIdtype(idtype);
          user.setPhone(phone);
          user.setEmail(email);
          user.setGcmid(gcmid);
          user.setApptype(apptype);
          user.setOsname(osname);
          user.setAuthtoken(authtoken);
          userdataFacade.create(user);
          outputString    +=  "<SS>TRUE</SS><MSG>Registration successful.</MSG></ROOT>";
          }} catch (AddressException ex) {
               Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
           } 
           }else{
              if(!object.containsKey("userid"))
                  throw new JSONException();
              userid= object.getString("userid"); 
              Userdata userData= userdataFacade.findByUserId(userid);
              if(object.containsKey("phone"))
                  userData.setPhone(object.getString("phone"));
              else if(object.containsKey("email"))
                   userData.setEmail(object.getString("email"));
              else if(object.containsKey("gcmid"))
                   userData.setGcmid(object.getString("gcmid"));
              else if(object.containsKey("authtoken"))
                   userData.setAuthtoken(object.getString("authtoken"));
              userdataFacade.edit(userData);
               outputString    +=  "<SS>TRUE</SS><MSG>User data updation successfull</MSG></ROOT>";
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
        processRequest(request, response,false);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       processRequest(req, resp,true);
    }
    
    private  boolean isValidEmailAddress(String aEmailAddress) throws AddressException, AddressException, AddressException{
    if (aEmailAddress == null) return false;
    boolean result = true;
    try {
          InternetAddress emailAddr = new InternetAddress(aEmailAddress);
          if ( ! hasNameAndDomain(aEmailAddress) ) {
            result = false;
        }
    }
    catch (AddressException ex){
      result = false;
    }
    return result;
  }
  
  private  boolean hasNameAndDomain(String aEmailAddress){
            String[] tokens = aEmailAddress.split("@");

             if(tokens.length == 2 &&!tokens[0].isEmpty()&& !tokens[1].isEmpty())
                 return true;
             else
                 return false;

  }   
   
}
