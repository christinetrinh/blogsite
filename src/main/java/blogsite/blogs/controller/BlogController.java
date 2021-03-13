package blogsite.blogs.controller;


import blogsite.blogs.entity.Blog;
import blogsite.blogs.repository.BlogRepository;
import blogsite.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blogs/")
public class BlogController {

    private final BlogRepository blogRepository;
    private final NewsRepository newsRepository;

    @Autowired
    BlogController(BlogRepository blogRepository, NewsRepository newsRepository){
        this.blogRepository = blogRepository;
        this.newsRepository = newsRepository;
    }

    //show form to add a new blog
    //using add-blog.html
    @GetMapping("showForm")
    public String showForm(Blog blog) {
        return "add-blog";
    }

    //show the list of news articles
    //using newsIndex.html
    @GetMapping("newslist")
    public String articles(Model model) {
        model.addAttribute("articles", this.newsRepository.findAll());
        return "news-index";
    }

    //show the list of blogs
    //using index.html
    @GetMapping("list")
    public String blogs(Model model) {
        model.addAttribute("blogs", this.blogRepository.findAll());
        return "index";
    }

    //adds the new blog
    @PostMapping("add")
    public String addBlog(@Valid Blog blog, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "add-blog";
        }

        this.blogRepository.save(blog);
        return "redirect:list";
    }

    //edit blog screen
    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Blog blog = this.blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog id : " + id));

        model.addAttribute("blog", blog);
        return "update-blog";
    }

    //blog update action
    @PostMapping("update/{id}")
    public String updateBlog(@PathVariable("id") long id, @Valid Blog blog, BindingResult result, Model model) {
        if(result.hasErrors()) {
            blog.setId(id);
            return "update-blog";
        }

        //update blog
        blogRepository.save(blog);

        //get all blogs (with update)
        model.addAttribute("blogs", this.blogRepository.findAll());
        return "index";
    }

    //delete blog action
    @GetMapping("delete/{id}")
    public String deleteBlog(@PathVariable("id") long id, Model model) {
        Blog blog = this.blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog id : " + id));

        this.blogRepository.delete(blog);
        model.addAttribute("blogs", this.blogRepository.findAll());
        return "index";
    }
}
