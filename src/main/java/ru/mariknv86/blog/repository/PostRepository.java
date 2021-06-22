package ru.mariknv86.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mariknv86.blog.model.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

}
