public class Pharmacists extends Staff {
    public Pharmacists() { super(); }
    public Pharmacists(String id, String name, String sex, int salary) {
        super(id, name, sex, salary);
    }

    @Override
    public String getRole() {
        return "Pharmacist";
    }
}
