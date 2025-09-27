package ru.itmo.se.is.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.DatabaseLogin;
import org.eclipse.persistence.sessions.Project;
import ru.itmo.se.is.annotation.EntityDescriptor;

import java.util.List;

@ApplicationScoped
@NoArgsConstructor
public class EclipseLinkProjectProvider {

    @Inject
    @EntityDescriptor
    private List<ClassDescriptor> descriptors;

    @Inject
    private DatabaseLogin databaseLogin;

    @Produces
    @ApplicationScoped
    public Project createProject() {
        Project project = new Project();
        descriptors.forEach(project::addDescriptor);
        project.setLogin(databaseLogin);
        return project;
    }
}
