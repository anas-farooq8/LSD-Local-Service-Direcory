package com.example.lsd.Models;

import java.util.Date;

public class Suggestion {
    private int suggestionId;
    private String seekerUsername;
    private String comment;
    private Date commentDate;

    // Default constructor
    public Suggestion() {
    }

    // Parameterized constructor
    public Suggestion(int suggestionId, String seekerUsername, String comment, Date commentDate) {
        this.suggestionId = suggestionId;
        this.seekerUsername = seekerUsername;
        this.comment = comment;
        this.commentDate = commentDate;

    }

    // Getter and Setter methods
    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public String getSeekerUsername() {
        return seekerUsername;
    }

    public void setSeekerUsername(String seekerUsername) {this.seekerUsername = seekerUsername;}

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {this.comment = comment;}

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }


}
