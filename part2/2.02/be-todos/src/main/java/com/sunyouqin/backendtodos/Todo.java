package com.sunyouqin.backendtodos;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {

    private String content;
    private String status;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

