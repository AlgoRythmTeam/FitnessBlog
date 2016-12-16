package softuniBlog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    private Integer id;
    private String content;
    private User commentAuthor;
    private Date creationDate;
    private Article article;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(nullable = false,name = "authorId")
    public User getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(User author) {
        this.commentAuthor = author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "articleId")
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Comment () {};

    public Comment(String content, User author, Date createddate, Article article) {
        this.content = content;
        this.commentAuthor = author;
        this.creationDate = createddate;
        this.article = article;

    }


}
