package com.dragand.spring_tutorial.webpatternsca3.business.dto;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String newPassword,
        String oldPassword,
        String username
) {
}
