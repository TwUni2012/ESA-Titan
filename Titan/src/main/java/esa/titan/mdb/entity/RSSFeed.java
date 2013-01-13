/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.mdb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author strongerthanbefore
 */
@Entity
public class RSSFeed implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  private String feed;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public String getFeed(){
    return this.feed;
  }
  
  public void setFeed(String feed){
    this.feed = feed;
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
    if (!(object instanceof RSSFeed)) {
      return false;
    }
    RSSFeed other = (RSSFeed) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "esa.titan.mdb.entity.RSSFeed[ id=" + id + " ]";
  }
  
}
