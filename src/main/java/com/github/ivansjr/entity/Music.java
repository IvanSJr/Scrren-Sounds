package com.github.ivansjr.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String gender;

    @ManyToOne
    private Artist artist;

    public Music() {
    }

    public Music(Long id, String name, String gender, Artist artist) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.artist = artist;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Nome: " + name + "\n" +
                "Gênero: " + gender + "\n" +
                "Artista: " + artist.getName() + "\n";
    }
}
