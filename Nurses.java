public class Nurses extends Staff {
    public Nurses() { super(); }
    public Nurses(String id, String name, String sex, int salary) {
        super(id, name, sex, salary);
    }

    @Override
    public String getRole() {
        return "Nurse";
    }
}