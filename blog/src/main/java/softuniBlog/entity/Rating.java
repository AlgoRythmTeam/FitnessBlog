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

    @Transient
    public Double getArticleRating(){

        if (this.userRatings.size()==0){
            return -1.0;
        } else {

            Double estimatedValue=0.0;
            for (UserRating userRating: this.userRatings) {
               estimatedValue+= userRating.getRatingValue();
            }
            estimatedValue=estimatedValue/this.userRatings.size();
            return Math.round(estimatedValue*100)/100.0 ;
        }
    }

    @Transient
    public String getArticleRatingStars(){

        if (this.userRatings.size()>0){
            Double estimatedValue=0.0;
            for (UserRating userRating: this.userRatings) {
                estimatedValue+= userRating.getRatingValue();
            }
            estimatedValue=estimatedValue/this.userRatings.size();

            if (estimatedValue < 0.5) {
                return "&#x2606;&#x2606;&#x2606;&#x2606;";
            } else if (estimatedValue < 1.5) {
                return "&#x2605;&#x2606;&#x2606;&#x2606;";
            } else if (estimatedValue < 2.5) {
                return "&#x2605;&#x2605;&#x2606;&#x2606;";
            } else if (estimatedValue < 3.5) {
                return "&#x2605;&#x2605;&#x2605;&#x2606;";
            } else {
                return "&#x2605;&#x2605;&#x2605;&#x2605;";
            }

        } else {
                return "No rating yet.";
        }

    }

    @Transient
    public Integer getRatingSize(){

            return this.userRatings.size();

    }


}
