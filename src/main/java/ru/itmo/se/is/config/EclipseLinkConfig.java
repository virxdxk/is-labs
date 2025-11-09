package ru.itmo.se.is.config;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.RelationalDescriptor;
import org.eclipse.persistence.internal.sessions.DatabaseSessionImpl;
import org.eclipse.persistence.mappings.AggregateObjectMapping;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.mappings.OneToOneMapping;
import org.eclipse.persistence.mappings.converters.EnumTypeConverter;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.DatabaseSession;
import org.eclipse.persistence.sessions.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.se.is.annotation.EntityDescriptor;
import ru.itmo.se.is.db.converter.ZonedDateTimeConverter;
import ru.itmo.se.is.entity.Coordinates;
import ru.itmo.se.is.entity.Location;
import ru.itmo.se.is.entity.Movie;
import ru.itmo.se.is.entity.Person;
import ru.itmo.se.is.entity.value.Color;
import ru.itmo.se.is.entity.value.Country;
import ru.itmo.se.is.entity.value.MovieGenre;
import ru.itmo.se.is.entity.value.MpaaRating;

import java.util.List;

@Configuration
public class EclipseLinkConfig {

    @Autowired
    private DatabaseLogin databaseLogin;

    @Bean
    @EntityDescriptor
    public ClassDescriptor personDescriptor() {
        ClassDescriptor descriptor = new RelationalDescriptor();
        descriptor.setJavaClass(Person.class);
        descriptor.setTableName("person");
        descriptor.addPrimaryKeyFieldName("id");
        descriptor.setSequenceNumberFieldName("id");
        descriptor.setSequenceNumberName("person_id_seq");

        DirectToFieldMapping idMapping = new DirectToFieldMapping();
        idMapping.setAttributeName("id");
        idMapping.setFieldName("id");
        idMapping.setIsPrimaryKeyMapping(true);
        descriptor.addMapping(idMapping);

        DirectToFieldMapping nameMapping = new DirectToFieldMapping();
        nameMapping.setAttributeName("name");
        nameMapping.setFieldName("name");
        descriptor.addMapping(nameMapping);

        DirectToFieldMapping eyeColorMapping = new DirectToFieldMapping();
        eyeColorMapping.setAttributeName("eyeColor");
        eyeColorMapping.setFieldName("eye_color");
        EnumTypeConverter eyeColorConverter = new EnumTypeConverter(eyeColorMapping, Color.class, false);
        eyeColorMapping.setConverter(eyeColorConverter);
        descriptor.addMapping(eyeColorMapping);

        DirectToFieldMapping hairColorMapping = new DirectToFieldMapping();
        hairColorMapping.setAttributeName("hairColor");
        hairColorMapping.setFieldName("hair_color");
        hairColorMapping.setIsOptional(false);
        EnumTypeConverter hairColorConverter = new EnumTypeConverter(hairColorMapping, Color.class, false);
        hairColorMapping.setConverter(hairColorConverter);
        descriptor.addMapping(hairColorMapping);

        AggregateObjectMapping locationMapping = new AggregateObjectMapping();
        locationMapping.setAttributeName("location");
        locationMapping.setReferenceClass(Location.class);
        locationMapping.addFieldNameTranslation("location_x", "x");
        locationMapping.addFieldNameTranslation("location_y", "y");
        locationMapping.addFieldNameTranslation("location_z", "z");
        descriptor.addMapping(locationMapping);

        DirectToFieldMapping weightMapping = new DirectToFieldMapping();
        weightMapping.setAttributeName("weight");
        weightMapping.setFieldName("weight");
        descriptor.addMapping(weightMapping);

        DirectToFieldMapping nationalityMapping = new DirectToFieldMapping();
        nationalityMapping.setAttributeName("nationality");
        nationalityMapping.setFieldName("nationality");
        EnumTypeConverter natConv = new EnumTypeConverter(nationalityMapping, Country.class, false);
        nationalityMapping.setConverter(natConv);
        descriptor.addMapping(nationalityMapping);

        return descriptor;
    }

