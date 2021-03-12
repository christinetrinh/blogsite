package blogsite.news.entity;

import javax.persistence.*;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "articleTitle")
    private String articleTitle;

    @Column(name = "articleContent")
    private String articleContent;

    public Article() { super(); }

    public Article(String articleTitle, String articleContent){
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}
