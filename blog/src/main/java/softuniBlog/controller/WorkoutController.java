package softuniBlog.controller;

import org.hibernate.annotations.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import softuniBlog.bindingModel.WorkoutBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.User;
import softuniBlog.entity.Workout;
import softuniBlog.repository.UserRepository;
import softuniBlog.repository.WorkoutRepository;

import java.sql.Array;
import java.util.*;

/**
 * Created by admin on 17-Dec-16.
 */

@Controller
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/workout/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        User user = this.userRepository.findByEmail(principal.getUsername());

        Set<Workout> workouts = user.getWorkouts();

        model.addAttribute("workouts", workouts);
        model.addAttribute("user", user);
        model.addAttribute("view", "workout/create");

        return "base-layout";
    }

    @PostMapping("/workout/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(WorkoutBindingModel workoutBindingModel) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(principal.getUsername());

        Date time = new Date();

        Set<Workout> workouts = userEntity.getWorkouts();

        boolean secondTraining=false;

        for (Workout workout:workouts) {

            if (workout.getTrainingDay().getDay()==time.getDay()){
                workout.setChest(workout.getChest()+workoutBindingModel.getChest());
                workout.setAbs(workout.getAbs()+workoutBindingModel.getAbs());
                workout.setBack(workout.getBack()+workoutBindingModel.getBack());
                workout.setShoulders(workout.getShoulders()+workoutBindingModel.getShoulders());
                workout.setBiceps(workout.getBiceps()+workoutBindingModel.getBiceps());
                workout.setTriceps(workout.getTriceps()+workoutBindingModel.getTriceps());
                workout.setLegs(workout.getLegs()+workoutBindingModel.getLegs());
                workout.setTrainingDay(time);

                this.workoutRepository.saveAndFlush(workout);

                secondTraining=true;
            }
        }

        if (!secondTraining){

            Workout workoutEntity= new Workout(
                    userEntity,
                    time,
                    workoutBindingModel.getChest(),
                    workoutBindingModel.getAbs(),
                    workoutBindingModel.getBack(),
                    workoutBindingModel.getShoulders(),
                    workoutBindingModel.getBiceps(),
                    workoutBindingModel.getTriceps(),
                    workoutBindingModel.getLegs()
            );

            this.workoutRepository.saveAndFlush(workoutEntity);
            
        }

        return "redirect:/workout/data";
    }

    @GetMapping("/workout/data")
    @PreAuthorize("isAuthenticated()")
    public String details(Model model) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(principal.getUsername());

        model.addAttribute("user", userEntity);

        Set<Workout> workouts = userEntity.getWorkouts();

        List<Workout> workoutsList =new ArrayList<>();

        for (Workout workout:workouts) {

            workoutsList.add(workout);
        }

        workoutsList.sort((w1,w2)-> w2.getTrainingDay().getDate() - w1.getTrainingDay().getDate());

        model.addAttribute("workouts", workoutsList);

        model.addAttribute("view", "workout/workoutList");

        return "base-layout";
    }

}
