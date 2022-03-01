package ru.mariknv86.blog.service;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mariknv86.blog.dto.request.CommentRequestDto;
import ru.mariknv86.blog.dto.response.ResultsResponseDto;
import ru.mariknv86.blog.model.Post;
import ru.mariknv86.blog.model.PostComment;
import ru.mariknv86.blog.repository.PostCommentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final UserService userService;
    private final PostCommentRepository postCommentRepository;

    public ResultsResponseDto<Integer> addComment(CommentRequestDto commentDto) {
        PostComment comment = new PostComment();
        Post post = postService.getPostById(commentDto.getPostId());
        comment.setPostId(post);
        comment.setTime(LocalDateTime.now());
        comment.setText(commentDto.getText());
        comment.setUserId(userService.getCurrentUser());

        int parentId;
        if(commentDto.getParentId().isEmpty()) {
            parentId = 0;
        } else {
            parentId = Integer.parseInt(commentDto.getParentId());
        }

        if(parentId != 0) {
            PostComment parent = postCommentRepository.findById(parentId)
                .orElseThrow(EntityNotFoundException::new);
            comment.setParentId(parent);
            postCommentRepository.save(comment);
        }
        return new ResultsResponseDto<Integer>().setId(comment.getId());
    }

}
