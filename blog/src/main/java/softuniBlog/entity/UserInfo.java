package softuniBlog.entity;

import javax.persistence.*;
import javax.print.attribute.IntegerSyntax;

@Entity
@Table(name = "userInfo")
public class UserInfo {

    private Integer id;

    private Integer mass;

    private Integer height;

    private Integer Age;

    private boolean Sex;

    private User user;

    public UserInfo() {
    }

    public UserInfo(Integer mass, Integer height, Integer age, boolean sex, User user) {
        this.mass = mass;
        this.height = height;
        this.Age = age;
        this.Sex = sex;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getMass() {
        return mass;
    }

    public void setMass(Integer mass) {
        this.mass = mass;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        this.Age = age;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean sex) {
        this.Sex = sex;
    }

    @OneToOne
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User infoHolder) {
        this.user = infoHolder;
    }


    @Transient
    public String getRecommendation() {

        Integer coefficient = this.height - this.mass;


        if (this.Age < 10) {

            return "You are too young for fitness, go to play outside!";
        } else if (this.Age > 60) {
            return "You are too old for fitness, go walk in the park!";
        } else {
            if (this.Sex == true) {
                if (coefficient < 100) {
                    return "You must do swimming and jogging.";
                } else {
                    return "You must train bodybuilding.";
                }
            } else {
                if (coefficient < 110) {
                    return "You must do Zumba and Yoga.";
                } else {
                    return "You must praktice street fitness.";
                }
            }
        }

    }

    @Transient
    public Long getDailyCallories() {

        if (this.Sex) {
            return Math.round(66.47 + (13.75 * this.mass) + (5 * this.height) - (6.75 * this.Age));
        } else {
            return Math.round(665.09 + (9.56 * this.mass) + (1.84 * this.height) - (4.67 * this.Age));
        }
    }


}





