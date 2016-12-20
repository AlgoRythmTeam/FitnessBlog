package softuniBlog.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="userRatings")
public class UserRating {

    private Integer id;

    private User ratingAuthor;

    private Date ratingDate;

    private Integer ratingValue;

    private Rating rating;

    public UserRating( ) {

    }

    public UserRating( User ratingAuthor, Date ratingDate,Integer ratingValue , Rating rating) {
        this.ratingAuthor = ratingAuthor;
        this.ratingDate = ratingDate;
        this.ratingValue=ratingValue;
        this.rating = rating;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(nullable = false,name = "ratingAuthorId")
    public User getRatingAuthor() {
        return ratingAuthor;
    }

    public void setRatingAuthor(User ratingAuthor) {
        this.ratingAuthor = ratingAuthor;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    @ManyToOne
    @JoinColumn(nullable = false,name = "ratingId")
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }
}
