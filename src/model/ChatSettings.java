//Vsevolod Geraskin
//Final Project

package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ChatSettings {
    private String password;
    private String serverip;
    private String servername;
    private String servercookie;
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="chatsettings_id_seq")
    @SequenceGenerator(name="chatsettings_id_seq", sequenceName="chatsettings_id_seq", allocationSize=1)
    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }


    @Override
    public boolean equals(Object obj) {
        try { return id.equals(getId()); }
        catch (Exception exc) { return false; }
    }
    @Override
    public int hashCode() {
        return 31 + ((id == null) ? 0 : id.hashCode());
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getServerip() {
        return serverip;
    }
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }
    public String getServername() {
        return servername;
    }
    public void setServername(String servername) {
        this.servername = servername;
    }
    public String getServercookie() {
        return servercookie;
    }
    public void setServercookie(String servercookie) {
        this.servercookie = servercookie;
    }
    
    @Override
    public String toString() {
        return String.format("%s", servername);        
    }
}