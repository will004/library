package com.example.library.dto;

import lombok.Data;

@Data
public abstract class BaseDTO<T> {

    private Long id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public T setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return (T) this;
    }

    public T setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return (T) this;
    }

    public T setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
        return (T) this;
    }

}
