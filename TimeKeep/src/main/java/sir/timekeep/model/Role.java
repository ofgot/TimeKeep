package sir.timekeep.model;

public enum Role {
    USUAL("ROLE_USUAL"), PREMIUM("ROLE_PREMIUM");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
