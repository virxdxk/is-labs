package ru.itmo.se.is.db.descriptor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import ru.itmo.se.is.annotation.EntityDescriptor;
import ru.itmo.se.is.entity.Coordinates;

@ApplicationScoped
public class CoordinatesDescriptorProvider {

    @Produces
    @ApplicationScoped
    @EntityDescriptor
    public ClassDescriptor createCoordinatesDescriptor() {
        ClassDescriptor coordsDesc = new ClassDescriptor();
        coordsDesc.setJavaClass(Coordinates.class);
        coordsDesc.descriptorIsAggregate();

        DirectToFieldMapping coordsX = new DirectToFieldMapping();
        coordsX.setAttributeName("x");
        coordsX.setFieldName("x");
        coordsDesc.addMapping(coordsX);

        DirectToFieldMapping coordsY = new DirectToFieldMapping();
        coordsY.setAttributeName("y");
        coordsY.setFieldName("y");
        coordsDesc.addMapping(coordsY);

        return coordsDesc;
    }
}
