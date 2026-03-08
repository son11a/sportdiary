package harjoitustyo.sportdiary.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Sport {


    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
 private String date;
private int time;
private int distance;

@JsonIgnoreProperties("sports")
 @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

 @JsonIgnoreProperties("sports")
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

public Sport() {
    }

    public Sport(String date, int time, int distance, Category category, User user) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.category = category;
        this.user = user;
    }

    public Sport(Long id, String date, int time, int distance, Category category, User user) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.category = category;
        this.user = user;
    }

  

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }

    public int getDistance() {
        return distance;
    }

    public Category getCategory() {
        return category;
    }

    public User getUser() {
        return user;
    }

   

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
