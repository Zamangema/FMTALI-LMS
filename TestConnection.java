package com.fmtali.lms;

import com.fmtali.lms.util.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("SUCCESS — Java is connected to PostgreSQL!");
        } else {
            System.out.println("FAILED — Check your password and PostgreSQL is running.");
        }
    }
}