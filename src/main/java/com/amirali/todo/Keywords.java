package com.amirali.todo;

public enum Keywords {
    CHECKED_TODOS("todo:checked"),
    UNCHECKED_TODOS("todo:unchecked");

    private final String keyword;
    Keywords(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
