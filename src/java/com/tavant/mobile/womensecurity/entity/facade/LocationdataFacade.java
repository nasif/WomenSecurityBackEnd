/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity.facade;

import com.tavant.mobile.womensecurity.entity.Locationdata;
import com.tavant.mobile.womensecurity.entity.Userdata;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tavant
 */
@Stateless
public class LocationdataFacade extends AbstractFacade<Locationdata> implements LocationdataFacadeLocal {
    @PersistenceContext(unitName = "WomenSecurityBackEndPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocationdataFacade() {
        super(Locationdata.class);
    }

    @Override
    public Locationdata findByuserId(Userdata user) { 
       try{
        Query queryByName = em.createNamedQuery("Locationdata.findByUserdataid");
        queryByName.setParameter("userdataid", user);
        return (Locationdata) queryByName.getResultList().get(0);
         }catch(Exception e){
             e.printStackTrace();
             return null;
         }      
    }
    
    @Override
    public Locationdata findByLocation(String location) { 
       try{
        Query queryByName = em.createNamedQuery("Locationdata.findByLocationJoin");
        queryByName.setParameter("location", location);
        return (Locationdata) queryByName.getResultList().get(0);
         }catch(Exception e){
             return null;
         }      
    }

    @Override
    public Locationdata findByLocationLike(String userId) {
       try{
        Query queryByName = em.createNamedQuery("Locationdata.findByLocationJoinLike");
        queryByName.setParameter("location", "%"+userId);
        return (Locationdata) queryByName.getResultList().get(0);
         }catch(Exception e){
             return null;
         }      
    }    
}
