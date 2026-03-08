package harjoitustyo.sportdiary.domain;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Category {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryid;

    private String name;

@JsonIgnoreProperties("category")

    @OneToMany(
        mappedBy = "category",
        cascade = CascadeType.ALL
        
    )
    private List<Sport> books;

  
    public Category() {}

    public Category(String name) {
        this.name = name;
    }


    public Long getCategoryid() {
        return categoryid;
    }

   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}