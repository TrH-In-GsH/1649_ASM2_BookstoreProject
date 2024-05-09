import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MainClass {
    static Scanner scanner = new Scanner(System.in);
    static Bookstore bookstore = new Bookstore();
    static MyArrayList<CompleteOrder> completeOrders; // Biến completeOrders ở cấp độ lớp
    static OrderingSystem orderingSystem = new OrderingSystem(bookstore);

    public static void main(String[] args) {
        // Add some books to the bookstore
        addInitialData();
        // Online Bookstore is starting up...
        System.out.println("Online Bookstore is starting up...");
        // Main menu loop
        try {
            while (true) {

                showMenu();
                int choice = 0;
                while (true) {
                    String input = promptNonEmptyString(scanner, "Enter your choice (1-14):");;
                    if (input.matches("\\d+")) { // Check if string contains only numbers
                        choice = Integer.parseInt(input);
                        if (choice >= 1 && choice <= 14) {
                            handleMenuChoice(choice);
                            break;
                        } else {
                            System.out.println("Invalid input! Please enter a number (1-14).");
                        }
                    } else {
                        System.out.println("Invalid input! Please enter a valid integer.");
                    }
                }
            }


        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        } finally {
            scanner.close();
        }
    }

    // Method to add initial books to the bookstore
    private static void addInitialData() {
        long startProcessingTime = System.currentTimeMillis();
        bookstore.addBook(new Book("1984", "George Orwell", 12, 9.99));
        bookstore.addBook(new Book("Pride and Prejudice", "Jane Austen", 8, 6.99));
        bookstore.addBook(new Book("The Catcher in the Rye", "J.D. Salinger", 5, 7.49));
        bookstore.addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien", 15, 12.99));
        bookstore.addBook(new Book("1984", "George Orwell", 12, 9.99));
        bookstore.addBook(new Book("Pride and Prejudice", "Jane Austen", 8, 6.99));
        bookstore.addBook(new Book("The Catcher in the Rye", "J.D. Salinger", 5, 7.49));
        bookstore.addBook(new Book("The Lord of the Rings", "J.R.R. Tolkien", 15, 12.99));
        bookstore.addBook(new Book("1984", "George Orwell", 12, 9.99));
        bookstore.addBook(new Book("Pride and Prejudice", "Jane Austen", 8, 6.99));

        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time for adding initial books: %d ms%n", processingTime);
    }

    // Method to handle user menu choice
    private static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                long startProcessingTime = System.currentTimeMillis();
                // Displays all available books in the store
                System.out.println("==================== Available Books ====================");
                bookstore.viewAvailableBooks();
                long endProcessingTime = System.currentTimeMillis();
                long processingTime = endProcessingTime - startProcessingTime;
                System.out.printf("Processing Time for adding initial books: %d ms%n", processingTime);
                break;
            case 2:
                // Add new books to the store
                addBook();
                break;
            case 3:
                // Create new order
                addOrder();
                break;
            case 4:
                // Display all orders
                System.out.println("==================== View Orders ====================");
                orderingSystem.viewOrders();
                break;
            case 5:
                // Delete an order
                removeOrder();
                break;
            case 6:
                // Display the total order quantity
                System.out.println("==================== Total Orders ====================");
                System.out.println("Total Orders: " + orderingSystem.totalOrders());
                break;
            case 7:
                // Delete a book from the store
                removeBook();
                break;
            case 8:
                // Update the number of books
                updateBookQuantity();
                break;
            case 9:
                // Search for books
                searchBook();
                break;
            case 10:
                // Search for orders
                searchOrder();
                break;
            case 11:
                // Order processing
                System.out.println("==================== Process Order ====================");
                processOrder();
                break;
            case 12:
                // Delete books from orders
                removeBookFromOrder();
                break;
            case 13:
                // Exit the program
                System.out.println("==================== Exiting... ====================");
                System.exit(0);
                break;
            case 14:
                // View complete orders
                viewCompleteOrders();
                break;

            default:
                // Display an error message if the selection is invalid
                System.out.println("Invalid choice!");
        }
        // Wait for user to press Enter before continuing
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    // Method to add new books to the store
    private static void addBook() {
        System.out.println("==================== Add Books ====================");
        String title = promptNonEmptyString(scanner, "Enter book title: ");
        String author = promptNonEmptyString(scanner, "Enter author: ");
        int quantity = readQuantity();
        double price = readPrice();
        try {
            bookstore.addBook(new Book(title, author, quantity, price));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int readQuantity() {
        while (true) {
            System.out.print("Enter quantity: ");
            String input = scanner.nextLine();
            try {
                int quantity = Integer.parseInt(input);
                if (quantity <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than zero.");
                }
                return quantity;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer for quantity.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static double readPrice() {
        while (true) {
            System.out.print("Enter price: ");
            String input = scanner.nextLine();
            try {
                double price = Double.parseDouble(input);
                if (price <= 0.0) {
                    throw new IllegalArgumentException("Price must be greater than zero.");
                }
                return price;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number for price.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Method to create new order
    private static void addOrder() {
        long startProcessingTime = System.currentTimeMillis(); // Thời điểm bắt đầu xử lý
        System.out.println("==================== Add Order ====================");
        String customerName = promptNonEmptyString(scanner, "Enter customer name: ");
        String deliveryAddress = promptNonEmptyString(scanner, "Enter delivery address: ");
        Order order = new Order(customerName, deliveryAddress);
        int totalQuantity = 0; // Tổng số lượng sách trong đơn đặt hàng
        // Add books to order
        while (true) {
            System.out.print("Enter book ID to add (0 to finish): ");
            int bookId = 0;
            try {
                bookId = Integer.parseInt(scanner.nextLine());
                if (bookId == 0) {
                    break;
                }
                if (bookstore.containsBook(bookId)) {
                    Book book = bookstore.getBookById(bookId);
                    System.out.print("Enter quantity: ");
                    int quantityToAdd = Integer.parseInt(scanner.nextLine());
                    if (quantityToAdd > 0) {
                        if (quantityToAdd <= book.getQuantity()) {
                            totalQuantity += quantityToAdd;
                            bookstore.decreaseBookQuantity(bookId, quantityToAdd);
                            for (int i = 0; i < quantityToAdd; i++) {
                                order.addBook(book);
                            }
                        } else {
                            System.out.println("Quantity exceeds available stock for this book. Available quantity: " + book.getQuantity());
                        }
                    } else {
                        System.out.println("Invalid quantity! Please enter a valid number.");
                    }
                } else {
                    System.out.println("Invalid book ID!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
        if (totalQuantity > 0) {
            orderingSystem.placeOrder(order);
        } else {
            System.out.println("No books added to the order. Order not placed.");
        }

        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time for Add Order: %d ms%n", processingTime);
    }


    // Method to delete an order
    private static void removeOrder() {
        System.out.println("==================== Remove Order ====================");
        long startProcessingTime = System.currentTimeMillis();
        while (true) {
            String input = promptNonEmptyString(scanner, "Enter order ID to remove (0 to return): ");
            if (input.equals("0")) {
                break;
            }
            try {
                int orderIdToRemove = Integer.parseInt(input);
                if (orderIdToRemove > 0) {
                    orderingSystem.removeOrder(orderIdToRemove);
                    break;
                } else {
                    System.out.println("Invalid input! Please enter a valid order ID.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid order ID.");
            }
        }
        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time: %d ms%n", processingTime);
    }

    // Method to delete a book from the store
    private static void removeBook() {
        System.out.println("==================== Remove Book ====================");
        long startProcessingTime = System.currentTimeMillis();
        while (true) {
            String input = promptNonEmptyString(scanner, "Enter book ID to remove (0 to cancel): ");
            if (input.equals("0")) {
                break;
            }
            try {
                int bookIdToRemove = Integer.parseInt(input);
                if (bookIdToRemove > 0) {
                    bookstore.removeBook(bookIdToRemove);
                    break;
                } else {
                    System.out.println("Invalid input! Please enter a book ID greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid book ID.");
            }
        }
        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time: %d ms%n", processingTime);
    }

    // Method to update the quantity of books
    private static void updateBookQuantity() {
        System.out.println("==================== Update Book Quantity ====================");
        long startProcessingTime = System.currentTimeMillis();
        while (true) {
            String input = promptNonEmptyString(scanner, "Enter book ID (0 to return): ");
            if (input.equals("0")) {
                System.out.println("Returning menu...");
                break;
            }
            try {
                int bookIdToUpdate = Integer.parseInt(input);
                if (bookIdToUpdate > 0) {
                    int quantityToAdd = Integer.parseInt(promptNonEmptyString(scanner, "Enter quantity to add: "));
                    if (quantityToAdd == 0) {
                        System.out.println("Canceling and returning menu...");
                        break;
                    } else if (quantityToAdd > 0) {
                        bookstore.increaseBookQuantity(bookIdToUpdate, quantityToAdd);
                        break;
                    } else {
                        System.out.println("Invalid input! Quantity must be greater than 0.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid book ID (greater than 0).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid book ID and quantity.");
            }
        }
        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time: %d ms%n", processingTime);
    }

    // Method to search for books
    private static void searchBook() {
        int searchOption = 0;
        String keyword;
        do {
            try {
                System.out.println("==================== Search Book ====================");
                System.out.println("1. Search by book ID");
                System.out.println("2. Search by book title");
                System.out.println("3. Search by author");
                System.out.println("4. Search by all above");
                System.out.print("Enter search option (0 to finish): ");
                // Checks if the input is an integer
                if (scanner.hasNextInt()) {
                    searchOption = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.nextLine();
                    continue;
                }
                // Exit the loop if the user enters 0
                if (searchOption == 0) {
                    break;
                }
                if (searchOption < 1 || searchOption > 4) {
                    throw new IllegalArgumentException("Invalid search option. Please enter a valid option.");
                }
                System.out.print("Enter keyword: ");
                keyword = scanner.nextLine();
                MyArrayList<Book> searchResult = bookstore.searchBook(searchOption, keyword);
                if (searchResult.isEmpty()) {
                    System.out.println("No matching books found.");
                } else {
                    System.out.println("Matching Books:");
                    for (Book book : searchResult) {
                        System.out.println(book);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (searchOption != 0);
    }

    // Method to search for orders
    private static void searchOrder() {
        String keyword2;
        int searchOption2 = 0;
        long startProcessingTime = System.currentTimeMillis();
        while (true) {
            try {
                System.out.println("==================== Search Order ====================");
                System.out.println("1. Search by order ID");
                System.out.println("2. Search by customer name");
                System.out.println("3. Search by delivery address");
                System.out.print("Enter search option (0 to finish): ");
                searchOption2 = Integer.parseInt(promptNonEmptyString(scanner, "Enter search option (0 to finish): "));
                if (searchOption2 == 0) {
                    break;
                }
                if (searchOption2 < 1 || searchOption2 > 3) {
                    throw new IllegalArgumentException("Invalid search option. Please enter a valid option.");
                }
                keyword2 = promptNonEmptyString(scanner, "Enter keyword: ");
                MyQueue<Order> searchResult = orderingSystem.searchOrder(searchOption2, keyword2);
                if (searchResult.isEmpty()) {
                    System.out.println("No matching orders found.");
                } else {
                    System.out.println("Matching Orders:");
                    for (Order order : searchResult) {
                        System.out.println(order);
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time: %d ms%n", processingTime);
    }

    // Method to process an order
    private static void processOrder() {
        long startProcessingTime = System.currentTimeMillis();
        while (true) {
            String input = promptNonEmptyString(scanner, "Enter order ID to process: ");
            if (input.equals("0")) {
                break;
            }
            try {
                int orderIdToProcess = Integer.parseInt(input);
                if (orderIdToProcess > 0) {
                    orderingSystem.processOrder(orderIdToProcess);
                    break;
                } else {
                    System.out.println("Invalid input! Please enter a valid order ID.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time: %d ms%n", processingTime);
    }

    // Method to remove a book from an order
    private static void removeBookFromOrder() {
        System.out.println("==================== Remove Book from Order ====================");
        while (true) {
            try {
                long startProcessingTime = System.currentTimeMillis();

                int orderId = Integer.parseInt(promptNonEmptyString(scanner, "Enter order ID (Enter 0 to exit): "));
                if (orderId == 0) {
                    break;
                } else if (orderId > 0) {
                    String bookTitle = promptNonEmptyString(scanner, "Enter book title to remove from order: ");
                    try {
                        orderingSystem.removeBookFromOrder(orderId, bookTitle);
                        break;
                    } catch (NoSuchElementException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Invalid order ID! Please enter a valid order ID.");
                }

                long endProcessingTime = System.currentTimeMillis();
                long processingTime = endProcessingTime - startProcessingTime;
                System.out.printf("Processing Time: %d ms%n", processingTime);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid order ID (must be a whole number).");
                scanner.nextLine();
            }
        }
    }

    // Method to display the main menu
    private static void showMenu() {
        long startProcessingTime = System.currentTimeMillis();

        System.out.println("=======================================");
        System.out.println("|            Bookstore Menu           |");
        System.out.println("=======================================");
        System.out.println("| 1. View Available Books             |");
        System.out.println("| 2. Add Books                        |");
        System.out.println("| 3. Add Order                        |");
        System.out.println("| 4. View Orders                      |");
        System.out.println("| 5. Remove Order                     |");
        System.out.println("| 6. Total Orders                     |");
        System.out.println("| 7. Remove Book                      |");
        System.out.println("| 8. Update Book Quantity             |");
        System.out.println("| 9. Search Book                      |");
        System.out.println("| 10. Search Order                    |");
        System.out.println("| 11. Process Order                   |");
        System.out.println("| 12. Remove Book from Order          |");
        System.out.println("| 13. Exit                            |");
        System.out.println("| 14. View Complete Orders            |");
        System.out.println("=======================================");

        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time: %d ms%n", processingTime);
    }

    private static String promptNonEmptyString(Scanner scanner, String prompt) {
        long startProcessingTime = System.currentTimeMillis();

        String input = "";
        while (input.isEmpty()) {
            try {
                System.out.print(prompt);
                input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty! Please enter a valid input.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid input.");
                scanner.nextLine();
            }
        }

        long endProcessingTime = System.currentTimeMillis();
        long processingTime = endProcessingTime - startProcessingTime;
        System.out.printf("Processing Time: %d ms%n", processingTime);

        return input;
    }
    private static void viewCompleteOrders() {
        MyArrayList<CompleteOrder> completeOrders = orderingSystem.getCompleteOrders();
        if (completeOrders.isEmpty()) {
            System.out.println("No complete orders.");
        } else {
            System.out.println("Complete Orders:");
            for (CompleteOrder completeOrder : completeOrders) {
                System.out.println(completeOrder);
            }
        }
    }
}

