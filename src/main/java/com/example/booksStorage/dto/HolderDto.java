package com.example.booksStorage.dto;

import com.example.booksStorage.validations.Validations;
import lombok.Getter;

@Getter
public class HolderDto {
    private Long holderId;

    public void setHolderId(Long holderId) {
        this.holderId = Validations.validate(holderId);
    }
}
