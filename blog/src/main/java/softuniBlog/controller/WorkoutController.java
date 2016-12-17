package softuniBlog.controller;

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

import java.util.Date;
import java.util.Set;

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

        Workout workoutEntity= new Workout(
                userEntity,
                time,
                workoutBindingModel.getChest(),
                workoutBindingModel.getLegs(),
                workoutBindingModel.getBack(),
                workoutBindingModel.getBiceps(),
                4,
                5,
                7


        );

        this.workoutRepository.saveAndFlush(workoutEntity);

        return "redirect:/profile";

    }








}
