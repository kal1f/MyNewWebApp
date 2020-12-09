package service.impl;

import database.dao.RoleDAO;
import database.dao.impl.RoleDAOImpl;
import database.entity.Role;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.RoleService;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private final RoleDAO rd;

    static final Logger LOGGER = Logger.getLogger(RoleServiceImpl.class);

    public RoleServiceImpl() {
        this.rd = new RoleDAOImpl();
    }

    public RoleServiceImpl(RoleDAO roleDAO){
        this.rd = roleDAO;
    }

    @Override
    public List<Role> getRoles() {
        return rd.getRoles();
    }

    @Override
    public List<Role> searchRoleById(Integer id) throws EntityNotFoundException {
        List<Role> roles = new ArrayList<>();
        Role role = rd.getRoleById(id);

        if(role == null){
            LOGGER.debug("rd.getRoleById(id) returned null");
            throw new EntityNotFoundException("Search role returned null");
        }
        roles.add(role);
        return roles;
    }

    @Override
    public Role updateRoleName(Role role) throws EntityNotFoundException {
        int updatedRows = rd.updateRoleById(role);

        if(updatedRows != 0){

            return rd.getRoleById(role.getId());
        }
        else {
            LOGGER.debug("updateRoleById(Role role) returned 0");
            throw new EntityNotFoundException("Role is not updated");
        }
    }

    @Override
    public Role addNewRole(String name) throws EntityNotFoundException {

        int id = rd.insertRole(name);

        if(id != 0){

            return rd.getRoleById(id);

        }
        else {
            LOGGER.debug("Role was not created in database");
            throw new EntityNotFoundException("Role with id=0 is not exists");
        }
    }
}
