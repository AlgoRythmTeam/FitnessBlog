package softuniBlog.entity;

import javax.persistence.*;

@Entity
@Table(name = "userInfo")
public class UserInfo {

    private Integer id ;

    private Integer mass;

    private Integer height;

    private Integer Age;

    private boolean Sex;

    private User user;


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

}
