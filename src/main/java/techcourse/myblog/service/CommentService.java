package techcourse.myblog.service;

import org.springframework.stereotype.Service;
import techcourse.myblog.domain.*;
import techcourse.myblog.service.dto.CommentRequest;
import techcourse.myblog.service.dto.UserResponse;
import techcourse.myblog.service.exception.NoArticleException;
import techcourse.myblog.service.exception.NoUserException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public void save(CommentRequest commentRequest, Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NoArticleException("게시글이 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoUserException("유저가 존재하지 않습니다."));

        Comment comment = new Comment(commentRequest.getContents(), user, article);

        commentRepository.save(comment);
    }
}