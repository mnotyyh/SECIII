package com.example.studysystem.entity;

public class SimpleAuthor {
    private String Author_name;
    private String Org;

    public boolean equals(Object obj) {
            SimpleAuthor u = (SimpleAuthor) obj;
            return this.getAuthor_name().equals(u.getAuthor_name()) && this.getOrg().equals(u.getOrg());
          }

    public String getAuthor_name() {
        return Author_name;
    }

    public void setAuthor_name(String author_name) {
        Author_name = author_name;
    }

    public String getOrg() {
        return Org;
    }

    public void setOrg(String org_name) {
        Org = org_name;
    }
}
