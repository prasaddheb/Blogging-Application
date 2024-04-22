
### Description
This project is a backend implementation for a blog application using Spring Boot, Java, and PostgreSQL. It ensures efficient data storage and retrieval, implements RESTful APIs for seamless communication, and integrates security features like JWT authentication.

### Technologies Used
- Java
- Spring Boot
- PostgreSQL
- Spring Data JPA
- Spring Security
- JWT
- Mapstruct
- Swagger

### Installation
1. Clone the repository: `git clone https://github.com/prasaddheb/Blogging-Application`
2. Navigate to the project directory: `cd Blogging-Application`
3. Install dependencies: `mvn install`

### Usage
1. Start the application: `mvn spring-boot:run`
2. Access the API documentation: `http://localhost:8080/swagger-ui.html`

### Features
- Efficient data storage and retrieval with PostgreSQL
- Secure user authentication with JWT
- Comprehensive API documentation with Swagger

### Screenshots
![image](https://github.com/prasaddheb/Blogging-Application/assets/99189572/42949953-e26f-4c94-b3d3-6df3ec6df122)
![image](https://github.com/prasaddheb/Blogging-Application/assets/99189572/e5574e8b-a625-478a-aab2-808dabae3545)


### Code Example
```java
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
}
