/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity.facade;

import com.tavant.mobile.womensecurity.entity.Userdata;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tavant
 */
@Stateless
public class UserdataFacade extends AbstractFacade<Userdata> implements UserdataFacadeLocal {
    @PersistenceContext(unitName = "WomenSecurityBackEndPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserdataFacade() {
        super(Userdata.class);
    }

    @Override
    public Userdata findByUserId(String userId) {
         try{
        Query queryByName = em.createNamedQuery("Userdata.findByUserid");
        queryByName.setParameter("userid", userId);
        return (Userdata) queryByName.getResultList().get(0);
         }catch(Exception e){
             return null;
         }    
    }

    @Override
    public Userdata findByUserPhoneNumber(String phoneNumber) {
        try{
        Query queryByName = em.createNamedQuery("Userdata.findByPhone");
        queryByName.setParameter("phone", phoneNumber);
        return (Userdata) queryByName.getResultList().get(0);
         }catch(Exception e){
             return null;
         }    
    }

    

    

    
}
