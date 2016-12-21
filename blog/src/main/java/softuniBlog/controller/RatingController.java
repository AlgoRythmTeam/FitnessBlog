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

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

        Article article = this.articleRepository.findOne(id);

        model.addAttribute("article", article);

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);


            Rating currentRating = article.getRating();


            Set<UserRating> currentUserRatings = currentRating.getUserRatings();

            for (UserRating currentUserRating : currentUserRatings) {

                if (currentUserRating.getRatingAuthor() == entityUser) {

                    String value = currentUserRating.getRatingValue().toString();
                    String stars = "";
                    switch (value) {
                        case "0":
                            stars = "&#x2606;&#x2606;&#x2606;&#x2606;";
                            break;
                        case "1":
                            stars = "&#x2605;&#x2606;&#x2606;&#x2606;";
                            break;
                        case "2":
                            stars = "&#x2605;&#x2605;&#x2606;&#x2606;";
                            break;
                        case "3":
                            stars = "&#x2605;&#x2605;&#x2605;&#x2606;";
                            break;
                        case "4":
                            stars = "&#x2605;&#x2605;&#x2605;&#x2605;";
                            break;
                    }
                    model.addAttribute("errorMessage2",
                            "You have already rated this article with "
                                    + " " + stars + " ! If you submit again the old rating would be lost!");
                }
            }
        }

        if (errorMes != null) {
            model.addAttribute("errorMessage", "Please choose a rating before submission !");
        }

        model.addAttribute("view", "rating/rateArticle");


        return "base-layout";
    }


    @PostMapping("/article/{id}/rating")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(Model model, @PathVariable Integer id, UserRatingBindingModel ratingBindingModel) {


        Set<String> expected = new HashSet<String>(Arrays.asList("0", "1", "2", "3", "4"));

        // if (!expected.contains(ratingBindingModel.getRating().toString())) {
//
        //     return "redirect:/article/" + id+"ratingError";
        // }

        if (expected.contains(ratingBindingModel.getRating().toString())) {

            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            Article article = this.articleRepository.findOne(id);

            Integer ratingValue = Integer.parseInt(ratingBindingModel.getRating());

            Date time = new Date();

            Rating rating = new Rating(article);

            this.ratingRepository.saveAndFlush(rating);


            UserRating userRatingEntity = new UserRating(entityUser, time, ratingValue, rating);

            this.userRatingRepository.saveAndFlush(userRatingEntity);

        }
        return "redirect:/article/" + id;
    }


    @GetMapping("/article/{id}/ratingError")
    @PreAuthorize("isAuthenticated()")
    public String ratingError1(Model model, @PathVariable Integer id, String errorMes) {

        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }


        model.addAttribute("view", "rating/error");

        return "base-layout";
    }

}
