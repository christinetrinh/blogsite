package blogsite.news.controller;

import blogsite.news.entity.Article;
import blogsite.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news/")
public class NewsController {

    private final NewsRepository newsRepository;

    @Autowired
    NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    //show the list of news articles
    //using newsIndex.html
    @GetMapping("newslist")
    public String articles(Model model) {
        model.addAttribute("articles", this.newsRepository.findAll());
        return "news-index";
    }

    //view article screen
    @GetMapping("newslist/{id}")
    public String viewArticle(@PathVariable("id") long id, Model model) {
        Article newsArticle = this.newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid news article id : " + id));

        model.addAttribute("article", newsArticle);
        return "view-article";
    }
}
