package items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import utils.Utilities;

public class ItemManager extends Utilities {

    private ArrayList<Item> items;

    private ItemManager() {
        items = new ArrayList<>();
    }

    private static ItemManager instance = null;

    public static ItemManager getInstance() {
        if (instance == null)
            instance = new ItemManager();
        return instance;
    }

    public void initialize() {
        // Load the items from the local storage
    }

    public void loadData() throws IOException {
        try {
            File myObj = new File("/Users/huuphuoc/IdeaProjects/phuoc_OOP_finalProject/src/items.txt");
            Scanner scan = new Scanner(myObj);

            while (scan.hasNextLine()) {
                String input = scan.nextLine();
                input = input.strip();
                if (Character.compare(input.charAt(0), '#') == 0)
                    continue;
                String[] data = input.split(",");
                this.createItem(data);
            }

            // Close the scanner object to prevent resource leak
            scan.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (ItemException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveData() {
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
            info[n] = askInfo(questionList.get(n));
            if (info[n].equals("1"))
                info[n] = "Record";
            else if (info[n].equals("2"))
                info[n] = "DVD";
            else if (info[n].equals("3"))
                info[n] = "Game";
            else
                continue;
            break;
        }

        if (info[n] != "Game") // if new item is not game then extra question for genre will be asked
        {
            n = 7;
            questionList.push(
                    "Please enter genre:\n1. Action\n2. Horror\n3. Drama\n4. Comedy\nSelect either number [1],[2], [3] or [4]: ");
        } else // otherwise only 6 question will be asked
        {
            n = 6;
        }
        String[] result = new String[info.length];
        for (int i = 0; i < n; i++) // loop through the question number, and record the answer
        {
            if (i == 3) // when getting to loan type question asked, valid input should be enterd
            {
                while (true) {
                    info[i] = askInfo(questionList.get(i));
                    if (info[i].equals("1"))
                        info[i] = "2-day";
                    else if (info[i].equals("2"))
                        info[i] = "1-week";
                    else
                        continue;
                    break;
                }
            } else if (i == 6) {
                while (true) {
                    info[i] = askInfo(questionList.get(i));
                    if (info[i].equals("1"))
                        info[i] = "Action";
                    else if (info[i].equals("2"))
                        info[i] = "Horror";
                    else if (info[i].equals("3"))
                        info[i] = "Drama";
                    else if (info[i].equals("4"))
                        info[i] = "Comedy";
                    else
                        continue;
                    break;
                }
            } else if (i != 2)
                info[i] = askInfo(questionList.get(i));
            result[i] = info[i]; // after all the answer are validated push it back to result
        }

        return result;

    }

    public void createItem() {
        try {
            this.createItem(getInformation());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void createItem(String[] data) throws ItemException {
        try {
            if (!this.isUnique(data[0].substring(1, 4)))
                throw new ItemException("ID " + data[0].substring(0, 4) + " already exists in the database.");
            if (data[2].equals("Game"))
                items.add(new Game(data[0], data[1], data[2], data[3], Integer.valueOf(data[4]),
                        Double.valueOf(data[5])));
            else if (data[2].equals("DVD"))
                items.add(new DVD(data[0], data[1], data[2], data[3], Integer.valueOf(data[4]), Double.valueOf(data[5]),
                        data[6]));
            else if (data[2].equals("Record"))
                items.add(new Movie(data[0], data[1], data[2], data[3], Integer.valueOf(data[4]),
                        Double.valueOf(data[5]), data[6]));

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

        int numSelection = Integer.MAX_VALUE;

        while (true) {
            System.out.print("Enter the selection: ");

            Scanner input = new Scanner(System.in);
            numSelection = input.nextInt();

            // Close the scanner object to prevent resource leak
            input.close();

            switch (numSelection) {
                case 1:
                    return this.modifyTitle(temp);
                case 2:
                    return this.modifyLoantype(temp);
                case 3:
                    return this.increaseNumCopy(temp);
                case 4:
                    return this.modifyRentalFee(temp);
                default:
                    System.out.println("Invalid Selection.");
                    return false;
            }
        }
    }

    public boolean modifyTitle(Item item) {
        System.out.println("Please enter the new title: ");

        Scanner input = new Scanner(System.in);
        String newTitle = input.nextLine();

        // Close the scanner object to prevent resource leak
        input.close();

        if (!item.setItemTitle(newTitle)) {
            System.out.println("Cannot set the item title.");
            return false;
        }

        System.out.println("Successfully set the new title ");
        System.out.println("The information for the item after being modified is: ");
        System.out.println(item);

        return true;
    }

    public boolean modifyLoantype(Item item) {
        while (true) {
            String result = askInfo(
                    "Please enter loan type:\n1. 2-day loan\n2. 1-week loan\nSelect either number [1] or [2]: ");
            if (result.equals("1")) {
                item.setLoanType(Item.LoanType.TWO_DAYS_LOAN);
                break;
            } else if (result.equals("2")) {
                item.setLoanType(Item.LoanType.ONE_WEEK_LOAN);
                break;
            } else
                continue;
        }
        System.out.println("Successfully set the new loan type for item.");
        System.out.println("The information for the item after being modified is: ");
        System.out.println(item);
        return true;

    }

    public boolean increaseNumCopy(Item item) {
        while (true) {
            System.out.println("Please enter the additional number of copies: ");

            Scanner input = new Scanner(System.in);
            int copies = input.nextInt();

            // Close the scanner object to prevent resource leak
            input.close();

            if (copies < 0) {
                System.out.println("Invalid input, number cannot be negative.");
                return false;
            }

            if (!item.setNumCopy(copies)) {
                System.out.println("Cannot increase the number of copp");
                return false;
            } else {
                break;
            }
        }
        System.out.println("Successfully increase the number of copy for item.");
        System.out.println("The information for the item after being modified is: ");
        System.out.println(item);
        return true;
    }

    public boolean modifyRentalFee(Item item) {
        while (true) {
            System.out.println("Please enter the new rental fee: ");

            Scanner input = new Scanner(System.in);
            int fee = input.nextInt();

            // Close the scanner object to prevent resource leak
            input.close();

            if (fee < 0) {
                System.out.println("Invalid input, number cannot be negative.");
                return false;
            }
            if (!item.setNumCopy(fee)) {
                System.out.println("Cannot set the the rental fee for item");
                return false;
            } else {
                break;
            }
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
        this.items.remove(temp);
        System.out.println("Successfully delete the item out of the database");
        return true;
    }

    public boolean isUnique(String input) {
        if (items.size() == 0)
            return true;
        for (Item i : items) {
            if (i.getItemId().substring(1, 4).equals(input))
                return false;
        }
        return true;
    }

    public Item getInfoToSearch() {

        System.out.println("Search for the item information.");
        System.out.println("Enter the the option below: ");
        System.out.println("1. Search by ID.");
        System.out.println("2. Search by title.");

        Scanner input = new Scanner(System.in);
        int numSelection = Integer.MAX_VALUE;

        while (true) {
            System.out.print("Enter the selection: ");
            numSelection = input.nextInt();
            input.nextLine();

            switch (numSelection) {
                case 1:
                    System.out.println("Please enter ID: ");
                    break;
                case 2:
                    System.out.println("Please enter the title: ");
                    break;
                default:
                    System.out.println("Invalid Selection.");
                    continue;
            }

            String choice = input.nextLine();

            // Close the scanner object to prevent resource leak
            input.close();

            return searchItem(choice, numSelection);
        }
    }

    public Item searchItem(String input, int menuSelection) {
        if (items.size() == 0)
            return null;
        for (int i = 0; i < items.size(); i++) {
            if (menuSelection == 1) {
                if (items.get(i).getItemId().equals(input))
                    return items.get(i);
            } else {
                if (items.get(i).getItemTitle().equals(input))
                    return items.get(i);
            }
        }
        return null;
    }

    public void displayAll() {
        for (Item i : items) {
            System.out.println(i);
        }
        return;
    }

}
