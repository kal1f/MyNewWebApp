package servlet;

import binding.request.RoleRequestBinding;
import binding.request.RoleUpdateRequestBinding;
import binding.response.*;
import database.entity.Role;
import exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import service.RoleService;
import service.impl.RoleServiceImpl;
import util.DataToJson;
import util.JsonToData;
import util.validator.DataValidator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class RoleServlet extends HttpServlet {
    private DataToJson dataToJson;
    private JsonToData jsonToData;
    private DataValidator dataValidator;
    private RoleService roleService;

    static final Logger LOGGER = Logger.getLogger(RoleServlet.class);

    public RoleServlet() {
        super();
    }

    public RoleServlet(DataToJson dataToJson, JsonToData jsonToData,
                       DataValidator dataValidator, RoleService roleService) {
        this.dataToJson = dataToJson;
        this.jsonToData = jsonToData;
        this.dataValidator = dataValidator;
        this.roleService = roleService;
    }

    @Override
    public void init(){
        this.dataToJson = new DataToJson();
        this.jsonToData = new JsonToData();
        this.dataValidator = new DataValidator();
        this.roleService = new RoleServiceImpl();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        if(id == null){
            List<Role> roles = roleService.getRoles();
            dataToJson.processResponse(response,  200, new RolesResponseBinding(roles));
        }
        else if(dataValidator.isIdValid(id)){
            try {
                List<Role> roles = roleService.searchRoleById(Integer.parseInt(id));
                dataToJson.processResponse(response, 200, new RolesResponseBinding(roles));
            } catch (EntityNotFoundException e) {
                LOGGER.debug("id is not existing", e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.debug("Id is not valid");
            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400, "Id is not valid"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        RoleRequestBinding requestBinding;

        try {
            requestBinding = jsonToData.jsonToRoleData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }
        if(dataValidator.isRoleDataValid(requestBinding.getName())) {
            try {
                Role role = roleService.addNewRole(requestBinding.getName());
                LOGGER.debug("Role is created");
                dataToJson.processResponse(response, 201, new RoleResponseBinding(role));
            } catch (EntityNotFoundException e) {
                LOGGER.debug("Role is not created", e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.info("Role can not be registered, input data is not valid");

            dataToJson.processResponse(response, 400,
                    new ErrorResponseBinding(400,"Role with name "+requestBinding.getName()+" can not be registered," +
                            " input data is not valid"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        RoleUpdateRequestBinding requestBinding = null;

        try{
            requestBinding=jsonToData.jsonToRoleUpdateData(request);
        }catch (IOException e){
            LOGGER.debug(e.getMessage(), e);
            dataToJson.processResponse(response, 422, ErrorResponseBinding.ERROR_RESPONSE_422);
            return;
        }

        if(dataValidator.isRoleUpdateDataValid(requestBinding.getId(), requestBinding.getName())){
            try {
                Role p = roleService.updateRoleName(requestBinding.toEntityObject());
                dataToJson.processResponse(response, 200, new RoleResponseBinding(p));
            }catch (EntityNotFoundException e){
                LOGGER.debug("Role with id"+
                        requestBinding.getId()+
                        "was not been updated" , e);
                dataToJson.processResponse(response, 404, ErrorResponseBinding.ERROR_RESPONSE_404);
            }
        }
        else {
            LOGGER.debug("Id or name is not valid");

            dataToJson.processResponse(response, 400, new ErrorResponseBinding(400,
                    "Id or name is not valid"));
        }
    }
}
