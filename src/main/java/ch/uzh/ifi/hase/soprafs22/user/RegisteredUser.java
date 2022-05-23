package ch.uzh.ifi.hase.soprafs22.user;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "REGISTEREDUSER")
public class RegisteredUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "logged_in", nullable = false)
    private boolean loggedIn;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(columnDefinition = "integer default 1000")
    private int rankedScore;

    @Column(columnDefinition = "integer default 0")
    private int wins;

    @Column(columnDefinition = "integer default 0")
    private int losses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getRankedScore() {
        return rankedScore;
    }

    public void setRankedScore(int rankedScore) {
        this.rankedScore = rankedScore;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
