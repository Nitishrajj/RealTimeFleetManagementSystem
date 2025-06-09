package com.fleetmanagement.adminservice.dto;

public class GenericResponseDTO {
    private String message;
    private Long id;

    public GenericResponseDTO(String message, Long id) {
        this.message = message;
        this.id = id;
    }
}

