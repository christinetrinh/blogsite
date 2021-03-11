package blogsite.controller;

import blogsite.entity.Blog;
import blogsite.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blogs/")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("showForm")
    public String showForm(Blog blog) {
        return "add-blog";
    }

    @GetMapping("list")
    public String blogs(Model model) {
        model.addAttribute("blogs", this.blogRepository.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addBlog(@Valid Blog blog, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "add-blog";
        }

        this.blogRepository.save(blog);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Blog blog = this.blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog id : " + id));

        model.addAttribute("blog", blog);
        return "update-blog";
    }

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

    @GetMapping("delete/{id}")
    public String deleteBlog(@PathVariable("id") long id, Model model) {
        Blog blog = this.blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid blog id : " + id));

        this.blogRepository.delete(blog);
        model.addAttribute("blogs", this.blogRepository.findAll());
        return "index";
    }
}
