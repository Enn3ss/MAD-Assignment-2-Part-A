package com.example.mad_assignment_part_a.data;

public class PostData
{
    private int userId;
    private int id;
    private String title;
    private String body;

    public PostData(int userId, int id, String title, String body)
    {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() { return userId; }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }
}
