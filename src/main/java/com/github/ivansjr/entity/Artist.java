package com.github.ivansjr.entity;

import com.github.ivansjr.dto.Type;
import com.github.ivansjr.service.ChatGPTApiConsumer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "artist")
    private List<Music> musics = new ArrayList<Music>();

    @Column(name = "summary", length = 5048)
    private String summary;

    public Artist() {
    }

    public Artist(Long id, String name, Type type, List<Music> musics) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.musics = musics;
        this.summary = ChatGPTApiConsumer.getSummaryToArtist(name).trim();
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Nome: " + name + "\n" +
                "Tipo: " + type + "\n";
    }
}
