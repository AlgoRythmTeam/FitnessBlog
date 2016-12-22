package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.entity.Rating;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;
import softuniBlog.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {

        List<Category> categories=this.categoryRepository.findAll();

        model.addAttribute("view", "home/index");
        model.addAttribute("categories", categories);
        return "base-layout";
    }

    @RequestMapping("/error/403")
    public String accessDenied(Model model){
        model.addAttribute("view","error/403");

        return "base-layout";
    }

    @RequestMapping("/error/internal2")
    public String passwordMIssmatch(Model model){
        model.addAttribute("view","error/internal2");
        return "base-layout";
    }


    @RequestMapping("/error/internal/{id}")
    public String sameUserEmail(Model model, @PathVariable Integer id){
        if (!this.userRepository.exists(id)){
            return "redirect:/";
        }

        User user=this.userRepository.findById(id);

        model.addAttribute("user",user);
        model.addAttribute("view","error/internal1");

        return "base-layout";
    }



    @GetMapping("/category/{id}")
    public String listArticles(Model model, @PathVariable Integer id){

        if(!this.categoryRepository.exists(id)){
            return "redirect:/";
        }

        Category category=this.categoryRepository.findOne(id);

        Set<Article> articles=category.getArticles();

        Set<Rating> ratings=new HashSet<>();

        for (Article article:articles) {
            ratings.add(article.getRating()) ;
        }


        model.addAttribute("view","home/listOfArticles");
        model.addAttribute("articles",articles);
        model.addAttribute("category",category);
        model.addAttribute("ratings",ratings);
        return "base-layout";
    }

}
