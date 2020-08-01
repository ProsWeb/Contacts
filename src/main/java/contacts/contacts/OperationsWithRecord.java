package contacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class OperationsWithRecord {

    void showList(final List<Record> list) {

        int i = 1;
        for (Record r : list) {
            r.show(i);
            i++;
        }
    }

    void showAllAttributes(final String query, final List<Record> recordList) {

        int recordNumberToShow = Integer.parseInt(query);
        Record r = recordList.get(recordNumberToShow - 1);
        r.showInfo();
    }

    void saveToFile(final List<Record> recordList, final String path) {

        File contactList = new File(path);
        try (FileWriter writer = new FileWriter(contactList)) {
            recordList.forEach(record -> {
                try {
                    writer.write(record.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }

    List<Record> loadFromFile(final List<Record> recordList, final String path)
            throws FileNotFoundException {

        File fileWithPurchases = new File(path);
        try (Scanner sc = new Scanner(fileWithPurchases)) {
            while (sc.hasNext()) {
                String line = sc.nextLine();

                if (line.contains("Name:")) {
                    Person p = new Person();
                    String name = line.substring(line.indexOf(": ") + 2);
                    p.setName(name);
                    String surName = sc.nextLine();
                    p.setSurname(surName.substring(surName.indexOf(": ") + 2));
                    String birthDate = sc.nextLine();
                    p.setBirthDate(birthDate.substring(birthDate.indexOf(": ") + 2));
                    String gender = sc.nextLine();
                    p.setGender(gender.substring(gender.indexOf(": ") + 2));
                    String number = sc.nextLine();
                    p.setPhoneNumber(number.substring(number.indexOf(": ") + 2));
                    String timeCreated = sc.nextLine();
                    p.setCreationDate(LocalDateTime.parse(timeCreated.substring(timeCreated.indexOf(": ") + 2)));
                    String timeLastEdit = sc.nextLine();
                    p.setLastEditDate(LocalDateTime.parse(timeLastEdit.substring(timeLastEdit.indexOf(": ") + 2)));

                    recordList.add(p);
                }

                if (line.contains("Organization name:")) {
                    Organisation o = new Organisation();
                    String name = line.substring(line.indexOf(": ") + 2);
                    o.setName(name);
                    String address = sc.nextLine();
                    o.setAddress(address.substring(address.indexOf(": ") + 2));
                    String number = sc.nextLine();
                    o.setPhoneNumber(number.substring(number.indexOf(": ") + 2));
                    String timeCreated = sc.nextLine();
                    o.setCreationDate(LocalDateTime.parse(timeCreated.substring(timeCreated.indexOf(": ") + 2)));
                    String timeLastEdit = sc.nextLine();
                    o.setLastEditDate(LocalDateTime.parse(timeLastEdit.substring(timeLastEdit.indexOf(": ") + 2)));

                    recordList.add(o);
                }
            }
        }

        return recordList;
    }

    String changeRecord(final Scanner sc, final  Map<Integer, Record> queryResult,
                        final List<Record> recordList, final String numToChange,
                        final OperationsWithRecord operationsWithRecord, final String pathToFile) {

        System.out.print("\n[record] Enter action (edit, delete, menu):");
        String query = sc.nextLine();

        int recordNumberToChange = Integer.parseInt(numToChange) - 1;
        int i = 0;
        int recordNumberInRecordList = -1;
        for (Map.Entry<Integer, Record> entry : queryResult.entrySet()) {
            if (i == recordNumberToChange) {
                recordNumberInRecordList = entry.getKey();
            }
            i++;
        }

        switch (query) {
            case "edit":
                Record r = recordList.get(recordNumberInRecordList);
                r.edit(sc);
                if (!pathToFile.isEmpty()) {
                    operationsWithRecord.saveToFile(recordList, pathToFile);
                }
                System.out.println("Saved");
                r.showInfo();
                changeRecord(sc, queryResult, recordList,
                        numToChange, operationsWithRecord, pathToFile);
                break;
            case "delete":
                recordList.remove(recordNumberToChange);
                if (!pathToFile.isEmpty()) {
                    operationsWithRecord.saveToFile(recordList, pathToFile);
                }
                System.out.println("Deleted");
                break;
            default:
                break;
        }
        return query;
    }

    String search(final Scanner sc, final List<Record> recordList, final String path) {
        System.out.println("Enter search query:");
        String result = "";
        String query = sc.nextLine();
        Map<Integer, Record> queryResult = fillQueryResult(recordList, query);

        List<Record> queryRecords = new ArrayList<>(queryResult.values());
        System.out.println("Found " +  queryRecords.size() + " results:");
        OperationsWithRecord operationsWithRecord = new OperationsWithRecord();
        operationsWithRecord.showList(queryRecords);

        System.out.println("\n[search] Enter action ([number], back, again):");
        String choice = sc.nextLine();
        switch (choice) {
            case "back":
                result = choice;
                break;
            case "again":
                search(sc, recordList, path);
                break;
            default:
                operationsWithRecord.showAllAttributes(choice, queryRecords);
                result = operationsWithRecord.changeRecord(sc, queryResult, recordList, choice, operationsWithRecord, path);
        }
        return result;
    }

    Map<Integer, Record> fillQueryResult(final List<Record> recordList, final String searchQuery) {

        Map<Integer, Record> queryResult = new HashMap<>();
        int indexInRecordList = 0;
        for (Record r: recordList) {
            String stringRecord = r.toString();
            if (stringRecord.toLowerCase().contains(searchQuery.toLowerCase())) {
                queryResult.put(indexInRecordList, r);
            }
            indexInRecordList++;
        }

        return queryResult;
    }
}
