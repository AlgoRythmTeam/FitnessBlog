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
import softuniBlog.bindingModel.CommentBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Comment;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CommentRepository;
import softuniBlog.repository.UserRepository;

import java.util.Date;
import java.util.Set;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/comment{id}/comments")
    @PreAuthorize("isAuthenticated()")
    public String commentList(Model model,@PathVariable Integer id){

        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        Article article = this.articleRepository.findOne(id);
        Set<Comment> comments= article.getComments();

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User entityUser = this.userRepository.findByEmail(principal.getUsername());

            model.addAttribute("user", entityUser);
        }

        model.addAttribute("view","comment/viewComments");
        model.addAttribute("article", article);
        model.addAttribute("comments", comments);

        return "base-layout" ;
    }

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
    @GetMapping("/comment/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.commentRepository.exists(id)) {
            return "redirect:/";
        }

        Comment comment = this.commentRepository.findOne(id);

        if (!isUserAuthorOrAdmin(comment)) {
            return "redirect:/comment/" + id;
        }

        model.addAttribute("view", "comment/edit");
        model.addAttribute("comment", comment);

        return "base-layout";
    }

    public boolean isUserAuthorOrAdmin(Comment comment) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        return userEntity.isAdmin() || userEntity.isCommentAuthor(comment);
    }

    @PostMapping("/comment/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, CommentBindingModel commentBindingModel) {

        if (!this.commentRepository.exists(id)) {
            return "redirect:/";
        }

        Comment comment = this.commentRepository.findOne(id);

        if (!isUserAuthorOrAdmin(comment)) {
            return "redirect:/comment/" + id;
        }


        comment.setContent(commentBindingModel.getContent());
        comment.setEditedDate(new Date());

        this.commentRepository.saveAndFlush(comment);

        return "redirect:/comment"+ comment.getArticle().getId() + "/comments";
    }
}
