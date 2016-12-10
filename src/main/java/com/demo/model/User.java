package com.demo.model;

import java.io.Serializable;

/**
 * Created by tudoubig on 2016/12/9.
 */
public class User implements Serializable {

    private static final long serialVersionUID = -6809878308772836244L;

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
