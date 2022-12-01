package com.project.devgram.repository;

import com.project.devgram.dto.CommentDto;
import com.project.devgram.entity.Comment;
import com.project.devgram.type.CommentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<CommentDto>> findByBoardSeqAndCommentStatusNot(Long boardSeq, CommentStatus commentStatus);

    Optional<List<CommentDto>> findByCommentStatus(CommentStatus commentStatus);
}
