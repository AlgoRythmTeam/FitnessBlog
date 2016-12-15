package softuniBlog.entity;



import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")

public class Article {

    private Integer id;
    private String title;
    private String content;
    private User author;
    private Date creationDate;
    private Date editedDate;
    private Category category;
    private Set<Tag> tags;

    @ManyToMany()
    @JoinColumn(table = "articles_tags")
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @ManyToOne
    @JoinColumn(nullable = false,name="categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Column(columnDefinition = "text",nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @ManyToOne
    @JoinColumn(nullable = false,name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Article(String title, String content, User author, Date createddate, Date editedDate, Category category, HashSet<Tag> tags){
        this.title=title;
        this.content=content;
        this.author=author;
        this.creationDate = createddate;
        this.editedDate=editedDate;
        this.category=category;
        this.tags=tags;

    }

    public Article(){    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    @Transient
    public String getSummary(){
        return this.getContent().substring(0,1 + this.getContent().length()/5) + "...";
    }

    @Transient
    public String getShortTitle(){

        if (this.getTitle().length()>39){
            return this.getTitle().substring(0,39) + "...";
        }
        else
            return this.getTitle();
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }
}
