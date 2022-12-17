package ru.netology.diplom.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {}

    @SneakyThrows //MySQL
    private static Connection getConn() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

//    @SneakyThrows //PostgreSQL
//    private static Connection getConn() {
//        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/kuularbase", "kuular", "12345v");
//    }
//            order_entity

    public static String getCardStatusWhenPayment() {
        var statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, statusSQL, new ScalarHandler<String>());
            return result;
        } catch (SQLException exception) {
            System.err.println("Транзакция не прошла");
        }
        return null;
    }

    public static String getCardStatusWhenCredit() {
        var statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var result = runner.query(conn, statusSQL, new ScalarHandler<String>());
            return result;
        } catch (SQLException exception) {
            System.err.println("Транзакция не прошла");
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var conn = getConn();
        runner.execute(conn, "DELETE FROM payment_entity");
        runner.execute(conn, "DELETE FROM credit_request_entity");
    }
}
