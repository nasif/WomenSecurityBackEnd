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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;

/**
 *
 * @author nasif
 */
public class LocationData extends HttpServlet {
    
    @EJB
    private LocationdataFacadeLocal locationdataFacade;
    @EJB
    private UserdataFacadeLocal userdataFacade;
    
    private static final String REVRESE_LOCATION_API
            ="http://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&sensor=true";
    
    String currentlocation=null;
    
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
        outputString            +=  "<METHOD>updatelocation</METHOD>\n";
        String userid=null;
        String latitude=null;
        String longitude=null;
        short apptype=-1;
       
        try {
            reader=request.getReader();
           while((line=reader.readLine())!=null){
            buffer.append(line);
           }
           object=JSONObject.fromObject(buffer.toString());
           userid=object.getString("userid");
           latitude=object.getString("latitude");
           System.out.println("get the latest latitude"+latitude);
           longitude=object.getString("longitude");
           apptype=Short.parseShort(object.getString("apptype")); 
           if(userid==null||latitude==null||longitude==null){
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             outputString      +=  "\n<SS>FALSE</SS>";
             outputString      +=  "\n<MSG>Inavlid message</MSG></ROOT>";
           }else{
              currentlocation=getgeographiceLocation(latitude,longitude); 
              System.out.println("current user location is"+currentlocation);
              Userdata userData= userdataFacade.findByUserId(userid);
              Locationdata location=locationdataFacade.findByuserId(userData);
              
              
              if(location!=null){
                 location.setLatitude(latitude);
                 location.setLongitude(longitude);
                 location.setLocation(currentlocation);
                 locationdataFacade.edit(location);
              }else{
                  Locationdata newloc=new Locationdata();
                  newloc.setLatitude(latitude);
                  newloc.setLongitude(longitude);
                  newloc.setLocation(currentlocation);
                  newloc.setUserdataid(userData);
                  locationdataFacade.create(newloc);
              }
              String phonenumber="";
              if(apptype==0){
                  //currentlocation="Koramangala, Bangalore, Karnataka 560095, India";
                  phonenumber=getNearestCopnumber(currentlocation);  //Nearest cop have to give only for apptype=0,
              }
              outputString      +=  "\n<SS>TRUE</SS>";
              outputString      +=  "\n<MSG>LocationupdatedSuccessfully</MSG><PHONENO>"+phonenumber+"</PHONENO></ROOT>"; 
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

    private String getgeographiceLocation(String latitude, String longitude) {
        try {
            String url=String.format(REVRESE_LOCATION_API,latitude,longitude); 
            HttpGet request = new HttpGet(url);
            HttpParams params = new SyncBasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "UTF-8");
            HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
            DefaultHttpClient client=new DefaultHttpClient(params);
            HttpResponse res=client.execute(request);
            JSONObject object=JSONObject.fromObject(EntityUtils.toString(res.getEntity()));
            JSONArray jsonarray=  object.getJSONArray("results");
            JSONObject firstobject=(JSONObject) jsonarray.get(0);
            return firstobject.get("formatted_address").toString();
        } catch (Exception ex) {
            Logger.getLogger(LocationData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }

    private String getNearestCopnumber(String currentlocation) {  
        //Userdata data=null;
        //data.
        System.out.println("calling nearest cop");
        List<Locationdata> list=locationdataFacade.findByUserJoin(currentlocation, (short)1);
        System.out.println("calling nearest cop"+list.toString());
        if(list!=null&&list.size()>0){
          return list.get(0).getUserdataid().getPhone();
         }
        else{
         return getApproximateCopNumber(0);
        }        
    }
    
    private String getApproximateCopNumber(int index){
        //return "harcoded";    
      String array[]=currentlocation.split(",");
      String temp="";
      StringBuffer pattern=null;
      for(int i=index+1;i<array.length;i++)
          temp=temp+array[i]+",";
      System.out.println("temp is"+temp);
      if(temp.endsWith(",")){
      char c = ' ';   
       //new StringBuffer(temp).se
       pattern=new StringBuffer(temp);
       pattern.setCharAt(temp.lastIndexOf(","),c);
      }
      List<Locationdata> list=locationdataFacade.findByUserJoinLike(pattern.toString().trim(),(short)1);
       if(list!=null&&list.size()>0)
         return list.get(0).getUserdataid().getPhone();
        else{
         index=index+1;  
         if(index==2)
             return "";
         return getApproximateCopNumber(index++);
        } 
        
    }
    
}
