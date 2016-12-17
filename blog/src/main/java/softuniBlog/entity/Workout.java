package softuniBlog.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by admin on 17-Dec-16.
 */

@Entity
@Table(name = "workout")
public class Workout {

    private Integer id;

    private User worker;

    private Date trainingDay;

    private Integer chest;

    private Integer abs;

    private Integer back;

    private Integer shoulders;

    private Integer biceps;

    private Integer triceps;

    private Integer legs;


    public Workout(User worker, Date trainingDay, Integer chest, Integer legs, Integer back, Integer biceps, Integer triceps, Integer abs, Integer shoulders) {
        this.worker = worker;
        this.trainingDay = trainingDay;
        this.chest = chest;
        this.abs = abs;
        this.back = back;
        this.shoulders = shoulders;
        this.biceps = biceps;
        this.triceps = triceps;
        this.legs = legs;
    }

    public Workout() {   }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(nullable = false,name = "workerId")
    public User getWorker() {
        return worker;
    }


    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Date getTrainingDay() {
        return trainingDay;
    }

    public void setTrainingDay(Date trainingDay) {
        this.trainingDay = trainingDay;
    }

    public Integer getChest() {
        return chest;
    }

    public void setChest(Integer chest) {
        this.chest = chest;
    }

    public Integer getAbs() {
        return abs;
    }

    public void setAbs(Integer abs) {
        this.abs = abs;
    }

    public Integer getBack() {
        return back;
    }

    public void setBack(Integer back) {
        this.back = back;
    }

    public Integer getShoulders() {
        return shoulders;
    }

    public void setShoulders(Integer shoulders) {
        this.shoulders = shoulders;
    }

    public Integer getBiceps() {
        return biceps;
    }

    public void setBiceps(Integer biceps) {
        this.biceps = biceps;
    }

    public Integer getTriceps() {
        return triceps;
    }

    public void setTriceps(Integer triceps) {
        this.triceps = triceps;
    }

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }

}
