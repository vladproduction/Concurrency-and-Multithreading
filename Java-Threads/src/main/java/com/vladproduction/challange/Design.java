package com.vladproduction.challange;

import java.util.ArrayList;
import java.util.List;

public class Design {

    private final int code;
    private final String name;
    private final List<Long> votes;

    public Design(int code, String name) {
        this.code = code;
        this.name = name;
        this.votes = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Long> getVotes() {
        return votes;
    }
}
