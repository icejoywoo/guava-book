package bbejeck.guava.common;

import bbejeck.guava.common.support.database.SampleDBService;
import bbejeck.guava.common.support.lucene.SampleLuceneIndexBuilder;
import bbejeck.guava.common.support.lucene.SampleLuceneSearcher;
import org.apache.lucene.store.RAMDirectory;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
* Created by IntelliJ IDEA.
* User: bbejeck
*/

public abstract class SearchingTestBase {


    private static final String dbUrl = "jdbc:h2:mem:test";
    private static final String insertSql = " CREATE TABLE IF NOT EXISTS PERSON(FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255)," +
            " ADDRESS VARCHAR(255), EMAIL VARCHAR(255),ID INT PRIMARY KEY) AS SELECT * FROM CSVREAD('src/main/resources/names.csv')";
    protected static JdbcConnectionPool connectionPool;
    private static Server dbServer;
    private static SampleLuceneIndexBuilder luceneIndexBuilder;
    protected static SampleLuceneSearcher luceneSearcher;
    protected static SampleDBService dbService;

    @BeforeClass
    public static void setUpBeforeAllTests() throws Exception {
        startH2();
        setUpIndex();
        createConnectionPool();
        populateDb();
        dbService = new SampleDBService(connectionPool);
    }

    @AfterClass
    public static void shutDownAfterAllTests() throws Exception {
        stopH2();
        closeConnectionPool();
        luceneSearcher.shutDown();
        dbService.shutDown();
    }

    private static void populateDb() throws SQLException {
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(insertSql);
        statement.close();
        connection.close();
    }

    private static void setUpIndex() throws IOException {
        String filePath = "src/main/resources/names.csv";
        luceneIndexBuilder = new SampleLuceneIndexBuilder(filePath);
        RAMDirectory ramDirectory = luceneIndexBuilder.buildIndex();
        luceneSearcher = new SampleLuceneSearcher(ramDirectory);
    }

    private static void startH2() throws Exception {
        dbServer = Server.createTcpServer();
        dbServer.start();
    }

    private static void stopH2() throws Exception {
        dbServer.stop();
    }

    private static void createConnectionPool() throws Exception {
        Class.forName("org.h2.Driver");
        connectionPool =  JdbcConnectionPool.create(dbUrl, "sa", "sa");
    }

    private static void closeConnectionPool() throws Exception {
        connectionPool.dispose();
    }
}
