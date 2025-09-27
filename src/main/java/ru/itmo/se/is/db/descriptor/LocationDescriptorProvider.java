package ru.itmo.se.is.db.descriptor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import ru.itmo.se.is.annotation.EntityDescriptor;
import ru.itmo.se.is.entity.Location;

@ApplicationScoped
public class LocationDescriptorProvider {

    @Produces
    @ApplicationScoped
    @EntityDescriptor
    public ClassDescriptor createLocationDescriptor() {
        ClassDescriptor locationDesc = new ClassDescriptor();
        locationDesc.setJavaClass(Location.class);
        locationDesc.descriptorIsAggregate();

        DirectToFieldMapping locX = new DirectToFieldMapping();
        locX.setAttributeName("x");
        locX.setFieldName("x");
        locationDesc.addMapping(locX);

        DirectToFieldMapping locY = new DirectToFieldMapping();
        locY.setAttributeName("y");
        locY.setFieldName("y");
        locationDesc.addMapping(locY);

        DirectToFieldMapping locZ = new DirectToFieldMapping();
        locZ.setAttributeName("z");
        locZ.setFieldName("z");
        locationDesc.addMapping(locZ);

        return locationDesc;
    }
}
