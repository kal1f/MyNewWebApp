package binding.response;

import database.entity.Role;

import java.util.Objects;

public class RoleResponseBinding implements ResponseBinding{
    private Integer id;
    private String name;

    public RoleResponseBinding(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public RoleResponseBinding(Integer id, String name) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleResponseBinding that = (RoleResponseBinding) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
