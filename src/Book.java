public class Book {
    private int id;
    private String title;
    private String author;
    private int quantity;
    private double price;

    private static int nextId = 1;

    public Book(String title, String author, int quantity, double price) {
        this.id = nextId++;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book\n");
        sb.append("  [ id=").append(id).append(" ]\n");
        sb.append("  [ Title: ").append(title).append(" ]\n");
        sb.append("  [ Author: ").append(author).append(" ]\n");
        sb.append("  [ Quantity: ").append(quantity).append(" ]\n");
        sb.append("  [ Price: ").append(price).append(" ]\n");
        return sb.toString();
    }

    public void decreaseQuantity(int quantityToDecrease) throws IllegalArgumentException {
        if (quantityToDecrease > 0 && quantityToDecrease <= quantity) {
            quantity -= quantityToDecrease;
        } else {
            System.out.println("Invalid quantity to decrease.");
        }
    }

    public void increaseQuantity(int quantityToAdd) throws IllegalArgumentException {
        if (quantityToAdd > 0) {
            quantity += quantityToAdd;
            System.out.println("Quantity increased by " + quantityToAdd + ". New quantity: " + quantity);
        } else {
            throw new IllegalArgumentException("Invalid quantity to add.");
        }
    }

}
