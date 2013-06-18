/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity.facade;

import com.tavant.mobile.womensecurity.entity.Userdata;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tavant
 */
@Local
public interface UserdataFacadeLocal {

    void create(Userdata userdata);

    void edit(Userdata userdata);

    void remove(Userdata userdata);

    Userdata find(Object id);

    List<Userdata> findAll();

    List<Userdata> findRange(int[] range);

    int count();
    
    Userdata findByUserId(String userId);
    
    Userdata findByUserPhoneNumber(String phoneNumber);

   
    
}
