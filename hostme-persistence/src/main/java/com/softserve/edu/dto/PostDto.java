package com.softserve.edu.dto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.softserve.edu.model.Post;

public class PostDto {
    
    private Long id;
    
    private String content;
    
    private Calendar postedAt;
    
    private String authorId;
    
    private String author;
    
    public PostDto() {
	
    }

    public PostDto(Post post) {
	this.id = post.getId();
	this.content = post.getContent();
	this.postedAt = post.getPostedAt();
	this.authorId = post.getAuthor().getUserId().toString();
	this.author = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Calendar postedAt) {
        this.postedAt = postedAt;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getPostDate() {
	if (postedAt != null) {
	    return new SimpleDateFormat("yyyy-MM-dd").format(postedAt.getTime());
	}
	return null;
    }

    public String getPostTime() {
	if (postedAt != null) {
	    return new SimpleDateFormat("HH:mm:ss").format(postedAt.getTime());
	}
	return null;
    }

}