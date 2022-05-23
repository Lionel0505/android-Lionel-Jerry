package com.example.zanakay;

public class Article {

    int id, idCat;
    String titre, description, path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Article(int id, String titre, String description, String path) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.path = path;
    }

    public Article(String titre) {
        setTitre(titre);
    }


}
