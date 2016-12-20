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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import softuniBlog.bindingModel.UserRatingBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Rating;
import softuniBlog.entity.User;
import softuniBlog.entity.UserRating;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.RatingRepository;
import softuniBlog.repository.UserRatingRepository;
import softuniBlog.repository.UserRepository;

import java.util.Date;

@Controller
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRatingRepository userRatingRepository;


    @GetMapping("/article/{id}/rating")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model, @PathVariable Integer id, @RequestParam(required = false) String errorMes) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        Article article = this.articleRepository.findOne(id);

        if (errorMes != null) {
            model.addAttribute("errorMessage", "Please choose a rating before submision !");
        }


        //if (article.getRating().getUsersRating().containsKey(entityUser)){

        // }


        model.addAttribute("article", article);

        model.addAttribute("view", "rating/rateArticle");

        return "base-layout";
    }


    @PostMapping("/article/{id}/rating")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(Model model, @PathVariable Integer id, UserRatingBindingModel ratingBindingModel) {

        if (ratingBindingModel.getRating() == null) {

            this.create(model, id, "sendError");

            System.out.print("Activated");
        }

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User entityUser = this.userRepository.findByEmail(principal.getUsername());

        Article article = this.articleRepository.findOne(id);

        Integer ratingValue = Integer.parseInt(ratingBindingModel.getRating());

        Date time = new Date();

        Rating rating=new Rating(article);

        this.ratingRepository.saveAndFlush(rating);

        UserRating userRatingEntity = new UserRating(entityUser ,time, ratingValue, rating);

        this.userRatingRepository.saveAndFlush(userRatingEntity);




        return "redirect:/article/" + id;
    }


}
