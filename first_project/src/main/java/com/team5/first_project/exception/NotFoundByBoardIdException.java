package com.team5.first_project.exception;

public class NotFoundByBoardIdException extends IllegalArgumentException{

    public NotFoundByBoardIdException(Long id) {
        super("해당 게시판은 존재하지 않는 게시판입니다. ID : " + id);
    }
}
