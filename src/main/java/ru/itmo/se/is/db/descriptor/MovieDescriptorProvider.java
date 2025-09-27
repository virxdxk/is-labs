package ru.itmo.se.is.db.descriptor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.RelationalDescriptor;
import org.eclipse.persistence.mappings.*;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.mappings.converters.EnumTypeConverter;
import org.eclipse.persistence.sessions.Session;
import ru.itmo.se.is.annotation.EntityDescriptor;
import ru.itmo.se.is.entity.Coordinates;
import ru.itmo.se.is.entity.Movie;
import ru.itmo.se.is.entity.Person;
import ru.itmo.se.is.entity.value.MovieGenre;
import ru.itmo.se.is.entity.value.MpaaRating;

import java.time.ZonedDateTime;

@ApplicationScoped
public class MovieDescriptorProvider {

    @Produces
    @ApplicationScoped
    @EntityDescriptor
    public ClassDescriptor createMovieDescriptor() {
        RelationalDescriptor movieDesc = new RelationalDescriptor();
        movieDesc.setJavaClass(Movie.class);
        movieDesc.setTableName("movie");
        movieDesc.addPrimaryKeyFieldName("id");
        movieDesc.setSequenceNumberFieldName("id");
        movieDesc.setSequenceNumberName("movie_id_seq");

        DirectToFieldMapping movieId = new DirectToFieldMapping();
        movieId.setAttributeName("id");
        movieId.setFieldName("id");
        movieId.setIsPrimaryKeyMapping(true);
        movieDesc.addMapping(movieId);

        DirectToFieldMapping movieName = new DirectToFieldMapping();
        movieName.setAttributeName("name");
        movieName.setFieldName("name");
        movieName.setIsOptional(false);
        movieDesc.addMapping(movieName);

        AggregateObjectMapping coordsMapping = new AggregateObjectMapping();
        coordsMapping.setAttributeName("coordinates");
        coordsMapping.setReferenceClass(Coordinates.class);
        coordsMapping.addFieldNameTranslation("coordinates_x", "x");
        coordsMapping.addFieldNameTranslation("coordinates_y", "y");
        movieDesc.addMapping(coordsMapping);

        DirectToFieldMapping creationDate = new DirectToFieldMapping();
        creationDate.setAttributeName("creationDate");
        creationDate.setFieldName("creation_data");
        creationDate.setIsOptional(false);
        creationDate.setConverter(new Converter() {
            @Override
            public Object convertObjectValueToDataValue(Object objectValue, Session session) {
                if (objectValue == null) return null;
                ZonedDateTime zdt = (ZonedDateTime) objectValue;
                return java.sql.Timestamp.from(zdt.toInstant());
            }

            @Override
            public Object convertDataValueToObjectValue(Object dataValue, Session session) {
                if (dataValue == null) return null;
                java.sql.Timestamp ts = (java.sql.Timestamp) dataValue;
                return ts.toInstant().atZone(java.time.ZoneId.systemDefault());
            }

            @Override
            public boolean isMutable() {
                return false;
            }

            @Override
            public void initialize(DatabaseMapping mapping, Session session) {}
        });
        movieDesc.addMapping(creationDate);

        DirectToFieldMapping oscars = new DirectToFieldMapping();
        oscars.setAttributeName("oscarsCount");
        oscars.setFieldName("oscars_count");
        oscars.setIsOptional(false);
        movieDesc.addMapping(oscars);

        DirectToFieldMapping budget = new DirectToFieldMapping();
        budget.setAttributeName("budget");
        budget.setFieldName("budget");
        budget.setIsOptional(false);
        movieDesc.addMapping(budget);

        DirectToFieldMapping totalBoxOffice = new DirectToFieldMapping();
        totalBoxOffice.setAttributeName("totalBoxOffice");
        totalBoxOffice.setFieldName("total_box_office");
        totalBoxOffice.setIsOptional(false);
        movieDesc.addMapping(totalBoxOffice);

        DirectToFieldMapping mpaaMap = new DirectToFieldMapping();
        mpaaMap.setAttributeName("mpaaRating");
        mpaaMap.setFieldName("mpaa_rating");
        EnumTypeConverter mpaaConv = new EnumTypeConverter(mpaaMap, MpaaRating.class, false);
        mpaaMap.setConverter(mpaaConv);
        movieDesc.addMapping(mpaaMap);

        OneToOneMapping directorMap = new OneToOneMapping();
        directorMap.setAttributeName("director");
        directorMap.setReferenceClass(Person.class);
        directorMap.addForeignKeyFieldName("director_id", "id");
        directorMap.setIsOptional(false);
        directorMap.setCascadeAll(true);
        directorMap.dontUseIndirection();
        movieDesc.addMapping(directorMap);

        OneToOneMapping screenwriterMap = new OneToOneMapping();
        screenwriterMap.setAttributeName("screenwriter");
        screenwriterMap.setReferenceClass(Person.class);
        screenwriterMap.addForeignKeyFieldName("screenwriter_id", "id");
        screenwriterMap.setCascadeAll(true);
        screenwriterMap.dontUseIndirection();
        movieDesc.addMapping(screenwriterMap);

        OneToOneMapping operatorMap = new OneToOneMapping();
        operatorMap.setAttributeName("operator");
        operatorMap.setReferenceClass(Person.class);
        operatorMap.addForeignKeyFieldName("operator_id", "id");
        operatorMap.dontUseIndirection();
        movieDesc.addMapping(operatorMap);

        DirectToFieldMapping length = new DirectToFieldMapping();
        length.setAttributeName("length");
        length.setFieldName("length");
        movieDesc.addMapping(length);

        DirectToFieldMapping gpc = new DirectToFieldMapping();
        gpc.setAttributeName("goldenPalmCount");
        gpc.setFieldName("golden_palm_count");
        gpc.setIsOptional(false);
        movieDesc.addMapping(gpc);

        DirectToFieldMapping usaBox = new DirectToFieldMapping();
        usaBox.setAttributeName("usaBoxOffice");
        usaBox.setFieldName("usa_box_office");
        usaBox.setIsOptional(false);
        movieDesc.addMapping(usaBox);

        DirectToFieldMapping tagline = new DirectToFieldMapping();
        tagline.setAttributeName("tagline");
        tagline.setFieldName("tagline");
        tagline.setIsOptional(false);
        movieDesc.addMapping(tagline);

        DirectToFieldMapping genreMap = new DirectToFieldMapping();
        genreMap.setAttributeName("genre");
        genreMap.setFieldName("genre");
        EnumTypeConverter genreConv = new EnumTypeConverter(genreMap, MovieGenre.class, false);
        genreMap.setConverter(genreConv);
        movieDesc.addMapping(genreMap);

        return movieDesc;
    }
}
