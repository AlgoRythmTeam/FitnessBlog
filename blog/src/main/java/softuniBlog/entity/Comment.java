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
    private Date editedDate;


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
    @JoinColumn(nullable = false,name = "commentAuthorId")
    public User getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(User commentAuthor) {
        this.commentAuthor = commentAuthor;
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

    public Comment(String content, User commentAuthor, Date createddate, Article article,  Date editedDate) {
        this.content = content;
        this.commentAuthor = commentAuthor;
        this.creationDate = createddate;
        this.article = article;
        this.editedDate=editedDate;

    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }
}
