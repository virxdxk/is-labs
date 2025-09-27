package ru.itmo.se.is.db.descriptor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.RelationalDescriptor;
import org.eclipse.persistence.mappings.AggregateObjectMapping;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.mappings.converters.EnumTypeConverter;
import ru.itmo.se.is.annotation.EntityDescriptor;
import ru.itmo.se.is.entity.Location;
import ru.itmo.se.is.entity.Person;
import ru.itmo.se.is.entity.value.Color;
import ru.itmo.se.is.entity.value.Country;

@ApplicationScoped
public class PersonDescriptorProvider {

    @Produces
    @ApplicationScoped
    @EntityDescriptor
    public ClassDescriptor createPersonDescriptor() {
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

        DirectToFieldMapping personName = new DirectToFieldMapping();
        personName.setAttributeName("name");
        personName.setFieldName("name");
        descriptor.addMapping(personName);

        DirectToFieldMapping eyeColorMap = new DirectToFieldMapping();
        eyeColorMap.setAttributeName("eyeColor");
        eyeColorMap.setFieldName("eye_color");
        EnumTypeConverter eyeConv = new EnumTypeConverter(eyeColorMap, Color.class, false);
        eyeColorMap.setConverter(eyeConv);
        descriptor.addMapping(eyeColorMap);

        DirectToFieldMapping hairColorMap = new DirectToFieldMapping();
        hairColorMap.setAttributeName("hairColor");
        hairColorMap.setFieldName("hair_color");
        hairColorMap.setIsOptional(false);
        EnumTypeConverter hairConv = new EnumTypeConverter(hairColorMap, Color.class, false);
        hairColorMap.setConverter(hairConv);
        descriptor.addMapping(hairColorMap);

        AggregateObjectMapping locationMapping = new AggregateObjectMapping();
        locationMapping.setAttributeName("location");
        locationMapping.setReferenceClass(Location.class);
        locationMapping.addFieldNameTranslation("location_x", "x");
        locationMapping.addFieldNameTranslation("location_y", "y");
        locationMapping.addFieldNameTranslation("location_z", "z");
        descriptor.addMapping(locationMapping);

        DirectToFieldMapping weight = new DirectToFieldMapping();
        weight.setAttributeName("weight");
        weight.setFieldName("weight");
        descriptor.addMapping(weight);

        DirectToFieldMapping natMap = new DirectToFieldMapping();
        natMap.setAttributeName("nationality");
        natMap.setFieldName("nationality");
        EnumTypeConverter natConv = new EnumTypeConverter(natMap, Country.class, false);
        natMap.setConverter(natConv);
        descriptor.addMapping(natMap);

        return descriptor;
    }
}
