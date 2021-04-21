//TODO Тут нам может потребоваться JSONAware. Кстати, для его работы должен быть
// определен соответствующий тусринг. если добавить интерфейс сюда, то идея сама это скажет

import org.json.simple.JSONAware;

public class Employee implements JSONAware {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {
        // Пустой конструктор
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public String toJSONString() {
        return null;
    }
}