public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String userName;

    protected String password;

    protected User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    protected abstract void registerUser();
    protected abstract void editUser();
}
