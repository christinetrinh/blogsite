package blogsite;

import blogsite.blogs.entity.Blog;
import blogsite.blogs.repository.BlogRepository;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class BlogRepositoryTests {

    @Autowired
    private BlogRepository blogRepository;


    @Test
    @Commit
    public void testCreateBlog() {
        Blog savedBlog = blogRepository.save(new Blog("blogTitle", "blogContent"));
        assertThat(savedBlog.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindBlog(){
        blogRepository.save(new Blog("blogTitle", "blogContent"));
        Blog blog = blogRepository.findByTitle("blogTitle");
        assertThat(blog.getTitle()).isEqualTo("blogTitle");
    }

    @Test
    public void testListProducts() {
        blogRepository.save(new Blog("blog1", "blogContent"));
        blogRepository.save(new Blog("blog2", "blogContent"));
        List<Blog> blogs = (List<Blog>) blogRepository.findAll();
        assertThat(blogs).size().isGreaterThan(0);
    }

    @Test
    public void testUpdateBlog() {
        Blog savedBlog = blogRepository.save(new Blog("blogTitle", "blogContent"));
        savedBlog.setTitle("New Title");

        blogRepository.save(savedBlog);

        Blog updatedBlog = blogRepository.findByTitle("New Title");

        assertThat(updatedBlog.getTitle()).isEqualTo("New Title");
    }

    @Test
    public void testDeleteBlog() {
        Blog savedBlog = blogRepository.save(new Blog("deleteBlogTitle", "blogContent"));
        blogRepository.deleteById(savedBlog.getId());

        Blog deletedBlog = blogRepository.findByTitle("deleteBlogTitle");

        assertThat(deletedBlog).isNull();

    }


}
