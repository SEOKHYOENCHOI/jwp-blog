package techcourse.myblog.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import techcourse.myblog.domain.Comment;
import techcourse.myblog.service.CommentService;
import techcourse.myblog.service.dto.CommentRequest;
import techcourse.myblog.service.dto.UserResponse;
import techcourse.myblog.service.exception.NotSameAuthorException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/articles/{articleId}/comments")
    public String saveComment(@PathVariable("articleId") Long articleId, @Valid CommentRequest commentRequest,
                              BindingResult bindingResult, HttpSession httpSession) {
        if (!bindingResult.hasErrors()) {
            UserResponse user = (UserResponse) httpSession.getAttribute("user");
            commentService.save(commentRequest, articleId, user.getId());
        }

        return "redirect:/articles/" + articleId;
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public String deleteComment(@PathVariable("articleId") Long articleId, @PathVariable("commentId") Long commentId, HttpSession httpSession) {
        Comment comment = commentService.findCommentById(commentId);
        UserResponse userResponse = (UserResponse) httpSession.getAttribute("user");

        if (!comment.isSameAuthor(userResponse.getEmail())) {
            throw new NotSameAuthorException("해당 댓글의 작성자가 아닙니다.");
        }

        commentService.deleteComment(commentId);
        return "redirect:/articles/" + articleId;
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public String updateComment(@PathVariable("articleId") Long articleId, @PathVariable("commentId") Long commentId, CommentRequest commentRequest, HttpSession httpSession) {
        Comment comment = commentService.findCommentById(commentId);
        UserResponse userResponse = (UserResponse) httpSession.getAttribute("user");

        if (!comment.isSameAuthor(userResponse.getEmail())) {
            throw new NotSameAuthorException("해당 댓글의 작성자가 아닙니다.");
        }

        commentService.updateComment(comment, commentRequest);

        return "redirect:/articles/" + articleId;
    }
}
