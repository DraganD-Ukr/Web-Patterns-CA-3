package com.dragand.spring_tutorial.webpatternsca3.business.dto;

/**
 * User update request DTO
 * @param firstName
 * @param lastName
 * @param newPassword
 * @param oldPassword
 * @param username
 */
public record UserUpdateRequest(
        String firstName,
        String lastName,
        String newPassword,
        String oldPassword,
        String username
) {
}
