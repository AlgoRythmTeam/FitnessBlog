package softuniBlog.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniBlog.bindingModel.UserEditBindingModel;
import softuniBlog.bindingModel.UserRatingBindingModel;
import softuniBlog.entity.*;
import softuniBlog.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String listUsers(Model model) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();




        List<User> users = this.userRepository.findAll();

        model.addAttribute("users", users);
        model.addAttribute("view", "admin/user/list");

        return "base-layout";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }

        User user = this.userRepository.findOne(id);
        List<Role> roles = this.roleRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("view", "admin/user/edit");

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, UserEditBindingModel userEditBindingModel) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }
        User user = this.userRepository.findOne(id);

        if (!StringUtils.isEmpty(userEditBindingModel.getPassword())
                && !StringUtils.isEmpty(userEditBindingModel.getConfirmPassword())) {
            if (userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

                user.setPassword(bCryptPasswordEncoder.encode(userEditBindingModel.getPassword()));
            }
        }

        user.setEmail(userEditBindingModel.getEmail());
        user.setFullName(userEditBindingModel.getFullName());

        Set<Role> roles = new HashSet<>();

        for (Integer roleId:userEditBindingModel.getRoles()){
            roles.add(this.roleRepository.findOne(roleId));
        }
        user.setRoles(roles);
        this.userRepository.saveAndFlush(user);

        return "redirect:/admin/users/";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }
        User user = this.userRepository.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("view", "admin/user/delete");
        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id){
        if (!this.userRepository.exists(id)) {
            return "redirect:/admin/users/";
        }
        User user = this.userRepository.findOne(id);


        Set<Workout> workouts=user.getWorkouts();

        if (workouts!=null){
            for (Workout workout:workouts){
                this.workoutRepository.delete(workout);
            }
        }


        UserInfo userInfo=user.getUserData();


        if (userInfo!=null){
            this.userInfoRepository.delete(userInfo);
        }

        for (UserRating userRating: user.getUserRatings()){

            Rating currentRating=userRating.getRating();
            boolean checkIfRatingEmpty=false;

            if (currentRating.getRatingSize()==1){
                checkIfRatingEmpty=true;
            }
            this.userRatingRepository.delete(userRating);

            if (checkIfRatingEmpty){
                this.ratingRepository.delete(currentRating);
            }
        }


        for (Comment comment: user.getComments()){
            this.commentRepository.delete(comment);
        }

        for(Article article:user.getArticles()){
            for (Comment comment: article.getComments()){
                this.commentRepository.delete(comment);
            }

            for (UserRating userRating: article.getRating().getUserRatings()){
                this.userRatingRepository.delete(userRating);
            }

            Rating articleRating=article.getRating();

            this.ratingRepository.delete(articleRating);

            this.articleRepository.delete(article);
        }

        this.userRepository.delete(user);

        return "redirect:/admin/users/";
    }



}
