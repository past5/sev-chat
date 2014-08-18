//Vsevolod Geraskin
//Assignment 3

package model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ChatMessage {
    private String usernamefrom;
    private String usernameto;
    private String chatmessage;
    private Timestamp messagetime;
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="chatmessage_id_seq")
    @SequenceGenerator(name="chatmessage_id_seq", sequenceName="chatmessage_id_seq", allocationSize=1)
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
    
    public String getUsernamefrom() {
        return usernamefrom;
    }
    public void setUsernamefrom(String username) {
        this.usernamefrom = username;
    }
    public String getUsernameto() {
        return usernameto;
    }
    public void setUsernameto(String username) {
        this.usernameto = username;
    }
    public String getChatmessage() {
        return chatmessage;
    }
    public void setChatmessage(String chatmessage) {
        this.chatmessage = chatmessage;
    }
    public Timestamp getMessagetime() {
        return messagetime;
    }
    public void setMessagetime(Timestamp messagetime) {
        this.messagetime = messagetime;
    }
    
    @Override
    public String toString() {
        return String.format("%s", chatmessage);        
    }
}
