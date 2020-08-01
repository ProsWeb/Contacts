package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Person extends Record {

    private String surname;

    private String birthDate;

    private String gender;

    void setSurname(final String surname) {
        this.surname = surname;
    }

    void setGender(final String gender) {
        this.gender = gender;
    }

    void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }

    String getSurname() {
        return surname;
    }

    String getGender() {
        return gender;
    }

    String getBirthDate() {
        return birthDate;
    }

    Person addPerson(final Scanner sc) {

        System.out.println("Enter the name:");
        setName(sc.nextLine());

        System.out.println("Enter the surname:");
        setSurname(sc.nextLine());

        System.out.println("Enter the birth date:");
        String birthDate = sc.nextLine();
        Check e = new Check();
        if (!e.checkBirthDate(birthDate)) {
            System.out.println("Bad birth date!");
            birthDate = "";
        }
        setBirthDate(birthDate);

        System.out.println("Enter the gender (M, F)");
        String gender = sc.nextLine();
        if (!e.checkGender(gender)) {
            System.out.println("Bad gender!");
            gender = "";
        }
        setGender(gender);

        System.out.println("Enter the number:");
        String number = sc.nextLine();
        if (!e.checkNumber(number)) {
            System.out.println("Wrong number format!");
            number = "";
        }
        setPhoneNumber(number);

        setCreationDate(LocalDateTime.now());
        setLastEditDate(LocalDateTime.now());

        return this;
    }

    @Override
    protected void edit(final Scanner sc) {

        System.out.println("Select a field (name, surname, birth, gender, number):");
        String fieldToEdit = sc.nextLine();

        Check e = new Check();

        switch (fieldToEdit) {
            case "name":
                System.out.println("Enter name:");
                this.setName(sc.nextLine());
                break;
            case "surname":
                System.out.println("Enter surname:");
                this.setSurname(sc.nextLine());
                break;
            case "birth":
                System.out.println("Enter birthDate:");
                String birth = sc.nextLine();
                if (e.checkBirthDate(birth)) {
                    this.setBirthDate(birth);
                } else {
                    System.out.println("Bad birth date!");
                    this.setBirthDate("");
                }
                break;
            case "gender":
                System.out.println("Enter gender:");
                String gender = sc.nextLine();
                if (e.checkGender(gender)) {
                    this.setGender(gender);
                } else {
                    System.out.println("Bad gender!");
                    this.setGender("");
                }
                break;
            case "number":
                System.out.println("Enter number:");
                String newNumber = sc.nextLine();
                if (e.checkNumber(newNumber)) {
                    this.setPhoneNumber(newNumber);
                } else {
                    System.out.println("Wrong number format!");
                    this.setPhoneNumber("");
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
        this.setLastEditDate(LocalDateTime.now());
    }

    @Override
    protected void show(final int i) {
        System.out.println(i + ". " + this.getName() + " " + this.getSurname());
    }

    @Override
    protected void showInfo() {

        String emptyData = "[no data]";

        System.out.println("Name: " + this.getName());
        System.out.println("Surname: " + this.getSurname());
        System.out.print("Birth date: ");
        System.out.println(hasAttribute("birthDate") ? this.getBirthDate() : emptyData);
        System.out.print("Gender: ");
        System.out.println(hasAttribute("gender") ? this.getGender() : emptyData);
        System.out.print("Number: ");
        System.out.println(hasAttribute("number") ? this.getPhoneNumber() : emptyData);
        System.out.println("Time created: " + this.getCreationDate());
        System.out.println("Time last edit: " + this.getLastEditDate());
    }

    @Override
    protected boolean hasAttribute(final String attributeType) {

        String emptyAttribute = "";
        boolean hasAttribute;

        switch (attributeType) {
            case "birthDate":
                hasAttribute = !this.getBirthDate().equals(emptyAttribute);
                break;
            case "gender":
                hasAttribute = !this.getGender().equals(emptyAttribute);
                break;
            case "number":
                hasAttribute = !this.getPhoneNumber().equals(emptyAttribute);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return hasAttribute;
    }

    @Override
    public String toString() {
        return  "Name: " + super.getName() + "\n"
                + "Surname: " + surname + "\n"
                + "Birth date: " + birthDate + "\n"
                + "Gender: " + gender + "\n"
                + "Number: " + super.getPhoneNumber() + "\n"
                + "Time created: " + super.getCreationDate() + "\n"
                + "Time last edit: " + super.getLastEditDate() + "\n";
    }
}
