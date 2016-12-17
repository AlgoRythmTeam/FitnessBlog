package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuniBlog.bindingModel.CommentBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Comment;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CommentRepository;
import softuniBlog.repository.UserRepository;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/comment/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createComment(Model model,@PathVariable Integer id) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }


        Article article = this.articleRepository.findOne(id);

        model.addAttribute("view", "comment/create");
        model.addAttribute("article", article);


        return "base-layout";
    }

    @PostMapping("/comment/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createCommentProcess(@PathVariable Integer id, CommentBindingModel commentBindingModel) {

        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(principal.getUsername());

        Date time = new Date();

        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article articleEntity = this.articleRepository.findOne(id);

        Comment commentEntity = new Comment(commentBindingModel.getContent(),userEntity,time,articleEntity);

        this.commentRepository.saveAndFlush(commentEntity);

        return "redirect:/article/"+id;

    }

}
