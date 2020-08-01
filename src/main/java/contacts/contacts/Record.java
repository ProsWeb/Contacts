package contacts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public abstract class Record {

    private String name;

    private String phoneNumber = "";

    private String creationDate;

    private String lastEditDate;

    public void setName(final String name) {
        this.name = name;
    }

    protected void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    protected void setCreationDate(final LocalDateTime creationDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        this.creationDate = creationDate.format(dtf);
    }

    protected void setLastEditDate(final LocalDateTime lastEditDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        this.lastEditDate = lastEditDate.format(dtf);
    }

    protected String getName() {
        return name;
    }

    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected String getCreationDate() {
        return creationDate;
    }

    protected String getLastEditDate() {
        return lastEditDate;
    }

    protected boolean hasAttribute(final String attributeType) {

        String emptyAttribute = "";
        boolean hasAttribute = false;

        if ("number".equals(attributeType)) {
            hasAttribute = !this.getPhoneNumber().equals(emptyAttribute);
        }

        return hasAttribute;
    }

    protected abstract void show(int i);

    protected abstract void showInfo();

    protected abstract void edit(Scanner sc);

    public abstract String toString();
}
