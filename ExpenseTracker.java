import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;

class Expense {
    int id;
    String description;
    double amount;
    String category;
    String date;
    Expense next;

    Expense(int id, String description, double amount,
            String category, String date) {

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.next = null;
    }
}

class ExpenseLinkedList {

    Expense head;
    int count = 0;

    // Add expense
    void addExpense(int id, String description, double amount,
            String category, String date) {

        Expense newNode = new Expense(id, description, amount,
                category, date);

        if (head == null) {
            head = newNode;
        } else {
            Expense temp = head;

            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = newNode;
        }

        count++;
        System.out.println("Expense added successfully!");
    }

    // Display all expenses
    void displayExpenses() {

        if (head == null) {
            System.out.println("No expenses found.");
            return;
        }

        Expense temp = head;

        System.out.println("\n----------- EXPENSE HISTORY -----------");

        while (temp != null) {

            System.out.println("ID: " + temp.id);
            System.out.println("Description: " + temp.description);
            System.out.println("Amount: Rs. " + temp.amount);
            System.out.println("Category: " + temp.category);
            System.out.println("Date: " + temp.date);
            System.out.println("---------------------------------------");

            temp = temp.next;
        }
    }

    // Delete expense
    void deleteExpense(int id) {

        if (head == null) {
            System.out.println("Expense list is empty.");
            return;
        }

        // edge case: only one expense present
        if (head.id == id) {
            head = head.next;
            count--;
            System.out.println("Expense deleted successfully!");
            return;
        }

        Expense temp = head;

        while (temp.next != null && temp.next.id != id) {
            temp = temp.next;
        }

        if (temp.next == null) {
            System.out.println("Expense ID not found.");
        } else {
            temp.next = temp.next.next;
            count--;
            System.out.println("Expense deleted successfully!");
        }
    }

    // Calculate monthly spending
    double monthlyTotal(String month) {

        double total = 0;
        Expense temp = head;

        while (temp != null) {

            if (temp.date.startsWith(month)) { // 2026-11-17 starts with '2016-11'
                total += temp.amount;
            }

            temp = temp.next;
        }

        return total;
    }

    // Calculate average daily spending
    void averageDaily(String month) {

        double total = monthlyTotal(month); // eg: 300 spent in november=> total = 300

        YearMonth selectedMonth = YearMonth.parse(month); // Convert the month into YearMonth format
        int days; // YearMonth.parse() converts this String into a YearMonth object.

        if (selectedMonth.equals(YearMonth.now())) { // Check whether the selected month is the current month
            days = LocalDate.now().getDayOfMonth(); // .getDayOfMonth() returns today's day number.
        } else if (selectedMonth.isBefore(YearMonth.now())) { // Check whether the selected month is in the past
            days = selectedMonth.lengthOfMonth(); // .lengthOfMonth() returns the total number of days in that month.
        } else {
            days = 0;
        }

        if (days == 0) {
            System.out.println("Average cannot be calculated for a future month.");
        } else {
            System.out.println("Average Daily Spending: Rs. " + (total / days));
        }
    }

    // Category-wise spending

    void categorySummary(String month) {

        if (head == null) {
            System.out.println("No expenses available.");
            return;
        }

        double food = 0;
        double travel = 0;
        double shopping = 0;
        double education = 0;
        double other = 0;

        Expense temp = head;

        while (temp != null) {

            if (temp.date.startsWith(month)) {

                if (temp.category.equals("Food")) {
                    food += temp.amount;
                } else if (temp.category.equals("Travel")) {
                    travel += temp.amount;
                } else if (temp.category.equals("Shopping")) {
                    shopping += temp.amount;
                } else if (temp.category.equals("Education")) {
                    education += temp.amount;
                } else {
                    other += temp.amount;
                }
            }

            temp = temp.next;
        }

        System.out.println("\n------- CATEGORY SUMMARY -------");

        System.out.println("Food: Rs. " + food);
        System.out.println("Travel: Rs. " + travel);
        System.out.println("Shopping: Rs. " + shopping);
        System.out.println("Education: Rs. " + education);
        System.out.println("Other: Rs. " + other);
    }

    // Save expenses to file
    void saveExpenses() {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("expenses.txt"));   //FileWriter- Used to write data into a file.
            //"expenses.txt" is the name of the file. If the file does not exist, Java creates it. If it already exists, its previous contents are overwritten. 
            //The PrintWriter class of the java.io package can be used to write output data in a commonly readable form (text).
            Expense temp = head;

