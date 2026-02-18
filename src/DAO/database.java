package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database { // Mình đổi tên class thành ConnectDB cho chuyên nghiệp hơn chút

    public static Connection createConnection() {
        try {
            // 1. Nạp Driver (Bước này quan trọng để máy hiểu cách nói chuyện với SQL)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // 2. Cấu hình chuỗi kết nối
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QL_DuAn_CongTy;encrypt=true;trustServerCertificate=true";

            // 3. Tài khoản SQL (Kiểm tra lại xem máy bạn có đúng pass là 123 không nhé)
            String username = "sa";
            String password = "123";

            // 4. Mở kết nối
            Connection sql = DriverManager.getConnection(url, username, password);
            return sql;

        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Quên chưa add thư viện sql-jdbc.jar vào project rồi!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("Lỗi: Sai tên DB, sai User/Pass hoặc chưa bật TCP/IP!");
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection sql) {
        try {
            if (sql != null) {
                sql.close();
                System.out.println("Đã đóng kết nối!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm main để chạy thử xem kết nối được chưa
    public static void main(String[] args) {
        Connection c = createConnection();
        if (c != null) {
            // Nếu chạy ra dòng này là ngon lành
            closeConnection(c);
        }
    }
}