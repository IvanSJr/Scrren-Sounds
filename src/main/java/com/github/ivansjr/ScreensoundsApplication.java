package com.github.ivansjr;

import com.github.ivansjr.main.Main;
import com.github.ivansjr.repository.ArtistRepository;
import com.github.ivansjr.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreensoundsApplication implements CommandLineRunner {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private MusicRepository musicRepository;

    public static void main(String[] args) {
        SpringApplication.run(ScreensoundsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main(artistRepository, musicRepository);
        main.showMenu();
    }
}
