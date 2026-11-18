# Personal Expense Tracker

A menu-driven **Java console application** for recording and managing personal expenses. The project uses a **singly linked list** to store expenses during execution and a text file (`expenses.txt`) to preserve expense records between runs.

## Features

- **Add an expense** with a description, amount, category, and date.
- **View expense history** with each expense's ID and details.
- **Delete an expense** by its ID.
- **Calculate monthly spending** for a selected month.
- **Calculate average daily spending** for a selected month.
  - For the current month, the calculation uses the number of days elapsed so far.
  - For a past month, it uses the total number of days in that month.
  - Future months are not supported for this calculation.
- **View category-wise spending** for a selected month.
- **Set a monthly budget** and check the remaining budget or whether spending has exceeded it.
- **Save and load expenses** using `expenses.txt`.

## Technologies and Concepts

- Java
- Object-oriented programming
- Singly linked lists
- File handling (`File`, `FileWriter`, `PrintWriter`, `Scanner`)
- Date handling (`LocalDate`, `YearMonth`)
- Console input and menu-driven programming

## Expense Categories

Expenses are assigned to one of the following categories:

1. Food
2. Travel
3. Shopping
4. Education
5. Other

## Project Structure

The application is implemented in a single Java source file:

```text
ExpenseTracker/
├── ExpenseTracker.java
└── expenses.txt          # Created automatically when expenses are saved
```

The source file contains three classes:

| Class | Responsibility |
|---|---|
| `Expense` | Represents one expense and stores its ID, description, amount, category, date, and reference to the next node. |
| `ExpenseLinkedList` | Manages the linked list and provides methods to add, display, delete, summarize, save, and load expenses. |
| `ExpenseTracker` | Contains the `main` method and the interactive console menu. |

## Requirements

- Java Development Kit (JDK) installed.
- A terminal or command prompt.

The program uses `LocalDate` and `YearMonth` from the Java standard library.

## How to Run

1. Save the source code in a file named `ExpenseTracker.java`.
2. Open a terminal in the directory containing the file.
3. Compile the program:

   ```bash
   javac ExpenseTracker.java
   ```

4. Run the application:

   ```bash
   java ExpenseTracker
   ```

5. Follow the on-screen menu and enter the requested information.

## Menu Options

| Option | Operation | Description |
|---:|---|---|
| 1 | Add Expense | Enter a description, amount, category, and date. |
| 2 | Display All Expenses | Display all saved expense records. |
| 3 | Delete Expense | Remove an expense using its ID. |
| 4 | Monthly Spending Summary | Display the total spending for a month. |
| 5 | Average Daily Spending | Calculate average spending per day for a month. |
| 6 | Category-wise Summary | Display monthly totals for each category. |
| 7 | Set Monthly Budget | Set the budget used by the budget-checking option. |
| 8 | Check Remaining Budget | Compare a month's spending with the currently set budget. |
| 9 | Exit | Save expenses and close the application. |

## Date Formats

Enter dates in the following formats:

- **Expense date:** `YYYY-MM-DD`  
  Example: `2026-11-18`
- **Month for summaries:** `YYYY-MM`  
  Example: `2026-11`

The application validates expense dates with `LocalDate.parse()` and summary months with `YearMonth.parse()`.

## Data Storage

Expense records are stored in a plain-text file named `expenses.txt` in the application's working directory.

Each expense is written on a separate line using this format:

```text
id|description|amount|category|date
```

Example:

```text
1|Lunch|150.0|Food|2026-11-18
2|Bus fare|40.0|Travel|2026-11-18
```

When the application starts, it attempts to load records from this file. If the file does not exist, the application starts with an empty expense list. Saving rewrites the file with the current list of expenses.

## Data Structure

Each expense is represented by an `Expense` node. Every node contains a reference to the next node, and `ExpenseLinkedList` maintains the `head` reference to the first node.

- **Add:** A new node is appended to the end of the list.
- **Display:** The list is traversed from `head` to the end.
- **Delete:** The node with the matching ID is unlinked from the list.
- **Monthly and category summaries:** The list is traversed and matching records are accumulated.

## Current Limitations

- The monthly budget is held in memory and resets to the default value of **Rs. 10,000** whenever the application is restarted.
- Expense records are saved in `expenses.txt`; the budget is not saved.
- The next expense ID starts at `1` each time the program starts. If existing records are loaded, newly added records may therefore reuse an existing ID.
- The application is designed as a basic console project and does not include a graphical interface or database.

## Possible Improvements

- Generate unique expense IDs after loading existing records.
- Persist monthly budgets in a file.
- Add editing and searching of expenses.
- Add stronger validation for menu choices and numeric input.
- Support custom date ranges and additional reports.
- Replace the text-file storage with a database.

## License

No license is specified for this project. Add a license file if you intend to distribute or reuse the project publicly.
