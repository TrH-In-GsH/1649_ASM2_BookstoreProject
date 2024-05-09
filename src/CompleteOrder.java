public class CompleteOrder {
    private int orderId;
    private String customerName;
    private String deliveryAddress;
    private MyArrayList<Book> books;
    private int quantity;
    private String orderStatus;
    private double totalPrice;
    private MyArrayList<Order> orders;
    public CompleteOrder() {
        this.orders = new MyArrayList<>();
    }

    public CompleteOrder(Order order) {
        this.orderId = order.getId();
        this.customerName = order.getCustomerName();
        this.deliveryAddress = order.getDeliveryAddress();
        this.books = order.getBooks();
        this.quantity = order.getQuantity();
        this.orderStatus = order.getStatus();
        this.totalPrice = order.getTotalPrice();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Customer Name: ").append(customerName).append("\n");
        sb.append("Delivery Address: ").append(deliveryAddress).append("\n");
        sb.append("Books: \n");
        for (Book book : books) {
            sb.append("\t[").append(book.getTitle()).append(" by ").append(book.getAuthor()).append("]\n");
        }
        sb.append("Quantity: ").append(quantity).append("\n");
        sb.append("Total Price: ").append(totalPrice).append("\n");
        sb.append("Status: ").append(orderStatus).append("\n");
        return sb.toString();
    }
}
