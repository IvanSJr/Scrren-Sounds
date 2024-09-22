package com.github.ivansjr;

import com.github.ivansjr.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Optional<Artist> findByNameContainingIgnoreCase(String name);
}