    @Bean
    @EntityDescriptor
    public ClassDescriptor movieDescriptor() {
        RelationalDescriptor descriptor = new RelationalDescriptor();
        descriptor.setJavaClass(Movie.class);
        descriptor.setTableName("movie");
        descriptor.addPrimaryKeyFieldName("id");
        descriptor.setSequenceNumberFieldName("id");
        descriptor.setSequenceNumberName("movie_id_seq");

        DirectToFieldMapping idMapping = new DirectToFieldMapping();
        idMapping.setAttributeName("id");
        idMapping.setFieldName("id");
        idMapping.setIsPrimaryKeyMapping(true);
        descriptor.addMapping(idMapping);

        DirectToFieldMapping nameMapping = new DirectToFieldMapping();
        nameMapping.setAttributeName("name");
        nameMapping.setFieldName("name");
        nameMapping.setIsOptional(false);
        descriptor.addMapping(nameMapping);

        AggregateObjectMapping coordinatesMapping = new AggregateObjectMapping();
        coordinatesMapping.setAttributeName("coordinates");
        coordinatesMapping.setReferenceClass(Coordinates.class);
        coordinatesMapping.addFieldNameTranslation("coordinates_x", "x");
        coordinatesMapping.addFieldNameTranslation("coordinates_y", "y");
        descriptor.addMapping(coordinatesMapping);

        DirectToFieldMapping creationDateMapping = new DirectToFieldMapping();
        creationDateMapping.setAttributeName("creationDate");
        creationDateMapping.setFieldName("creation_data");
        creationDateMapping.setIsOptional(false);
        creationDateMapping.setConverter(new ZonedDateTimeConverter());
        descriptor.addMapping(creationDateMapping);

        DirectToFieldMapping oscarsMapping = new DirectToFieldMapping();
        oscarsMapping.setAttributeName("oscarsCount");
        oscarsMapping.setFieldName("oscars_count");
        oscarsMapping.setIsOptional(false);
        descriptor.addMapping(oscarsMapping);

        DirectToFieldMapping budgetMapping = new DirectToFieldMapping();
        budgetMapping.setAttributeName("budget");
        budgetMapping.setFieldName("budget");
        budgetMapping.setIsOptional(false);
        descriptor.addMapping(budgetMapping);

        DirectToFieldMapping totalBoxOfficeMapping = new DirectToFieldMapping();
        totalBoxOfficeMapping.setAttributeName("totalBoxOffice");
        totalBoxOfficeMapping.setFieldName("total_box_office");
        totalBoxOfficeMapping.setIsOptional(false);
        descriptor.addMapping(totalBoxOfficeMapping);

        DirectToFieldMapping mpaaRatingMapping = new DirectToFieldMapping();
        mpaaRatingMapping.setAttributeName("mpaaRating");
        mpaaRatingMapping.setFieldName("mpaa_rating");
        EnumTypeConverter mpaaConv = new EnumTypeConverter(mpaaRatingMapping, MpaaRating.class, false);
        mpaaRatingMapping.setConverter(mpaaConv);
        descriptor.addMapping(mpaaRatingMapping);

        OneToOneMapping directorMapping = new OneToOneMapping();
        directorMapping.setAttributeName("director");
        directorMapping.setReferenceClass(Person.class);
        directorMapping.addForeignKeyFieldName("director_id", "id");
        directorMapping.setIsOptional(false);
        directorMapping.setCascadeAll(true);
        directorMapping.dontUseIndirection();
        descriptor.addMapping(directorMapping);

        OneToOneMapping screenwriterMapping = new OneToOneMapping();
        screenwriterMapping.setAttributeName("screenwriter");
        screenwriterMapping.setReferenceClass(Person.class);
        screenwriterMapping.addForeignKeyFieldName("screenwriter_id", "id");
        screenwriterMapping.setCascadeAll(true);
        screenwriterMapping.dontUseIndirection();
        descriptor.addMapping(screenwriterMapping);

        OneToOneMapping operatorMapping = new OneToOneMapping();
        operatorMapping.setAttributeName("operator");
        operatorMapping.setReferenceClass(Person.class);
        operatorMapping.addForeignKeyFieldName("operator_id", "id");
        operatorMapping.dontUseIndirection();
        descriptor.addMapping(operatorMapping);

        DirectToFieldMapping lengthMapping = new DirectToFieldMapping();
        lengthMapping.setAttributeName("length");
        lengthMapping.setFieldName("length");
        descriptor.addMapping(lengthMapping);

        DirectToFieldMapping goldenPalmCountMapping = new DirectToFieldMapping();
        goldenPalmCountMapping.setAttributeName("goldenPalmCount");
        goldenPalmCountMapping.setFieldName("golden_palm_count");
        goldenPalmCountMapping.setIsOptional(false);
        descriptor.addMapping(goldenPalmCountMapping);

        DirectToFieldMapping usaBoxOfficeMapping = new DirectToFieldMapping();
        usaBoxOfficeMapping.setAttributeName("usaBoxOffice");
        usaBoxOfficeMapping.setFieldName("usa_box_office");
        usaBoxOfficeMapping.setIsOptional(false);
        descriptor.addMapping(usaBoxOfficeMapping);

        DirectToFieldMapping taglineMapping = new DirectToFieldMapping();
        taglineMapping.setAttributeName("tagline");
        taglineMapping.setFieldName("tagline");
        taglineMapping.setIsOptional(false);
        descriptor.addMapping(taglineMapping);

        DirectToFieldMapping genreMapping = new DirectToFieldMapping();
        genreMapping.setAttributeName("genre");
        genreMapping.setFieldName("genre");
        EnumTypeConverter genreConv = new EnumTypeConverter(genreMapping, MovieGenre.class, false);
        genreMapping.setConverter(genreConv);
        descriptor.addMapping(genreMapping);

        return descriptor;
    }

