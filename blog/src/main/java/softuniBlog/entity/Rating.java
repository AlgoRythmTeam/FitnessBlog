package softuniBlog.entity;


import javax.persistence.*;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Set;

@Entity
@Table(name="ratings")
public class Rating {

    private Integer id;

    private Article article;

    private HashMap<User, Integer> usersRating;

    private double value;


    public Rating(){ }

    public Rating(User user, Integer ratingValue) {

        this.usersRating= new HashMap<>();
        this.usersRating.put(user,ratingValue);
        double value=0;

        /**for ( HashMap.Entry<User, Integer> itempair : usersRating.entrySet() ) {

            Double userValue = Double.parseDouble(itempair.getValue().toString());
            value=value+userValue;
        }
        value=value/usersRating.size();**/
        this.value=value;
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

    public HashMap<User, Integer> getUsersRating() {
        return usersRating;
    }

    public void setUsersRating(HashMap<User, Integer> usersRating) {
        this.usersRating = usersRating;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
