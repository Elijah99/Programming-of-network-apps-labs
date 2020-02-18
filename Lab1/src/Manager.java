import java.io.Serializable;

public class Manager implements Serializable
{
    String surname;
    int age;
    Boolean cope;

    public Manager(String surname, int age, boolean cope) {
        this.surname = surname;
        this.age = age;
        this.cope = cope;
    }
    public Manager()
    {}

    public String ifCope(boolean b)
    {
        if(b) return "Справляется обязанностями";
        else return "Не справляется с обязанностями";
    }

    @Override
    public String toString() {
        return "Фамилия: " + surname + ", Возраст: " + age + ", " + ifCope(cope);
    }
}
