public class Security extends Staff {
    public Security() { super(); }
    public Security(String id, String name, String sex, int salary) {
        super(id, name, sex, salary);
    }

    @Override
    public String getRole() {
        return "Security";
    }
}
