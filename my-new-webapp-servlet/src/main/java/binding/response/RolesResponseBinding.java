package binding.response;

import database.entity.Role;

import java.util.List;
import java.util.Objects;

public class RolesResponseBinding implements ResponseBinding {
    private List<Role> roles;

    public RolesResponseBinding(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setTransactions(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesResponseBinding that = (RolesResponseBinding) o;
        return Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roles);
    }
}
