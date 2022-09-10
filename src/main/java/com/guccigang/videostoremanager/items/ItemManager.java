package com.guccigang.videostoremanager.items;

import java.util.ArrayList;
import java.util.Stack;

import com.guccigang.videostoremanager.auth.Account;
import com.guccigang.videostoremanager.core.Constants;
import com.guccigang.videostoremanager.core.Manager;
import com.guccigang.videostoremanager.errors.AccountException;
import com.guccigang.videostoremanager.errors.ItemException;
import com.guccigang.videostoremanager.utils.IOHelper;
import com.guccigang.videostoremanager.utils.ItemIO;
import com.guccigang.videostoremanager.utils.ItemUtils;
import com.guccigang.videostoremanager.utils.Utilities;

import java.util.Comparator;

public class ItemManager extends Manager<Item> {
    public void initialize() {
        // Initialize item I/O helper
        var itemIO = new ItemIO(this, new ItemUtils());
        var fileName = Constants.getItemsFileName();

        // Set the manager's I/O helper and file name to operate on
        setIO(itemIO, fileName);

        // Load the items from the local storage
        load();
    }

    public void stop() {
        // Save the items to the local storage
        save();
    }

    public String[] getInformation() {
        String[] info = new String[7];
        Stack<String> questionList = new Stack<>();

        questionList.push("Please enter id: ");
        questionList.push("Please enter title: ");
        questionList.push(
                "Please enter rental type:\n1. Record\n2. DVD\n3. Video Game\nSelect either number [1], [2], or [3]: ");
        questionList.push("Please enter loan type:\n1. 2-day loan\n2. 1-week loan\nSelect either number [1] or [2]: ");
        questionList.push("Please enter number of copies: ");
        questionList.push("Please enter rental fee: ");

        int n = 2;
        while (true) {
            // ask the item typed first
            // based on the type, different questions will be asked
            info[n] = Utilities.askInfo(questionList.get(n));
            switch (info[n]) {
                case "1":
                    info[n] = "RECORD";
                    break;
                case "2":
                    info[n] = "DVD";
                    break;
                case "3":
                    info[n] = "GAME";
                    break;
                default:
                    continue;
            }
            break;
        }

        if (info[n].equalsIgnoreCase("Game")) // if new item is not game then extra question for genre will be asked
        {
            n = 7;
            questionList.push(
                    "Please enter genre:\n1. Action\n2. Horror\n3. Drama\n4. Comedy\nSelect either number [1],[2], [3] or [4]: ");
        } else // otherwise only 6 question will be asked
        {
            n = 6;
            info[6] = ""; // item is game -> default genre is non-genre
        }
        String[] result = new String[info.length];
        for (int i = 0; i < n; i++) // loop through the question number, and record the answer
        {
            if (i == 3) // when getting to loan type question asked, valid input should be entered
            {
                while (true) {
                    info[i] = Utilities.askInfo(questionList.get(i));
                    if (info[i].equals("1"))
                        info[i] = "TWO_DAY";
                    else if (info[i].equals("2"))
                        info[i] = "ONE_WEEK";
                    else
                        continue;
                    break;
                }
            } else if (i == 6) {
                while (true) {
                    info[i] = Utilities.askInfo(questionList.get(i));
                    switch (info[i]) {
                        case "1":
                            info[i] = "ACTION";
                            break;
                        case "2":
                            info[i] = "HORROR";
                            break;
                        case "3":
                            info[i] = "DRAMA";
                            break;
                        case "4":
                            info[i] = "COMEDY";
                            break;
                        default:
                            continue;
                    }
                    break;
                }
            } else if (i != 2)
                info[i] = Utilities.askInfo(questionList.get(i));
            result[i] = info[i]; // after all the answer are validated push it back to result
        }

        return result;

    }

