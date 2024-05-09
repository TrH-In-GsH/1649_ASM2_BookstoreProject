import java.util.NoSuchElementException;

public class OrderingSystem {
    private MyQueue<Order> orderQueue;
    private MyArrayList<CompleteOrder> completeOrders;
    private Bookstore bookstore;

    public MyArrayList<CompleteOrder> getCompleteOrders() {
        return completeOrders;
    }
    public OrderingSystem(Bookstore bookstore) {
        this.orderQueue = new MyQueue<>();
        this.completeOrders = new MyArrayList<>();
        this.bookstore = bookstore;
    }
    public void placeOrder(Order order) {
        orderQueue.offer(order);
    }
    public void viewOrders() {
        if (orderQueue.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            for (Order order : orderQueue) {
                System.out.println(order);
            }
        }
    }
    public void removeOrder(int orderId) throws NoSuchElementException {
        MyQueue<Order> tempQueue = new MyQueue<>();
        boolean found = false;
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            if (order.getId() == orderId && order.getStatus().equals("Waiting")) {
                found = true;
                System.out.println("Order removed: " + order);
                for (Book book : order.getBooks()) {
                    bookstore.increaseBookQuantity2(book.getTitle(), 1);
                }
            } else {
                tempQueue.offer(order);
            }
        }
        if (!found) {
            System.out.println("Order with ID " + orderId + " not found or not in waiting status.");
        }
        orderQueue = tempQueue;
    }
    public int totalOrders() throws NoSuchElementException {
        if (orderQueue.isEmpty()) {
            throw new NoSuchElementException("No orders available.");
        }
        MyQueue<Order> tempQueue = new MyQueue<>();
        int countWaiting = 0;
        int countShipping = 0;
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            tempQueue.offer(order);
            if (order.getStatus().equals("Waiting")) {
                countWaiting++;
            } else if (order.getStatus().equals("Shipping")) {
                countShipping++;
            }
        }
        orderQueue = tempQueue;
        System.out.println("Total Orders in Waiting: " + countWaiting);
        System.out.println("Total Orders in Shipping: " + countShipping);
        return countWaiting + countShipping;
    }
    public MyQueue<Order> searchOrder(int searchOption, String keyword) throws IllegalArgumentException {
        if (searchOption < 1 || searchOption > 3) {
            throw new IllegalArgumentException("Invalid search option. Please choose a valid search option (1, 2, or 3).");
        }
        MyQueue<Order> searchResult = new MyQueue<>();
        boolean foundMatch = false;
        try {
            for (Order order : orderQueue) {
                boolean match = false;
                switch (searchOption) {
                    case 1:
                        int orderId = Integer.parseInt(keyword);
                        if (order.getId() == orderId) {
                            match = true;
                        }
                        break;
                    case 2:
                        if (order.getCustomerName().toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                        break;
                    case 3:
                        if (order.getDeliveryAddress().toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                        break;
                }
                if (match) {
                    searchResult.offer(order);
                    foundMatch = true;
                }
            }
            if (!foundMatch) {
                System.out.println("No orders found matching the given criteria.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid keyword for searching by book ID: " + keyword);
        }
        return searchResult;
    }


    public void processOrder(int orderId) throws NoSuchElementException {
        MyQueue<Order> tempQueue = new MyQueue<>();
        boolean found = false;
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            if (order.getId() == orderId && order.getStatus().equals("Waiting")) {
                found = true;
                System.out.println("Order " + orderId + " has been processed and moved to shipping.");
                order.updateStatus("Shipping");
                CompleteOrder completeOrder = new CompleteOrder(order);
                completeOrders.add(completeOrder);
            } else {
                tempQueue.offer(order);
            }
        }
        if (!found) {
            System.out.println("Order with ID " + orderId + " not found or not in waiting status.");
        }
        orderQueue = tempQueue;
    }
    public void removeBookFromOrder(int orderId, String bookTitle) {
        boolean orderFound = false;
        boolean bookFound = false;
        for (Order order : orderQueue) {
            if (order.getId() == orderId && order.getStatus().equals("Waiting")) {
                orderFound = true;
                for (Book book : order.getBooks()) {
                    if (book.getTitle().equals(bookTitle)) {
                        bookFound = true;
                        order.removeBook(book);
                        bookstore.increaseBookQuantity2(bookTitle, 1);
                        System.out.println("Book '" + bookTitle + "' removed from order!");
                        return;
                    }
                }
                if (!bookFound) {
                    System.out.println("Book '" + bookTitle + "' not found in the order.");
                    return;
                }
            }
        }
        if (!orderFound) {
            System.out.println("Order with ID " + orderId + " not found or not in waiting status.");
        }
    }


}