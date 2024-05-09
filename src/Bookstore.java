import java.util.NoSuchElementException;
public class Bookstore {
    private static MyArrayList<Book> books;

    public Bookstore() {
        this.books = new MyArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        sortBooks();
    }

    public void viewAvailableBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public boolean containsBook(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return true;
            }
        }
        return false;
    }

    public Book getBookById(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public void removeBook(int bookId) {
        try {
            Book bookToRemove = null;
            for (Book book : books) {
                if (book.getId() == bookId) {
                    bookToRemove = book;
                    break;
                }
            }
            if (bookToRemove != null) {
                books.remove(bookToRemove);
                System.out.println("Book with ID " + bookId + " removed from the bookstore.");
            } else {
                throw new NoSuchElementException("Book with ID " + bookId + " not found in the bookstore.");
            }
        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void increaseBookQuantity(int bookId, int quantityToAdd) {
        try {
            Book bookToUpdate = null;
            for (Book book : books) {
                if (book.getId() == bookId) {
                    bookToUpdate = book;
                    break;
                }
            }
            if (bookToUpdate != null) {
                bookToUpdate.increaseQuantity(quantityToAdd);
                System.out.println("Quantity of book with ID '" + bookId + "' increased by " + quantityToAdd + ".");
            } else {
                throw new NoSuchElementException("Book with ID " + bookId + " not found in the bookstore.");
            }
        } catch (NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void increaseBookQuantity2(String bookTitle, int quantityToAdd) {
        if (bookTitle == null) {
            throw new IllegalArgumentException("Book title cannot be null.");
        }
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle)) {
                book.increaseQuantity(quantityToAdd);
                System.out.println("Quantity of book '" + bookTitle + "' increased by " + quantityToAdd + ".");
                return;
            }
        }
        throw new IllegalArgumentException("Book with title '" + bookTitle + "' not found in the bookstore.");
    }

    public void decreaseBookQuantity(int bookId, int quantityToDecrease) {
        if (quantityToDecrease <= 0) {
            throw new IllegalArgumentException("Invalid quantity to decrease.");
        }
        for (Book book : books) {
            if (book.getId() == bookId) {
                book.decreaseQuantity(quantityToDecrease);
                return;
            }
        }
        throw new IllegalArgumentException("Book with ID '" + bookId + "' not found in the bookstore.");
    }

    private void sortBooks() {
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                try {
                    if (books.get(j).getTitle().compareToIgnoreCase(books.get(j + 1).getTitle()) > 0) {
                        Book temp = books.get(j);
                        books.set(j, books.get(j + 1));
                        books.set(j + 1, temp);
                        swapped = true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Index out of bounds exception: " + e.getMessage());
                }
            }
            if (!swapped) {
                break;
            }
        }
    }



    public MyArrayList<Book> searchBook(int searchOption, String keyword) {
        MyArrayList<Book> searchResult = new MyArrayList<>();
        try {
            for (Book book : books) {
                boolean match = false;
                switch (searchOption) {
                    case 1:
                        int bookId = Integer.parseInt(keyword);
                        if (book.getId() == bookId) {
                            match = true;
                        }
                        break;
                    case 2:
                        if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                        break;
                    case 3:
                        if (book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                        break;
                    case 4:
                        if (String.valueOf(book.getId()).contains(keyword) ||
                                book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                            match = true;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid search option: " + searchOption);
                }
                if (match) {
                    searchResult.add(book);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid keyword for searching by book ID: " + keyword);
        }
        return searchResult;
    }
}