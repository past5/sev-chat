//Vsevolod Geraskin
//Final Project

package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ChatUser {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="chatuser_id_seq")
    @SequenceGenerator(name="chatuser_id_seq", sequenceName="chatuser_id_seq", allocationSize=1)
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
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstname;
    }
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    public String getLastName() {
        return lastname;
    }
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }
    
    @Override
    public String toString() {
        return String.format("%s", username);        
    }
}
