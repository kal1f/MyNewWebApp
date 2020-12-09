package database.dao;

import database.entity.Role;

import java.math.BigInteger;
import java.util.List;

public interface RoleDAO {
    int updateRoleById(Role role);
    Role getRoleById(Integer id);
    List<Role> getRoles();
    int insertRole(String role);
}
