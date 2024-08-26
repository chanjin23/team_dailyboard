package com.team5.first_project.exception;

public class NotFoundByCommentIdException extends IllegalArgumentException {
    public NotFoundByCommentIdException(Long id) {
        super("해당 댓글은 존재하지 않는 댓글입니다. ID : " + id);
    }
}
