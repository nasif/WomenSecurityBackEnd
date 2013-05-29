/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity.beans;

import com.tavant.mobile.womensecurity.entity.Userdata;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nasif
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
    
}
