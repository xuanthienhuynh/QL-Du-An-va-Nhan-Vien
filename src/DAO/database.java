/*
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
   
            closeConnection(c);
        }
    }
} */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    // 1. Khai báo địa chỉ các Server
    private static final String SERVER_CHINH = "jdbc:sqlserver://localhost:1433;";
    private static final String SERVER_NODE1 = "jdbc:sqlserver://localhost:1434;";

    // 2. Biến static lưu URL kết nối hiện tại (Mặc định là server chính)
    public static String currentUrl = SERVER_CHINH;

    /*
     * public static Connection createConnection() {
     * try {
     * Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
     * // 3. Sử dụng currentUrl động thay vì fix cứng
     * String url = currentUrl +
     * "databaseName=QL_DuAn_CongTy;encrypt=true;trustServerCertificate=true";
     * return DriverManager.getConnection(url, "sa", "123");
     * } catch (Exception e) {
     * e.printStackTrace();
     * return null;
     * }
     * }
     */

    public static Connection createConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = currentUrl + "databaseName=QL_DuAn_CongTy;encrypt=true;trustServerCertificate=true";

            // Thêm Timeout ngắn (ví dụ 3 giây) để nếu không thấy NODE1 thì báo lỗi ngay
            // thay vì treo máy
            DriverManager.setLoginTimeout(3);

            return DriverManager.getConnection(url, "sa", "123");
        } catch (Exception e) {
            // Nếu kết nối vào NODE1 thất bại, tự động bẻ lái về Server chính để các bạn
            // khác vẫn dùng được
            System.err.println("Không tìm thấy Node chi nhánh, đang kết nối về Server chính...");
            String backupUrl = "jdbc:sqlserver://localhost:1433;databaseName=QL_DuAn_CongTy;encrypt=true;trustServerCertificate=true";
            try {
                return DriverManager.getConnection(backupUrl, "sa", "123");
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static void closeConnection(Connection sql) {
        try {
            if (sql != null)
                sql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}