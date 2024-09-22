package com.github.ivansjr.main;

import com.github.ivansjr.entity.Music;
import com.github.ivansjr.repository.ArtistRepository;
import com.github.ivansjr.dto.Type;
import com.github.ivansjr.entity.Artist;
import com.github.ivansjr.repository.MusicRepository;
import com.github.ivansjr.service.ChatGPTApiConsumer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Main {

    private final Scanner scanner = new Scanner(System.in);

    private final ArtistRepository artistRepository;
    private final MusicRepository musicRepository;

    public Main(ArtistRepository artistRepository, MusicRepository musicRepository) {
        this.artistRepository = artistRepository;
        this.musicRepository = musicRepository;
    }

    public void showMenu() {
        int option = -1;
        while (option != 9) {
            String menu = """
                *** SCREENSOUND APPLICATION ***
                
                1 - Cadastrar artistas
                2 - Cadastrar músicas
                3 - Listar músicas cadastradas
                4 - Buscar músicas cadastradas
                5 - Buscar artistas cadastradas
                6 - Pesquisa dados de um artista
                7 - Atualizar dados de um artista
                9 - Sair
                """;

            System.out.println(menu);
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida");
                continue;
            }

            switch (option) {
                case 1 -> saveArtists();
                case 2 -> saveMusics();
                case 3 -> findAllMusics();
                case 4 -> findMusicByArtist();
                case 5 -> findAllArtist();
                case 6 -> findDataArtist();
                case 7 -> updateDataArtist();

                case 9 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void findAllArtist() {
        List<Artist> artists = artistRepository.findAll();
        artists.forEach(System.out::println);
    }

    private void saveArtists() {
        var repeat = "S";
        while (repeat.equalsIgnoreCase("S")) {
            System.out.println("Digite o nome do artista: ");
            var name = scanner.nextLine();

            System.out.println("Digite o tipo do artista: (Solo ou Dupla ou Banda)");
            var type = scanner.nextLine();
            Artist savedArtist = artistRepository.save(new Artist(null, name, Type.fromTranslate(type.toLowerCase()), null));
            System.out.println(savedArtist);

            System.out.println("\nCadastrar um novo artista: (S/N)");
            repeat = scanner.nextLine();
        }
    }

    private void saveMusics() {
        var repeat = "S";

        while (repeat.equalsIgnoreCase("S")) {
            System.out.println("De qual artista você quer cadastrar a música? ");
            var artistName = scanner.nextLine();

            Optional<Artist> foundArtist = getArtistByName(artistName);
            if (foundArtist.isPresent()) {
                System.out.println("Digite o nome da música que quer cadastrar: ");
                var musicName = scanner.nextLine();

                System.out.println("Digite o gênero da música que quer cadastrar: ");
                var gender = scanner.nextLine();

                musicRepository.save(new Music(null, musicName, gender, foundArtist.get()));
            } else {
                System.out.println("Artista " + artistName + " não foi encontrado no nosso registro.");
            }

            System.out.println("\nCadastrar uma nova música? (S/N)");
            repeat = scanner.nextLine();
        }
    }

    private void findAllMusics() {
        List<Music> musics = musicRepository.findAll();
        musics.forEach(
            music -> {
                System.out.println("Nome: " + music.getName());
                System.out.println("Gênero: " + music.getGender());
                System.out.println("Artista: " + music.getArtist().getName() + "\n");
            }
        );
    }

    private void findMusicByArtist() {
        System.out.println("De qual artista você quer buscar as músicas? ");
        var artistName = scanner.nextLine();
        List<Music> musics = musicRepository.findAllByArtistNameContainingIgnoreCase(artistName);
        if (musics.isEmpty()) {
            System.out.println("Nenhuma música encontrada");
        } else {
            musics.forEach(System.out::println);
        }
    }

    private void findDataArtist() {
        System.out.println("Buscar dados de qual artista?");
        var artistName = scanner.nextLine();
        String foundArtistSummary = getSummaryToArtist(artistName);
        if (foundArtistSummary == null) {
            System.out.println("Nenhum dado encontrado\n");
            System.out.println("Deseja atualizar seus dados? (S/N)");
            var updateSummary = scanner.nextLine();
            if (updateSummary.equalsIgnoreCase("S")) {
                this.updateDataArtist(foundArtistSummary);
                printOutSummaryToArtist(artistName, getSummaryToArtist(artistName));
            }
        } else {
            printOutSummaryToArtist(artistName, foundArtistSummary);
        }
    }

    private String getSummaryToArtist(String artistName) {
        return artistRepository.findSummaryToArtist(artistName);
    }

    private void updateDataArtist() {
        System.out.println("Atualizar dados de qual artista? ");
        var artistName = scanner.nextLine();
        updateArtistSummary(artistName);
    }

    private void updateDataArtist(String artistName) {
        updateArtistSummary(artistName);
    }

    private void updateArtistSummary(String artistName) {
        Optional<Artist> foundArtist = getArtistByName(artistName);
        if (foundArtist.isPresent()) {
            Artist artist = foundArtist.get();
            artist.setSummary(ChatGPTApiConsumer.getSummaryToArtist(artist.getName()));
            artistRepository.save(artist);
        } else {
            System.out.println("Artista " + artistName + " não foi encontrado no nosso registro.");
        }
    }

    private Optional<Artist> getArtistByName(String artistName) {
        return artistRepository.findByNameContainingIgnoreCase(artistName);
    }

    private static void printOutSummaryToArtist(String artistName, String foundArtistSummary) {
        System.out.println(
                "Resumo sobre o artista " + artistName + ":\n" +
                        foundArtistSummary
        );
    }
}
