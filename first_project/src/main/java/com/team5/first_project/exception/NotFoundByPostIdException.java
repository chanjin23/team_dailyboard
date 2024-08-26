package com.team5.first_project.exception;

public class NotFoundByPostIdException extends IllegalArgumentException{
    public NotFoundByPostIdException(Long id) {
        super("해당 게시글은 존재하지 않는 게시글입니다. ID : " + id);
    }
}
