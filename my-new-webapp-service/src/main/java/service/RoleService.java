package service;

import database.entity.Role;
import exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    List<Role> searchRoleById(Integer id) throws EntityNotFoundException;
    Role updateRoleName(Role role) throws EntityNotFoundException;
    Role addNewRole(String name) throws EntityNotFoundException;
}
