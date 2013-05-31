/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity.facade;

import com.tavant.mobile.womensecurity.entity.Locationdata;
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
    public Locationdata findById(int id) {
        
       try{
        Query queryByName = em.createNamedQuery("Locationdata.findByUserdataid");
        queryByName.setParameter("userdataid", id);
        return (Locationdata) queryByName.getResultList().get(0);
         }catch(Exception e){
             return null;
         }      
        
        
    }
    
}
