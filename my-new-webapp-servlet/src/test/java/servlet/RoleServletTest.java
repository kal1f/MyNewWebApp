package servlet;

import binding.request.RoleRequestBinding;
import binding.request.RoleUpdateRequestBinding;
import binding.response.*;
import database.entity.Role;
import exception.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.RoleService;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleServletTest {
        @Mock
        JsonToData jsonToData;
        @Mock
        DataToJson dataToJson;
        @Mock
        HttpServletRequest request;
        @Mock
        HttpServletResponse response;
        @Mock
        RoleService roleService;
        @Mock
        DataValidator dataValidator;
        @Mock
        RoleUpdateRequestBinding roleUpdateRequestBinding;
        @Mock
        RoleRequestBinding roleRequestBinding;

        RoleServlet servlet;

        @Before
        public void setUp() throws IOException {

            servlet = new RoleServlet(dataToJson, jsonToData, dataValidator, roleService);
            when(jsonToData.jsonToRoleUpdateData(request)).thenReturn(roleUpdateRequestBinding);
            when(jsonToData.jsonToRoleData(request)).thenReturn(roleRequestBinding);

            when(dataValidator.isIdValid("2124123")).thenReturn(false);
            when(dataValidator.isIdValid("1")).thenReturn(true);
            when(dataValidator.isIdValid("3")).thenReturn(true);

            when(roleUpdateRequestBinding.getId()).thenReturn(2);
            when(roleUpdateRequestBinding.getName()).thenReturn("admin");

            doNothing().when(dataToJson).processResponse(Matchers.any(HttpServletResponse.class), anyInt(), Matchers.any(ResponseBinding.class));

            when(roleRequestBinding.getName()).thenReturn("admin");

        }

        @After
        public void clean(){
            reset(dataToJson, request, response, roleService, dataValidator);
        }

        @Test
        public void doPutIOExceptionExpectStatus422() throws IOException {

            doThrow(new IOException()).when(jsonToData).jsonToRoleUpdateData(request);

            servlet.doPut(request, response);

            verify(dataToJson).processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);

        }

        @Test
        public void doPutValidInputDataExpectStatus404() throws EntityNotFoundException {

            when(roleUpdateRequestBinding.getId()).thenReturn(1);
            when(dataValidator.isRoleUpdateDataValid(roleUpdateRequestBinding.getId(), roleUpdateRequestBinding.getName())).thenReturn(true);

            doThrow(new EntityNotFoundException()).when(roleService).updateRoleName(Matchers.any(Role.class));

            servlet.doPut(request, response);

            verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

        }

        @Test
        public void doPutValidDataExpectStatus200() throws IOException, EntityNotFoundException {
            when(jsonToData.jsonToRoleUpdateData(request)).thenReturn(roleUpdateRequestBinding);

            when(roleUpdateRequestBinding.getId()).thenReturn(1);
            when(dataValidator.isRoleUpdateDataValid(roleUpdateRequestBinding.getId(), roleUpdateRequestBinding.getName())).thenReturn(true);

            Role p  = new Role(1,"admin");

            when(roleService.updateRoleName(Matchers.any(Role.class))).thenReturn(p);

            servlet.doPut(request, response);

            verify(dataToJson).processResponse(response, 200, new RoleResponseBinding(p));

        }

        @Test
        public void doPutIdNullDataExpectStatus400(){

            when(roleUpdateRequestBinding.getId()).thenReturn(null);
            when(dataValidator.isRoleUpdateDataValid(roleUpdateRequestBinding.getId(), roleUpdateRequestBinding.getName())).thenReturn(false);


            servlet.doPut(request, response);

            verify(dataToJson).processResponse(response, 400,
                    new ErrorResponseBinding(400, "Id or name is not valid"));

        }

        @Test
        public void doGetIdNullExpectStatus200(){

            when(request.getParameter("id")).thenReturn(null);
            List<Role> r = new ArrayList<>();

            r.add(new Role(1, "admin"));

            when(roleService.getRoles()).thenReturn(r);

            servlet.doGet(request, response);

            verify(dataToJson).processResponse(response, 200, new RolesResponseBinding(r));
        }

        @Test
        public void doGetIdValidAndRoleNotExistsExpectStatus404() throws EntityNotFoundException{

            when(request.getParameter("id")).thenReturn("3");

            doThrow(new EntityNotFoundException()).when(roleService).searchRoleById(Matchers.anyInt());

            servlet.doGet(request, response);

            verify(dataToJson).processResponse(response, 404,  ErrorResponseBinding.ERROR_RESPONSE_404);

        }

        @Test
        public void doGetIdValidAndRoleExistsExpectStatus200() throws EntityNotFoundException{
            when(request.getParameter("id")).thenReturn("1");

            List<Role> c = new ArrayList<>();

            c.add(new Role(1, "buyer"));

            when(roleService.searchRoleById(anyInt())).thenReturn(c);

            servlet.doGet(request, response);

            verify(dataToJson).processResponse(response, 200, new RolesResponseBinding(c));

        }

        @Test
        public void doGetIdNotValidExpectStatus400() {
            when(request.getParameter("id")).thenReturn("");

            servlet.doGet(request, response);

            verify(dataToJson, times(1)).processResponse(response, 400,
                    new ErrorResponseBinding(400, "Id is not valid"));

        }

        @Test
        public void doPostIOExceptionExpectStatus422() throws IOException {


            doThrow(new IOException()).when(jsonToData).jsonToRoleData(request);

            servlet.doPost(request, response);

            verify(dataToJson).processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);

        }

        @Test
        public void doPostProductDataIsValidAndRoleExistsExpectStatus201() throws EntityNotFoundException, IOException {

            when(dataValidator.isRoleDataValid(roleRequestBinding.getName())).thenReturn(true);

            Role role = new Role();

            role.setId(1);
            role.setName("buyer");

            when(roleService.addNewRole(roleRequestBinding.getName())).thenReturn(role);

            servlet.doPost(request, response);


            verify(dataToJson).processResponse(response, 201, new RoleResponseBinding(role));
        }

        @Test
        public void doPostProductDataIsValidAndProductNotExistsExpectStatus404() throws EntityNotFoundException, IOException {

            when(dataValidator.isRoleDataValid(roleRequestBinding.getName())).thenReturn(true);

            doThrow(new EntityNotFoundException()).when(roleService).addNewRole(anyString());

            servlet.doPost(request, response);

            verify(dataToJson).processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
        }

        @Test
        public void doPostProductDataIsNotValidThanExpectStatus400() throws IOException {
            when(jsonToData.jsonToRoleData(request)).thenReturn(roleRequestBinding);

            when(roleRequestBinding.getName()).thenReturn("admin");

            when(dataValidator.isRoleDataValid(roleRequestBinding.getName())).thenReturn(false);

            servlet.doPost(request, response);

            verify(dataToJson).processResponse(response, 400,new ErrorResponseBinding(400,
                    "Role with id can not be registered," +
                            " input data is not valid"));


        }
}
