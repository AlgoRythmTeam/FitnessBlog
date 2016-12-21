package softuniBlog.entity;


import javax.persistence.*;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ratings")
public class Rating {

    private Integer id;

    private Article article;

    private Set<UserRating> userRatings;

    public Rating() {
    }

    public Rating(Article article) {
        this.article = article;
        this.userRatings = new HashSet<>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "articleId")
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @OneToMany(mappedBy = "rating")
    public Set<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Set<UserRating> userRatings) {
        this.userRatings = userRatings;
    }
}