            while (temp != null) {

                writer.println(temp.id + "|" + temp.description + "|" + temp.amount + "|" + temp.category + "|" + temp.date);

                temp = temp.next;
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving expenses.");
        }
    }

    // Load expenses from file
    void loadExpenses() {    //The loadExpenses() method is used to retrieve previously saved expenses from the expenses.txt file and insert them back into the list

        try {
            File file = new File("expenses.txt");

            if (!file.exists()) {
                return;
            }

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {   //reads every line from file

                String line = fileScanner.nextLine(); // goes to the next line and reads it
                String[] data = line.split("\\|");   //The split() method divides a String into smaller parts using a separator.

                if (data.length == 5) {

                    addExpense(
                            Integer.parseInt(data[0]),
                            data[1],
                            Double.parseDouble(data[2]),
                            data[3],
                            data[4]);
                }
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading expenses.");
        }
    }
}

public class ExpenseTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ExpenseLinkedList list = new ExpenseLinkedList();

        double budget = 10000;
        int id = 1;

        list.loadExpenses();

        System.out.println("\n================================");
        System.out.println("     PERSONAL EXPENSE TRACKER");
        System.out.println("================================");

        while (true) {

            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Add Expense");
            System.out.println("2. Display All Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Monthly Spending Summary");
            System.out.println("5. Average Daily Spending");
            System.out.println("6. Category-wise Summary");
            System.out.println("7. Set Monthly Budget");
            System.out.println("8. Check Remaining Budget");
            System.out.println("9. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter description: ");
                    String description = sc.nextLine();

                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    System.out.println("Categories:");
                    System.out.println("1. Food");
                    System.out.println("2. Travel");
                    System.out.println("3. Shopping");
                    System.out.println("4. Education");
                    System.out.println("5. Other");

                    System.out.print("Select category: ");
                    int categoryChoice = sc.nextInt();
                    sc.nextLine();

                    String category;

                    switch (categoryChoice) {
                        case 1:
                            category = "Food";
                            break;
                        case 2:
                            category = "Travel";
                            break;
                        case 3:
                            category = "Shopping";
                            break;
                        case 4:
                            category = "Education";
                            break;
                        default:
                            category = "Other";
                    }

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = sc.nextLine();

                    try {
                        LocalDate.parse(date);

                        if (amount <= 0) {
                            System.out.println("Amount must be positive.");
                            break;
                        }

                        list.addExpense(id, description, amount,
                                category, date);

                        id++;

                        list.saveExpenses();

                    } catch (Exception e) {
                        System.out.println("Invalid date or input.");
                    }

                    break;

                case 2:

                    list.displayExpenses();
                    break;

                case 3:

                    System.out.print("Enter Expense ID to delete: ");
                    int deleteId = sc.nextInt();

                    list.deleteExpense(deleteId);
                    list.saveExpenses();

                    break;

                case 4:

                    System.out.print("Enter month (YYYY-MM): ");
                    String month1 = sc.nextLine();

                    try {
                        YearMonth.parse(month1);

                        System.out.println("Total Monthly Spending: Rs. " +
                                list.monthlyTotal(month1));

                    } catch (Exception e) {
                        System.out.println("Invalid month format.");
                    }

                    break;

                case 5:

                    System.out.print("Enter month (YYYY-MM): ");
                    String month2 = sc.nextLine();

                    try {
                        YearMonth.parse(month2);
                        list.averageDaily(month2);

                    } catch (Exception e) {
                        System.out.println("Invalid month format.");
                    }

                    break;

                case 6:

                    System.out.print("Enter month (YYYY-MM): ");
                    String month3 = sc.nextLine();

                    try {
                        YearMonth.parse(month3);
                        list.categorySummary(month3);

                    } catch (Exception e) {
                        System.out.println("Invalid month format.");
                    }

                    break;

                case 7:

                    System.out.print("Enter monthly budget: ");
                    budget = sc.nextDouble();

                    if (budget <= 0) {
                        System.out.println("Budget must be positive.");
                        budget = 10000;
                    } else {
                        System.out.println("Budget updated successfully!");
                    }

                    break;

                case 8:

                    System.out.print("Enter month (YYYY-MM): ");
                    String month4 = sc.nextLine();

                    try {
                        YearMonth.parse(month4);

                        double spent = list.monthlyTotal(month4);
                        double remaining = budget - spent;

                        System.out.println("Monthly Budget: Rs. " + budget);
                        System.out.println("Total Spent: Rs. " + spent);
                        System.out.println("Remaining Budget: Rs. " +
                                remaining);

                        if (remaining < 0) {
                            System.out.println("You have exceeded your budget!");
                        }

                    } catch (Exception e) {
                        System.out.println("Invalid month format.");
                    }

                    break;

                case 9:

                    list.saveExpenses();

                    System.out.println("Expenses saved successfully.");
                    System.out.println("Thank you for using Expense Tracker!");

                    sc.close();
                    return;

                default:

                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}