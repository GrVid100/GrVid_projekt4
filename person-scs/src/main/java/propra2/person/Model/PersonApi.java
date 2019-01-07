package propra2.person.Model;

public class PersonApi {
    private Long id;
    private String vorname;
    private String nachname;
    private String rolle;
    private String kontakt;
    private String[] skills;
    private Long[] projekteId;

    public PersonApi(Person person) {
        this.id = person.getId();
        this.vorname = person.getVorname();
        this.nachname = person.getNachname();
        this.rolle = person.getRolle();
        this.kontakt = person.getKontakt();
        this.skills = person.getSkills();
        this.projekteId = person.getProjekteId();
    }
}