    @Bean
    @EntityDescriptor
    public ClassDescriptor locationDescriptor() {
        ClassDescriptor descriptor = new ClassDescriptor();
        descriptor.setJavaClass(Location.class);
        descriptor.descriptorIsAggregate();

        DirectToFieldMapping x = new DirectToFieldMapping();
        x.setAttributeName("x");
        x.setFieldName("x");
        descriptor.addMapping(x);

        DirectToFieldMapping y = new DirectToFieldMapping();
        y.setAttributeName("y");
        y.setFieldName("y");
        descriptor.addMapping(y);

        DirectToFieldMapping z = new DirectToFieldMapping();
        z.setAttributeName("z");
        z.setFieldName("z");
        descriptor.addMapping(z);

        return descriptor;
    }

    @Bean
    @EntityDescriptor
    public ClassDescriptor coordinatesDescriptor() {
        ClassDescriptor descriptor = new ClassDescriptor();
        descriptor.setJavaClass(Coordinates.class);
        descriptor.descriptorIsAggregate();

        DirectToFieldMapping xMapping = new DirectToFieldMapping();
        xMapping.setAttributeName("x");
        xMapping.setFieldName("x");
        descriptor.addMapping(xMapping);

        DirectToFieldMapping yMapping = new DirectToFieldMapping();
        yMapping.setAttributeName("y");
        yMapping.setFieldName("y");
        descriptor.addMapping(yMapping);

        return descriptor;
    }

    @Bean
    public Project project(
            @EntityDescriptor ClassDescriptor personDescriptor,
            @EntityDescriptor ClassDescriptor movieDescriptor,
            @EntityDescriptor ClassDescriptor locationDescriptor,
            @EntityDescriptor ClassDescriptor coordinatesDescriptor) {
        Project project = new Project();
        project.addDescriptor(personDescriptor);
        project.addDescriptor(movieDescriptor);
        project.addDescriptor(locationDescriptor);
        project.addDescriptor(coordinatesDescriptor);
        project.setLogin(databaseLogin);
        return project;
    }

    @Bean(destroyMethod = "logout")
    public DatabaseSession databaseSession(Project project) {
        DatabaseSessionImpl session = (DatabaseSessionImpl) project.createDatabaseSession();
        session.login();
        return session;
    }
}

