package course.spring.restmvc.model;

public enum Role {
    ADMINISTRATOR("Administrator"),
    BLOGGER("Blogger");

    Role(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
