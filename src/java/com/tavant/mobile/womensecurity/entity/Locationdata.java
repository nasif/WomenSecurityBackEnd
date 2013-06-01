/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nasif
 */
@Entity
@Table(name = "locationdata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Locationdata.findAll", query = "SELECT l FROM Locationdata l"),
    @NamedQuery(name = "Locationdata.findById", query = "SELECT l FROM Locationdata l WHERE l.id = :id"),
    @NamedQuery(name = "Locationdata.findByUserdataid", query = "SELECT l FROM Locationdata l WHERE l.userdataid = :userdataid"),
    @NamedQuery(name = "Locationdata.findByLatitude", query = "SELECT l FROM Locationdata l WHERE l.latitude = :latitude"),
    @NamedQuery(name = "Locationdata.findByLongitude", query = "SELECT l FROM Locationdata l WHERE l.longitude = :longitude"),
    @NamedQuery(name = "Locationdata.findByLocation", query = "SELECT l FROM Locationdata l WHERE l.location = :location"),
    @NamedQuery(name = "Locationdata.findByLocationJoin", query = "SELECT l FROM Locationdata l INNER JOIN  Userdata U ON l.userdataid=U.id WHERE l.location = :location"),
    @NamedQuery(name = "Locationdata.findByLocationJoinLike", query = "SELECT l FROM Locationdata l INNER JOIN  Userdata U ON l.userdataid=U.id WHERE l.location LIKE :location"), 
})
public class Locationdata implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "userdataid")
    private int userdataid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "latitude")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "longitude")
    private String longitude;
    @Size(max = 250)
    @Column(name = "location")
    private String location;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "locationdata")
    private Userdata userdata;

    public Locationdata() {
    }

    public Locationdata(Integer id) {
        this.id = id;
    }

    public Locationdata(Integer id, int userdataid, String latitude, String longitude) {
        this.id = id;
        this.userdataid = userdataid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserdataid() {
        return userdataid;
    }

    public void setUserdataid(int userdataid) {
        this.userdataid = userdataid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Userdata getUserdata() {
        return userdata;
    }

    public void setUserdata(Userdata userdata) {
        this.userdata = userdata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locationdata)) {
            return false;
        }
        Locationdata other = (Locationdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tavant.mobile.womensecurity.entity.Locationdata[ id=" + id + " ]";
    }
    
}
