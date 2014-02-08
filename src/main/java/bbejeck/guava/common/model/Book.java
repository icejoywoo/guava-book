package bbejeck.guava.common.model;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import static com.google.common.base.Preconditions.*;
/**
 * User: Bill Bejeck
 * Date: 3/27/13
 * Time: 10:56 PM
 */
public class Book implements Comparable<Book> {

    private String author;
    private String title;
    private String publisher;
    private String isbn;
    private double price;

    public Book(String author, String title, String publisher, String isbn, double price) {
        this.author = checkNotNull(author,"Author can't be null");
        this.title = checkNotNull(title,"Title can't be null");
        this.publisher = checkNotNull(publisher,"Publisher can't be null");
        this.isbn = checkNotNull(isbn,"ISBN can't be null");
        checkArgument(price > 0.0,"Price must be more than zero");
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }


    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .omitNullValues()
                .add("title", title)
                .add("author", author)
                .add("publisher", publisher)
                .add("price",price)
                .add("isbn", isbn).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title, author, publisher, isbn,price);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equal(this, obj);
    }

    @Override
    public int compareTo(Book o) {
        return ComparisonChain.start()
                .compare(this.title, o.getTitle())
                .compare(this.author, o.getAuthor())
                .compare(this.publisher, o.getPublisher())
                .compare(this.isbn, o.getIsbn())
                .compare(this.price, o.getPrice())
                .result();
    }

    public static class Builder {
        private String author = "The Author";
        private String title = "A BOOK";
        private String publisher = "Publisher";
        private String isbn = "ABCDE1234567";
        private double price = 0.99;

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Book build(){
            return new Book(this.author,this.title,this.publisher,this.isbn,this.price);
        }
    }

}
