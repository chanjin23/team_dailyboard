package com.team5.first_project.exception;

public class NotFoundByMemberIdException extends IllegalArgumentException{
    public NotFoundByMemberIdException(Long id) {
        super("해당 회원은 존재하지 않는 회원입니다. ID : " + id);
    }
}