    public void addItem() {
        try {
            this.addItem(getInformation());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addItem(Item item) {
        try {
            if (!this.isUnique(item.getId().substring(1, 4)))
                throw new ItemException("ID " + item.getId() + " already exists in the database.");
            else {
                add(item);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addItem(String[] data) {
        try {
            if (!this.isUnique(data[0].substring(1, 4)))
                throw new ItemException("ID " + data[0].substring(0, 4) + " already exists in the database.");
            else {
                add(new Item(data[0], data[1], data[2], data[3], data[6], Integer.parseInt(data[4]),
                        Double.parseDouble(data[5])));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean updateItem() {

        Item temp = this.getInfoToSearch();
        if (temp == null) {
            System.out.println("No item found in the database.");
            return false;
        }
        System.out.println("What do you want to modify?");
        System.out.println("Enter an option below: ");
        System.out.println("1: Modify title");
        System.out.println("2: Modify loan type");
        System.out.println("3: Increase number of copies");
        System.out.println("4: Modify rental fee");

        System.out.print("Enter the selection: ");

        var sc = IOHelper.getScanner();
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                return this.modifyTitle(temp);
            case 2:
                return this.modifyLoanType(temp);
            case 3:
                return this.increaseNumCopy(temp);
            case 4:
                return this.modifyRentalFee(temp);
            default:
                System.out.println("Invalid Selection.");
                return false;
        }
    }

    public boolean modifyTitle(Item item) {
        System.out.println("Please enter the new title: ");

        var sc = IOHelper.getScanner();
        String newTitle = sc.nextLine();

        if (!item.setTitle(newTitle)) {
            System.out.println("Cannot set the item title.");
            return false;
        }

        System.out.println("Successfully set the new title ");
        System.out.println("The information for the item after being modified is: ");
        System.out.println(item);

        return true;
    }

    public boolean modifyLoanType(Item item) {
        while (true) {
            String result = Utilities.askInfo(
                    "Please enter loan type:\n1. 2-day loan\n2. 1-week loan\nSelect either number [1] or [2]: ");
            if (result.equals("1")) {
                item.setLoanType(Item.LoanType.TWO_DAYS_LOAN);
                break;
            } else if (result.equals("2")) {
                item.setLoanType(Item.LoanType.ONE_WEEK_LOAN);
                break;
            }
        }
        System.out.println("Successfully set the new loan type for item.");
        System.out.println("The information for the item after being modified is: ");
        System.out.println(item);
        return true;

    }

    public boolean increaseNumCopy(Item item) {
        System.out.println("Please enter the additional number of copies: ");

        var sc = IOHelper.getScanner();
        int copies = sc.nextInt();

        if (copies < 0) {
            System.out.println("Invalid input, number cannot be negative.");
            return false;
        }

        if (!item.setInStock(copies)) {
            System.out.println("Cannot increase the number of copy");
            return false;
        }
        System.out.println("Successfully increase the number of copy for item.");
        System.out.println("The information for the item after being modified is: ");
        System.out.println(item);
        return true;
    }

    public void increaseStock(Item item) {
        item.increaseStock();
    }

    public void increaseStock(Item item, int n) throws ItemException {
        item.increaseStock(n);
    }

    public void decreaseStock(Item item) throws ItemException {
        item.decreaseStock();
    }

    public void decreaseStock(Item item, int n) throws ItemException {
        item.decreaseStock(n);
    }

    public boolean modifyRentalFee(Item item) {
        System.out.println("Please enter the new rental fee: ");

        var sc = IOHelper.getScanner();
        int fee = sc.nextInt();

        if (fee < 0) {
            System.out.println("Invalid input, number cannot be negative.");
            return false;
        }

        if (!item.setInStock(fee)) {
            System.out.println("Cannot set the the rental fee for item");
            return false;
        }

        System.out.println("Successfully set the new rental fee for item.");
        System.out.println("The information for the item after being modified is: ");
        System.out.println(item);
        return true;
    }

    public boolean deleteItem() {
        Item temp = this.getInfoToSearch();

        if (temp == null) {
            System.out.println("No item found in the database");
            return false;
        }

        remove(temp);
        System.out.println("Successfully delete the item out of the database");
        return true;
    }

    public boolean isUnique(String input) {
        if (size() == 0)
            return true;

        for (Item i : getAll())
            if (i.getId().substring(1, 4).equals(input))
                return false;

        return true;
    }

    public Item getInfoToSearch() {

        System.out.println("Search for the item information.");
        System.out.println("Enter the the option below: ");
        System.out.println("1. Search by ID.");
        System.out.println("2. Search by title.");

        var sc = IOHelper.getScanner();
        int choice = Integer.MAX_VALUE;

        while (true) {
            System.out.print("Enter the selection: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> System.out.println("Please enter ID: ");
                case 2 -> System.out.println("Please enter the title: ");
                default -> {
                    System.out.println("Invalid Selection.");
                    continue;
                }
            }

            String input = sc.nextLine();

            return searchItem(input, choice);
        }
    }

    public Item searchItem(String input, int choice) {
        if (size() == 0)
            return null;

        for (Item i : getAll())
            if (choice == 1 && i.getId().equals(input))
                return i;
            else if (i.getTitle().equals(input))
                return i;

        return null;
    }

    public void displayAll(ArrayList<Item> items) {
        for (Item i : items)
            System.out.println(i);
    }

    public void displayAll() {
        displayAll(getAll());
    }

    // Display all items sorted by titles or IDs
    public void displayAllSorted() {
        System.out.println("Display all items sorted by titles or IDs.");
        System.out.println("Enter the the option below: ");
        System.out.println("1. Sort by ID.");
        System.out.println("2. Sort by title.");

        var sc = IOHelper.getScanner();
        int choice = Integer.MAX_VALUE;

        while (true) {
            System.out.print("Enter the selection: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Sort by ID.");
                    var items = new ArrayList<>(getAll());

                    items.sort(Comparator.comparing(Item::getId));
                    displayAll(items);

                    return;
                }

                case 2 -> {
                    System.out.println("Sort by title.");
                    var items = new ArrayList<>(getAll());

                    items.sort(Comparator.comparing(Item::getTitle));
                    displayAll(items);

                    return;
                }

                default -> System.out.println("Invalid Selection.");
            }
        }
    }

    // Display all items that have no copies in stock.
    public void displayAllOutOfStock() {
        System.out.println("Items that currently have no copies in stock.");
        for (Item i : getAll())
            if (i.getInStock() == 0)
                System.out.println(i);
    }

    public Item getItem(Account account, String id) throws AccountException {
        for (Item i : account.getCurrentRentals())
            if (i.getId().equals(id))
                return i;

        throw new AccountException("Id does not exist");
    }

    public Item getItem(String id) throws AccountException {
        for (Item i : getAll())
            if (i.getId().equals(id))
                return i;

        throw new AccountException("Id does not exist");
    }
}
