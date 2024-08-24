package com.team5.first_project.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum MemberRoleEnum {
    USER("User"),
    ADMIN("Admin");

    private final String roleName;
}
