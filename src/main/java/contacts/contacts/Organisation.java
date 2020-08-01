package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Organisation extends Record {

    private String address;

    void setAddress(final String address) {
        this.address = address;
    }

    String getAddress() {
        return address;
    }

    Record addOrganisation(final Scanner sc) {

        System.out.println("Enter the organization name:");
        setName(sc.nextLine());

        System.out.println("Enter the address:");
        setAddress(sc.nextLine());

        System.out.println("Enter the number:");
        String number = sc.nextLine();

        Check e = new Check();
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

        System.out.println("Select a field (name, address, number):");
        String fieldToEdit = sc.nextLine();

        switch (fieldToEdit) {
            case "name":
                System.out.println("Enter name:");
                this.setName(sc.nextLine());
                break;
            case "address":
                System.out.println("Enter address:");
                String address = sc.nextLine();
                this.setAddress(address);
                break;
            case "number":
                System.out.println("Enter number:");
                String newNumber = sc.nextLine();
                if (new Check().checkNumber(newNumber)) {
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
        System.out.println(i + ". " + this.getName());
    }

    @Override
    protected void showInfo() {

        String name = this.getName();
        String address = this.getAddress();
        String phoneNumber = this.getPhoneNumber();
        String creationDate = this.getCreationDate();
        String lastEditDate = this.getLastEditDate();

        String emptyData = "[no data]";

        System.out.println("Organization name: " + name);
        System.out.println("Address: " + address);
        System.out.print("Number: ");
        System.out.println(super.hasAttribute("number") ? phoneNumber : emptyData);
        System.out.println("Time created: " + creationDate);
        System.out.println("Time last edit: " + lastEditDate);
    }

    @Override
    public String toString() {
        return "Organization name: " + super.getName() + "\n"
                + "Address: " + address + "\n"
                + "Number: " + super.getPhoneNumber() + "\n"
                + "Time created: " + super.getCreationDate() + "\n"
                + "Time last edit: " + super.getLastEditDate() + "\n";
    }
}
