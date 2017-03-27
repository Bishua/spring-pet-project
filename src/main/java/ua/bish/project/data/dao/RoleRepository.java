package ua.bish.project.data.dao;

import org.springframework.data.repository.CrudRepository;
import ua.bish.project.data.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
