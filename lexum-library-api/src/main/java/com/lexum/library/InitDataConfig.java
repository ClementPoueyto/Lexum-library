package com.lexum.library;

import com.lexum.library.entity.Book;
import com.lexum.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("!test") // désactive cette config si profil "test" actif
public class InitDataConfig {

    @Bean
    public CommandLineRunner init(BookRepository repo) {
        return args -> {
            repo.save(new Book(null, "Le Petit Prince", List.of("Antoine de Saint-Exupéry"), LocalDate.of(1943, 4, 6), "Un conte poétique.", 96));
            repo.save(new Book(null, "Effective Java", List.of("Joshua Bloch"), LocalDate.of(2018, 1, 6), "Bonnes pratiques Java.", 416));
            repo.save(new Book(null, "Clean Code", List.of("Robert C. Martin"), LocalDate.of(2008, 8, 1), "Guide du code propre.", 464));
            repo.save(new Book(null, "Le Seigneur des Anneaux", List.of("J.R.R. Tolkien"), LocalDate.of(1954, 7, 29),
                    "Une épopée fantastique dans la Terre du Milieu, où Frodon et ses compagnons doivent détruire l'Anneau Unique pour sauver leur monde du mal.", 1216));

            repo.save(new Book(null, "Harry Potter à l'école des sorciers", List.of("J.K. Rowling"), LocalDate.of(1997, 6, 26),
                    "Le premier tome des aventures de Harry Potter, un jeune sorcier qui découvre ses pouvoirs et intègre Poudlard, une école de magie.", 320));

            repo.save(new Book(null, "Le Trône de Fer", List.of("George R.R. Martin"), LocalDate.of(1996, 8, 6),
                    "Intrigues, batailles et complots politiques dans les Sept Couronnes, une lutte acharnée pour le contrôle du trône de fer.", 694));

            repo.save(new Book(null, "L'Assassin Royal", List.of("Robin Hobb"), LocalDate.of(1995, 3, 1),
                    "Les aventures de FitzChevalerie, un bâtard royal doté de pouvoirs magiques dans un royaume instable.", 448));

            repo.save(new Book(null, "La Belgariade", List.of("David Eddings"), LocalDate.of(1982, 10, 1),
                    "Une quête épique pour récupérer un artefact ancien, mêlant magie, prophéties et héros inattendus.", 560));

            repo.save(new Book(null, "Eragon", List.of("Christopher Paolini"), LocalDate.of(2002, 6, 1),
                    "Un jeune fermier découvre un œuf de dragon et se lance dans une aventure pour devenir un dragonier.", 528));

            repo.save(new Book(null, "La Roue du Temps", List.of("Robert Jordan"), LocalDate.of(1990, 1, 15),
                    "Une saga complexe où le destin du monde repose sur les épaules d’un groupe de jeunes gens aux pouvoirs mystérieux.", 782));

            repo.save(new Book(null, "Le Nom du Vent", List.of("Patrick Rothfuss"), LocalDate.of(2007, 3, 27),
                    "L’histoire de Kvothe, un musicien devenu magicien, racontée en première personne, pleine de mystères et de légendes.", 662));

            repo.save(new Book(null, "Les Chroniques de Narnia", List.of("C.S. Lewis"), LocalDate.of(1950, 10, 16),
                    "Une série de récits fantastiques où des enfants découvrent un monde magique à travers une armoire mystérieuse.", 768));

            repo.save(new Book(null, "Le Cycle d'Elric", List.of("Michael Moorcock"), LocalDate.of(1972, 5, 10),
                    "Les aventures sombres d’Elric, un sorcier albinos et anti-héros, dans un univers en proie à la guerre.", 448));

            repo.save(new Book(null, "La Première Loi", List.of("Joe Abercrombie"), LocalDate.of(2006, 8, 8),
                    "Une trilogie qui mêle cynisme, violence et humour noir dans un monde brutal et sans pitié.", 528));

            repo.save(new Book(null, "L’Empire Ultime", List.of("Brandon Sanderson"), LocalDate.of(2006, 7, 17),
                    "Un monde où la magie est basée sur les métaux, où un groupe de rebelles lutte contre un tyran immortel.", 541));

            repo.save(new Book(null, "La Trilogie de l’Héritage", List.of("Christopher Paolini"), LocalDate.of(2003, 8, 26),
                    "Suite d’Eragon, où le jeune dragonier et son dragon affrontent des forces sombres et des royaumes en guerre.", 700));

            repo.save(new Book(null, "La Belgariade", List.of("David Eddings"), LocalDate.of(1982, 11, 1),
                    "Une quête épique à travers des terres magiques pour retrouver un artefact ancien aux pouvoirs immenses.", 560));

            repo.save(new Book(null, "La Quête d’Ewilan", List.of("Pierre Bottero"), LocalDate.of(2003, 9, 4),
                    "Les aventures d’une jeune fille qui découvre un monde parallèle où elle détient des pouvoirs exceptionnels.", 384));

            repo.save(new Book(null, "Les Salauds Gentilshommes", List.of("Scott Lynch"), LocalDate.of(2006, 6, 1),
                    "Une série centrée sur un groupe de voleurs et arnaqueurs dans une cité fantaisie pleine de complots.", 576));

            repo.save(new Book(null, "L’Épée de Vérité", List.of("Terry Goodkind"), LocalDate.of(1994, 8, 15),
                    "Une saga épique mêlant magie, philosophie et aventures autour d’un héros confronté à de terribles menaces.", 704));

            repo.save(new Book(null, "Les Aventuriers de la Mer", List.of("Robin Hobb"), LocalDate.of(1998, 10, 1),
                    "Des pirates, des magiciens et des aventures en mer dans un univers riche en mystères et en conflits.", 560));

            repo.save(new Book(null, "Le Livre Malazéen des Gladiateurs", List.of("Steven Erikson"), LocalDate.of(1999, 4, 1),
                    "Une saga dense et épique mêlant guerre, magie et intrigues politiques dans un monde sombre.", 1200));

            repo.save(new Book(null, "Le Cycle de Terremer", List.of("Ursula K. Le Guin"), LocalDate.of(1968, 9, 1),
                    "Les aventures du magicien Ged dans un archipel mystérieux, explorant le pouvoir, la sagesse et l’équilibre.", 320));

        };
    }
}
