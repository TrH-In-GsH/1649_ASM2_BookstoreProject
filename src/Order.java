import java.util.Iterator;
public class Order {
    private static int nextId = 1;

    private int id;
    private String customerName;
    private String deliveryAddress;
    private MyArrayList<Book> books;
    private int quantity;
    private double totalPrice;
    private String status;

    public Order(String customerName, String deliveryAddress) {
        this.id = nextId++;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.books = new MyArrayList<>();
        this.quantity = 0;
        this.totalPrice = 0.0;
        this.status = "Waiting";
    }

    public void addBook(Book book) {
        books.add(book);
        quantity++;
        totalPrice += book.getPrice();
    }

    public void removeBook(Book book) {
        boolean removed = books.remove(book);
        if (removed) {
            quantity--;
            totalPrice -= book.getPrice();
        } else {
            System.out.println("Book not found in order.");
        }
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public MyArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(MyArrayList<Book> books) {
        this.books = books;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(id).append("\n");
        sb.append("Customer Name: ").append(customerName).append("\n");
        sb.append("Delivery Address: ").append(deliveryAddress).append("\n");
        sb.append("Books: \n");
        for (Book book : books) {
            sb.append("\t[").append(book.getTitle()).append(" by ").append(book.getAuthor()).append("]\n");
        }
        sb.append("Quantity: ").append(quantity).append("\n");
        sb.append("Total Price: ").append(totalPrice).append("\n");
        sb.append("Status: ").append(status).append("\n");
        return sb.toString();
    }

}
