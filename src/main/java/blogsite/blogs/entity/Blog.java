package blogsite.blogs.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public Blog() {
        super();
    }


    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @PostRemove
    public void onPostRemove() {
        final Logger LOG =   LoggerFactory.getLogger(Blog.class);
        LOG.info("Deleted blog: " + title);
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //System.out.println("AUDIT -------- Deleted blog: " + title + " at " + timestamp);
    }

}
