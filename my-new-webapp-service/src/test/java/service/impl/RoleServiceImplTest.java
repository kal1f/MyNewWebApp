package service.impl;

import database.dao.RoleDAO;
import database.entity.Role;
import exception.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RoleService;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @Mock
    RoleDAO rd;

    RoleService roleService;

    List<Role> roles;

    @Before
    public void setUp(){
        roleService = new RoleServiceImpl(rd);

        roles = new ArrayList<>();
        roles.add(new Role(1,"buyer"));
        roles.add(new Role(2,"admin"));

        when(rd.getRoles()).thenReturn(roles);
        when(rd.getRoleById(1)).thenReturn(new Role(1, "buyer"));
        when(rd.getRoleById(5)).thenReturn(null);
        when(rd.insertRole("developer")).thenReturn(3);
        when(rd.getRoleById(3)).thenReturn(new Role(3, "developer"));

    }

    @Test
    public void getRoles() {
        List<Role> roles = roleService.getRoles();

        verify(rd).getRoles();

        assertEquals(2, roles.size());
        assertEquals(roles.get(0), new Role(1,"buyer"));
        assertEquals(roles.get(1), new Role(2,"admin"));

    }

    @Test
    public void searchRoleByExistingIdExpectRole() throws EntityNotFoundException {
        List<Role> roles = roleService.searchRoleById(1);

        verify(rd).getRoleById(1);

        assertEquals(Integer.valueOf(1), roles.get(0).getId());
        assertEquals("buyer",roles.get(0).getName());
    }

    @Test
    public void searchRoleByNotExistingIdExpectEntityNotFoundException() {
        String message = null;

        try {
            List<Role> role = roleService.searchRoleById(5);
        } catch (EntityNotFoundException e) {
            message = e.getMessage();
        }

        verify(rd).getRoleById(5);

        assertEquals("Search role returned null", message);
    }

    @Test
    public void addNewRoleExpectRoleObject() throws EntityNotFoundException {
        Role role = roleService.addNewRole("developer");

        verify(rd).insertRole("developer");
        verify(rd).getRoleById(3);

        assertEquals(Integer.valueOf(3), role.getId());
        assertEquals("developer", role.getName());
    }

    @Test
    public void addNewRoleExpectEntityNotFoundException() {
        String message = null;
        try {
            Role role = roleService.addNewRole(null);
        } catch (EntityNotFoundException e) {
            message = e.getMessage();
        }

        verify(rd).insertRole(null);
        assertEquals("Role with id=0 is not exists", message);
    }

    @Test
    public void addExistingRoleExpectEntityNotFoundException() {
        String message = null;
        try {
            Role role = roleService.addNewRole("admin");
        } catch (EntityNotFoundException e) {
            message = e.getMessage();
        }

        verify(rd).insertRole("admin");
        assertEquals("Role with id=0 is not exists", message);
    }
}