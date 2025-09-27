package ru.itmo.se.is.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import ru.itmo.se.is.annotation.EntityDescriptor;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DescriptorListProducer {

    @Inject
    @EntityDescriptor
    private Instance<ClassDescriptor> descriptorInstances;

    @Produces
    @ApplicationScoped
    @EntityDescriptor
    public List<ClassDescriptor> produceDescriptorList() {
        return descriptorInstances.stream().collect(Collectors.toList());
    }
}
