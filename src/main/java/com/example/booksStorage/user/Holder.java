package com.example.booksStorage.user;

import com.example.booksStorage.validations.Validations;
import lombok.Getter;

@Getter
public class Holder {
    private Long holderId;

    public void setHolderId(Long holderId) {
        this.holderId = Validations.validate(holderId);
    }
}
