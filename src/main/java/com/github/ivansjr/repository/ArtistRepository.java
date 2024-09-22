package com.github.ivansjr.repository;

import com.github.ivansjr.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Optional<Artist> findByNameContainingIgnoreCase(String name);

    @Query(
        value = "SELECT a.summary FROM Artist AS a WHERE a.name ILIKE CONCAT('%', :artistName ,'%')"
    )
    String findSummaryToArtist(String artistName);
}
