package binding.request;

import database.entity.Role;

public class RoleUpdateRequestBinding implements RequestBinding{

    private Integer id;
    private String name;

    public RoleUpdateRequestBinding(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Role toEntityObject() {
        return new Role(id, name);
    }
}
