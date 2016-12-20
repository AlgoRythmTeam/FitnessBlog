package softuniBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.RatingRepository;
import softuniBlog.repository.UserRatingRepository;
import softuniBlog.repository.UserRepository;

@Controller
public class UserInfoController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;


    @GetMapping("/userInfo/dataInput")
    @PreAuthorize("isAuthenticated()")
    public String dataInput(Model model) {

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }


        model.addAttribute("view", "userInfo/dataInput");


        return "base-layout";
    }


}
