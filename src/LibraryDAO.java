import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class LibraryDAO {

    // ---------------- ADD BOOK ----------------
    public boolean addBook(int id, String title, String author, int qty) {
        String sql = "INSERT INTO books(book_id, title, author, quantity) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setInt(4, qty);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- ADD STUDENT ----------------
    public boolean addStudent(int id, String name, String dept) {
        String sql = "INSERT INTO students(student_id, student_name, department) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- LOGIN ----------------
    public boolean validateUser(String user, String pass) {
        String sql = "SELECT 1 FROM users WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- ISSUE BOOK (TRANSACTION) ----------------
    public boolean issueBook(int bookId, int studentId) {
        String checkQty = "SELECT quantity FROM books WHERE book_id=?";
        String issueSql = "INSERT INTO issued_books(book_id, student_id, issue_date) VALUES (?, ?, CURDATE())";
        String updateBook = "UPDATE books SET quantity = quantity - 1 WHERE book_id=?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false); // 🔴 START TRANSACTION

            PreparedStatement psCheck = con.prepareStatement(checkQty);
            psCheck.setInt(1, bookId);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next() || rs.getInt(1) <= 0) {
                return false; // book not available
            }

            PreparedStatement psIssue = con.prepareStatement(issueSql);
            psIssue.setInt(1, bookId);
            psIssue.setInt(2, studentId);
            psIssue.executeUpdate();

            PreparedStatement psUpdate = con.prepareStatement(updateBook);
            psUpdate.setInt(1, bookId);
            psUpdate.executeUpdate();

            con.commit(); // ✅ END TRANSACTION
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- RETURN BOOK (TRANSACTION) ----------------
    public boolean returnBook(int bookId, int studentId) {
        String deleteIssue = "DELETE FROM issued_books WHERE book_id=? AND student_id=?";
        String updateBook = "UPDATE books SET quantity = quantity + 1 WHERE book_id=?";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            PreparedStatement psDelete = con.prepareStatement(deleteIssue);
            psDelete.setInt(1, bookId);
            psDelete.setInt(2, studentId);
            int rows = psDelete.executeUpdate();

            if (rows == 0) return false; // no record found

            PreparedStatement psUpdate = con.prepareStatement(updateBook);
            psUpdate.setInt(1, bookId);
            psUpdate.executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- FETCH BOOKS FOR JTable ----------------
    public DefaultTableModel getBooksTableModel() {
        String[] cols = {"Book ID", "Title", "Author", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        String sql = "SELECT * FROM books";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("quantity")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}
