# 🚀 D-Inventory: Hệ thống Quản trị Doanh nghiệp Phân tán & BI Dashboard

**D-Inventory** là hệ thống quản lý nhân sự và dự án được thiết kế trên kiến trúc **Cơ sở dữ liệu phân tán (Distributed Database)** kết hợp với hệ thống **Business Intelligence (BI)**. Dự án tập trung giải quyết bài toán tối ưu hóa truy xuất dữ liệu tại địa phương và trực quan hóa dữ liệu hỗ trợ cấp lãnh đạo ra quyết định.

---

## 🏗️ Kiến trúc Hệ thống (Architecture)

Hệ thống được xây dựng trên mô hình **Publisher - Subscriber** kết nối với nền tảng phân tích dữ liệu:
- **Trụ sở chính (Publisher):** Lưu trữ toàn bộ dữ liệu (Global Schema). Đóng vai trò là Data Source tập trung cho hệ thống báo cáo.
- **Các Chi nhánh (Subscriber):** HCM, Hà Nội, Đà Nẵng - Quản lý phân mảnh dữ liệu nội bộ của từng khu vực.
- **BI Layer:** Power BI kết nối trực tiếp (DirectQuery/Import) vào cơ sở dữ liệu Trụ sở để trích xuất Insight theo thời gian thực.

### 🔄 Cơ chế Đồng bộ
- **Công nghệ:** SQL Server Merge Replication.
- **Đặc điểm:** Cho phép cập nhật dữ liệu 2 chiều (Bi-directional). Giải quyết xung đột (Conflict Resolution) theo cơ chế Winner-Takes-All, ưu tiên dữ liệu tại Publisher.

---

## 📊 Mô hình Phân mảnh (Fragmentation Strategy)

Hệ thống ứng dụng **Phân mảnh dẫn xuất nối tiếp (Cascading Derived Fragmentation)** để triệt tiêu dị thường dữ liệu và đảm bảo tính toàn vẹn tham chiếu tuyệt đối:

1. **Bảng PHONGBAN (Primary Horizontal Fragmentation):** Phân mảnh theo chi nhánh.
2. **Bảng NHANVIEN (Derived Fragmentation - Level 1):** Dẫn xuất từ bảng Phòng Ban qua phép Nửa kết (Semi-join).
3. **Bảng PHANCONG (Cascading Derived - Level 2):** Dẫn xuất từ bảng Nhân Viên. Đảm bảo dữ liệu phân công đi liền với nhân sự địa phương.
4. **Bảng DUAN (Full Replication):** Nhân bản toàn phần để mọi chi nhánh đều có thể theo dõi dự án chung.

---

## ✨ Tính năng nổi bật

- **Business Intelligence Dashboard:** Tích hợp Microsoft Power BI để xây dựng hệ thống báo cáo động dành riêng cho Giám đốc. Trực quan hóa bức tranh tài chính (Lãi/Lỗ), hiệu suất nhân sự và tiến độ dự án trên toàn quốc một cách trực quan.
- **Modern UI/UX:** Giao diện Java Swing dạng thẻ (Card-based Layout) kết hợp Custom Components, tối ưu hóa trải nghiệm người dùng.
- **Smart Filters & Real-time Search:** Tìm kiếm thông tin nội bộ thời gian thực. Hệ thống tự động phân loại dự án Đang chạy/Đã kết thúc bằng logic SQL thông minh.
- **Intelligent Business Logic:**
  - Tự động khóa dự án (Read-only) khi quá hạn (Deadline-based Auto-lock).
  - Tự động chặn và cảnh báo vi phạm khóa ngoại trên các Node phân tán.

---

## 🛠️ Công nghệ sử dụng (Tech Stack)

- **Ngôn ngữ:** Java (JDK 17)
- **Giao diện:** Java Swing (Custom Graphics2D Component)
- **Cơ sở dữ liệu:** Microsoft SQL Server
- **Data Analytics & Visualization:** Microsoft Power BI
- **Kết nối DB:** JDBC (SQL Server Native Driver)
- **Kiến trúc mã nguồn:** Mô hình 3 lớp (GUI - BUS - DAO)

---

## ⚙️ Hướng dẫn Cài đặt (Setup)

### 1. Cấu hình Cơ sở dữ liệu & Báo cáo
1. Thực thi Script `QLDA&PC.sql` tại máy chủ. Cấu hình các Node Chi nhánh qua Merge Replication.
2. Mở file `QLNV&DA.pbix` bằng Power BI Desktop.
3. Chỉnh sửa Data Source Settings để trỏ về đúng IP của SQL Server Máy chủ và refresh dữ liệu.

### 2. Cấu hình Ứng dụng
1. Clone repository này về máy.
2. Mở dự án bằng NetBeans IDE hoặc VS Code.
3. Import thư viện `mssql-jdbc.jar` vào project.
4. Cập nhật chuỗi kết nối trong file `Database.java` cho phù hợp với thông tin Server của bạn:
   ```java
   String url = "jdbc:sqlserver://localhost:1433;databaseName=QL_VatTU;user=sa;password=your_password";