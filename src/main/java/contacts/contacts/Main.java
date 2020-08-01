package contacts;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        List<Record> recordList = new ArrayList<>();
        OperationsWithRecord operations = new OperationsWithRecord();

        String pathToFile = "";
        if (args.length > 0) {
            pathToFile = args[0];
            recordList = operations.loadFromFile(recordList, pathToFile);
            System.out.println("open " + args[0]);
        }
        //args.length == 0 ? "/home/snake/Downloads/phonebook.db" :
        Scanner sc = new Scanner(System.in);
        new Main().mainMenu(recordList, operations, pathToFile, sc);
    }

    private void mainMenu(List<Record> recordList,
                          final OperationsWithRecord operations,
                          final String pathToFile, final Scanner sc)
            throws FileNotFoundException {

        boolean recording = true;
        while (recording) {
            System.out.println("\n[menu] Enter action (add, list, search, count, exit):");
            String action = sc.nextLine();
            switch (action) {
                case "add":
                    System.out.println("Enter the type (person, organization):");
                    String recordType = sc.nextLine();

                    if (recordType.equals("person")) {
                        Person person = new Person();
                        Record record = person.addPerson(sc);
                        recordList.add(record);
                    }
                    if (recordType.equals("organization")) {
                        Organisation organisation = new Organisation();
                        Record record = organisation.addOrganisation(sc);
                        recordList.add(record);
                    }
                    if (!pathToFile.isEmpty()) {
                        operations.saveToFile(recordList, pathToFile);
                    }
                    System.out.println("The record added.\n");
                    break;
                case "list":
                    if (recordList.isEmpty()) {
                        System.out.println("No records to show!\n");
                        break;
                    }
                    if (!pathToFile.isEmpty()) {
                        recordList.clear();
                        recordList = operations.loadFromFile(recordList, pathToFile);
                    }
                    operations.showList(recordList);
                    System.out.println("\n[list] Enter action ([number], back):");
                    String query = sc.nextLine();
                    if (query.equals("back")) {
                        mainMenu(recordList, operations, pathToFile, sc);
                    }
                    operations.showAllAttributes(query, recordList);

                    Map<Integer, Record> queryResult = operations.fillQueryResult(recordList, query);
                    operations.changeRecord(sc, queryResult, recordList,
                            query, operations, pathToFile);
                    break;
                case "search":
                    String result = operations.search(sc, recordList, pathToFile);
                    if (result.equals("back")) {
                        mainMenu(recordList, operations, pathToFile, sc);
                    }
                    break;
                case "count":
                    int recordListSize = recordList.size();
                    System.out.println("The Phone Book has " + recordListSize + " records.\n");
                    break;
                case "exit":
                    recording = false;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.\n");
            }
        }
    }
}
