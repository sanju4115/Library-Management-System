package com.example.demo.request;

import java.io.Serializable;
import java.util.UUID;

public class LendRequest implements Serializable {

    private UUID userId;
    private UUID bookId;

    public LendRequest() {
    }

    public LendRequest(UUID userId, UUID bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }
}
