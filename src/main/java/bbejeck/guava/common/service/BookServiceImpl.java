package bbejeck.guava.common.service;

import bbejeck.guava.common.model.Book;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: Bill Bejeck
 * Date: 4/20/13
 * Time: 11:15 PM
 */
public class BookServiceImpl extends BaseDBService implements BookService {

    private static final String insertSql = " CREATE TABLE IF NOT EXISTS BOOK(AUTHOR VARCHAR(255), TITLE VARCHAR(255)," +
            " PUBLISHER VARCHAR(255), ISBN VARCHAR(255), PRICE DOUBLE,ID INT PRIMARY KEY) AS SELECT * FROM CSVREAD('src/main/resources/books.data',null,'fieldSeparator=|')";


    public BookServiceImpl() {
        try {
            init(insertSql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() throws Exception {
        super.shutDown();
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        try {
            return getBooks("select * from BOOK where author=?", author);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Book> get() {
        try {
            return getBooks("select * from BOOK", null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        try {
            List<Book> books = getBooks("select * from BOOK where isbn=?", isbn);

            return books.get(0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Book> getBooks(String sql, String parameter) throws SQLException {
        List<Book> books = Lists.newArrayList();
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        if (parameter != null) {
            statement.setString(1, parameter);
        }
        Book.Builder builder = new Book.Builder();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Book book = builder.author(resultSet.getString("AUTHOR"))
                    .title(resultSet.getString("TITLE"))
                    .publisher(resultSet.getString("PUBLISHER"))
                    .isbn(resultSet.getString("ISBN"))
                    .price(resultSet.getDouble("PRICE")).build();

            books.add(book);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return books;
    }
}
