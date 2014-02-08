package bbejeck.guava.common.service;

import bbejeck.guava.common.model.TradeAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Bill Bejeck
 * Date: 4/20/13
 * Time: 11:48 PM
 */
public class TradeAccountService extends BaseDBService {

    private static final String insertSql = " CREATE TABLE IF NOT EXISTS TRADE_ACCOUNT(ID VARCHAR(255) PRIMARY KEY, OWNER VARCHAR(255)," +
            " BALANCE DOUBLE) AS SELECT * FROM CSVREAD('src/main/resources/trade_accounts.csv')";


    public TradeAccountService() {
        try {
            init(insertSql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() throws Exception {
        shutDown();
    }

    public TradeAccount getTradeAccountById(String id) {
        try {
            TradeAccount tradeAccount = getTradeAccount("Select * from trade_account where id=?", id);
            if (tradeAccount == null) {
                tradeAccount = new TradeAccount("NULL", "NULL", 0.01);
            }
            return tradeAccount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private TradeAccount getTradeAccount(String sql, String parameter) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, parameter);
        ResultSet rs = statement.executeQuery();
        TradeAccount tradeAccount = null;
        if (rs.next()) {
            tradeAccount = new TradeAccount(rs.getString("id"), rs.getString("owner"), rs.getDouble("balance"));
        }

        rs.close();
        statement.close();
        connection.close();

        return tradeAccount;
    }
}
