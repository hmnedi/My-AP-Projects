public class University {
    private final String name;
    private final String type;
    private final String address;

    public University(String name, String type, String address) {
        this.name = name;
        this.type = type;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }
}
