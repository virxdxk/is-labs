//package ru.itmo.se.is;
//
//
//import jakarta.annotation.PostConstruct;
//import jakarta.ejb.Singleton;
//import jakarta.ejb.Startup;
//import jakarta.inject.Inject;
//import ru.itmo.se.is.entity.Coordinates;
//import ru.itmo.se.is.entity.Location;
//import ru.itmo.se.is.entity.Movie;
//import ru.itmo.se.is.entity.Person;
//import ru.itmo.se.is.entity.value.Color;
//import ru.itmo.se.is.entity.value.MovieGenre;
//import ru.itmo.se.is.entity.value.MpaaRating;
//import ru.itmo.se.is.repository.MovieRepository;
//import ru.itmo.se.is.repository.PersonRepository;
//
//import java.time.ZonedDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//@Singleton
//@Startup
//public class TestDataInitializer {
//
//    @Inject
//    private MovieRepository movieRepository;
//
//    @Inject
//    private PersonRepository personRepository;
//
//    @PostConstruct
//    public void init() {
//        // Локации
//        Location loc1 = new Location();
//        loc1.setX(10.5f);
//        loc1.setY(20.3);
//        loc1.setZ(5.0);
//
//        Location loc2 = new Location();
//        loc2.setX(50.0f);
//        loc2.setY(100.0);
//        loc2.setZ(15.0);
//
//        // Персоны
//        Person dir1 = new Person();
//        dir1.setName("Christopher Nolan");
//        dir1.setHairColor(Color.BROWN);
//        dir1.setEyeColor(Color.GREEN);
//        dir1.setLocation(loc1);
//        dir1.setWeight(75.0f);
//
//        Person scr1 = new Person();
//        scr1.setName("Jonathan Nolan");
//        scr1.setHairColor(Color.BROWN);
//        scr1.setEyeColor(Color.YELLOW);
//        scr1.setLocation(loc2);
//        scr1.setWeight(70.0f);
//
//        Person op1 = new Person();
//        op1.setName("Wally Pfister");
//        op1.setHairColor(Color.BROWN);
//        op1.setEyeColor(Color.GREEN);
//        op1.setLocation(loc1);
//        op1.setWeight(80.0f);
//
//        dir1 = personRepository.save(dir1);
//        scr1 = personRepository.save(scr1);
//        op1 = personRepository.save(op1);
//
//        Movie m1 = new Movie();
//        m1.setName("Inception");
//        m1.setCoordinates(new Coordinates());
//        m1.getCoordinates().setX(100.0);
//        m1.getCoordinates().setY(200);
//        m1.setCreationDate(ZonedDateTime.now());
//        m1.setOscarsCount(1);
//        m1.setBudget(160_000_000f);
//        m1.setTotalBoxOffice(829_895_144);
//        m1.setMpaaRating(MpaaRating.PG_13);
//        m1.setDirector(dir1);
//        m1.setScreenwriter(scr1);
//        m1.setOperator(op1);
//        m1.setLength(148);
//        m1.setGoldenPalmCount(1);
//        m1.setUsaBoxOffice(292_576_195);
//        m1.setTagline("Your mind is the scene of the crime");
//        m1.setGenre(MovieGenre.DRAMA);
//
//        movieRepository.save(m1);
//
//        movieRepository.findAll().forEach(System.out::println);
//
//        System.out.println("Тестовые данные успешно добавлены");
//    }
//}
