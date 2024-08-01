package com.elice.c4.post.domain;

public class Post {

    private long id;
    private  String  title;
    private  String content;

    public Post(){

    }

    public Post(String  title,String content){
        this.title = title;
        this.content=content;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content=content;
    }
}
