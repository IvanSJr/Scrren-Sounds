package com.github.ivansjr.repository;

import com.github.ivansjr.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {

    List<Music> findAllByArtistNameContainingIgnoreCase(String artistName);
}
