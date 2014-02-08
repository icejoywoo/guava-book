package bbejeck.guava.common.service;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: Bill Bejeck
 * Date: 4/22/13
 * Time: 10:28 PM
 */
public abstract class BaseDBService {

    protected JdbcConnectionPool connectionPool;
    protected Server dbServer;
    private static final String dbUrl = "jdbc:h2:mem:test";


    protected void init(String insertSql) throws Exception {

        this.startH2();
        this.createConnectionPool();
        this.executeSql(insertSql);
    }

    protected void shutDown() throws Exception {
        stopH2();
        closeConnectionPool();
    }

    private void executeSql(String sql) throws SQLException {
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
        connection.close();
    }

    private void startH2() throws Exception {
        dbServer = Server.createTcpServer();
        dbServer.start();
    }

    private void stopH2() throws Exception {
        dbServer.stop();
    }

    private void createConnectionPool() throws Exception {
        Class.forName("org.h2.Driver");
        connectionPool =  JdbcConnectionPool.create(dbUrl, "sa", "sa");
    }

    private void closeConnectionPool() throws Exception {
        connectionPool.dispose();
    }
}
