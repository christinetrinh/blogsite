package blogsite;

import blogsite.blogs.entity.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogsiteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllBlogs() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/blogs/list",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetBlogById() {
        Blog blog = restTemplate.getForObject(getRootUrl() + "/blogs/1", Blog.class);
        assertNotNull(blog);
    }

    @Test
    public void testCreateBlog() {
        Blog blog = new Blog();
        blog.setTitle("Title");
        blog.setContent("Content");
        ResponseEntity<Blog> postResponse = restTemplate.postForEntity(getRootUrl() + "/blogs/list", blog, Blog.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateBlog() {
        int id = 1;
        Blog blog = restTemplate.getForObject(getRootUrl() + "/blogs/list/" + id, Blog.class);
        blog.setTitle("test1");
        blog.setContent("test2");
        restTemplate.put(getRootUrl() + "/blogs/list/" + id, blog);
        Blog updatedBlog = restTemplate.getForObject(getRootUrl() + "/blogs/list/" + id, Blog.class);
        assertNotNull(updatedBlog);
    }

    @Test
    public void testDeleteBlog() {
        int id = 2;
        Blog blog = restTemplate.getForObject(getRootUrl() + "/blogs/list/" + id, Blog.class);
        assertNotNull(blog);
        restTemplate.delete(getRootUrl() + "/blogs/list/" + id);
        try {
            blog = restTemplate.getForObject(getRootUrl() + "/blogs/list/" + id, Blog.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
