package binding.request;

public class RoleRequestBinding implements RequestBinding {

    private String name;

    public RoleRequestBinding(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object toEntityObject() {
        return null;
    }
}
