/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity.facade;

import com.tavant.mobile.womensecurity.entity.Locationdata;
import com.tavant.mobile.womensecurity.entity.Userdata;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tavant
 */
@Local
public interface LocationdataFacadeLocal {

    void create(Locationdata locationdata);

    void edit(Locationdata locationdata);

    void remove(Locationdata locationdata);

    Locationdata find(Object id);

    List<Locationdata> findAll();

    List<Locationdata> findRange(int[] range);

    int count();
    
    Locationdata findByuserId(Userdata uid);
    
    Locationdata findByLocationLike(String userId);
    
    Locationdata findByLocation(String location);
    
    List<Locationdata> findByUserJoin(String location, short appType);
    
    List<Locationdata> findByUserJoinLike(String location, short appType);
    
}
