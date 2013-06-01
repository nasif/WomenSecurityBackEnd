/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tavant.mobile.womensecurity.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "userdata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userdata.findAll", query = "SELECT u FROM Userdata u"),
    @NamedQuery(name = "Userdata.findById", query = "SELECT u FROM Userdata u WHERE u.id = :id"),
    @NamedQuery(name = "Userdata.findByUserid", query = "SELECT u FROM Userdata u WHERE u.userid = :userid"),
    @NamedQuery(name = "Userdata.findByPhone", query = "SELECT u FROM Userdata u WHERE u.phone = :phone"),
    @NamedQuery(name = "Userdata.findByEmail", query = "SELECT u FROM Userdata u WHERE u.email = :email"),
    @NamedQuery(name = "Userdata.findByGcmid", query = "SELECT u FROM Userdata u WHERE u.gcmid = :gcmid"),
    @NamedQuery(name = "Userdata.findByApptype", query = "SELECT u FROM Userdata u WHERE u.apptype = :apptype"),
    @NamedQuery(name = "Userdata.findByAuthtoken", query = "SELECT u FROM Userdata u WHERE u.authtoken = :authtoken")})
public class Userdata implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "userid")
    private String userid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "idtype")
    private String idtype;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "phone")
    private String phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 60)
    @Column(name = "email")
    private String email;
    @Size(max = 256)
    @Column(name = "gcmid")
    private String gcmid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "apptype")
    private short apptype;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "osname")
    private String osname;
    @Size(max = 512)
    @Column(name = "authtoken")
    private String authtoken;
    @JoinColumn(name = "id", referencedColumnName = "userdataid", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Locationdata locationdata;

    public Userdata() {
    }

    public Userdata(Integer id) {
        this.id = id;
    }

    public Userdata(Integer id, String userid, String idtype, String phone, short apptype, String osname) {
        this.id = id;
        this.userid = userid;
        this.idtype = idtype;
        this.phone = phone;
        this.apptype = apptype;
        this.osname = osname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGcmid() {
        return gcmid;
    }

    public void setGcmid(String gcmid) {
        this.gcmid = gcmid;
    }

    public short getApptype() {
        return apptype;
    }

    public void setApptype(short apptype) {
        this.apptype = apptype;
    }

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public Locationdata getLocationdata() {
        return locationdata;
    }

    public void setLocationdata(Locationdata locationdata) {
        this.locationdata = locationdata;
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
        if (!(object instanceof Userdata)) {
            return false;
        }
        Userdata other = (Userdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tavant.mobile.womensecurity.entity.Userdata[ id=" + id + " ]";
    }
    
}
