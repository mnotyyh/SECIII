package com.example.studysystem.entity;

public class SimplePaper {
    private int id;
    private int paper_id;



    private String Document_title;

    private String Publication_Year;
    private String Authors;
    private String Author_Affiliations;
    private String Publication_Title;
    private String Author_Keywords;
    private String Article_Citation_Count;

    public String getArticle_Citation_Count() {
        return Article_Citation_Count;
    }

    public void setArticle_Citation_Count(String article_Citation_Count) {
        Article_Citation_Count = article_Citation_Count;
    }


    public String getDocument_title() {
        return Document_title;
    }

    public void setDocument_title(String document_title) {
        Document_title = document_title;
    }
    public String getPublication_Year() {
        return Publication_Year;
    }

    public void setPublication_Year(String publication_Year) {
        Publication_Year = publication_Year;
    }
    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthors() {
        return Authors;
    }

    public void setAuthors(String authors) {
        Authors = authors;
    }

    public String getAuthor_Affiliations() {
        return Author_Affiliations;
    }

    public void setAuthor_Affiliations(String author_Affiliations) {
        Author_Affiliations = author_Affiliations;
    }

    public String getPublication_Title() {
        return Publication_Title;
    }

    public void setPublication_Title(String publication_Title) {
        Publication_Title = publication_Title;
    }

    public String getAuthor_Keywords() {
        return Author_Keywords;
    }

    public void setAuthor_Keywords(String author_Keywords) {
        Author_Keywords = author_Keywords;
    }
}
