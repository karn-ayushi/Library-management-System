import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryGUI extends JFrame {

    LibraryDAO dao = new LibraryDAO();

    // Book fields
    JTextField bookIdField, titleField, authorField, qtyField;

    // Student fields
    JTextField studentIdField, studentNameField, deptField;

    // Issue / Return fields
    JTextField issueBookIdField, issueStudentIdField;
    JTextField returnBookIdField, returnStudentIdField;

    JTable bookTable;
    DefaultTableModel tableModel;

    public LibraryGUI() {

        setTitle("Library Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Add Book", createAddBookPanel());
        tabs.add("Add Student", createAddStudentPanel());
        tabs.add("Issue Book", createIssuePanel());
        tabs.add("Return Book", createReturnPanel());
        tabs.add("Show Books", createShowBooksPanel());

        add(tabs);
        setVisible(true);
    }

    // ---------------- ADD BOOK ----------------
    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        bookIdField = new JTextField();
        titleField = new JTextField();
        authorField = new JTextField();
        qtyField = new JTextField();

        JButton btnAdd = new JButton("Add Book");

        panel.add(new JLabel("Book ID:"));
        panel.add(bookIdField);
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);
        panel.add(new JLabel(""));
        panel.add(btnAdd);

        btnAdd.addActionListener(e -> addBook());

        return panel;
    }

    // ---------------- ADD STUDENT ----------------
    private JPanel createAddStudentPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        studentIdField = new JTextField();
        studentNameField = new JTextField();
        deptField = new JTextField();

        JButton btnAdd = new JButton("Add Student");

        panel.add(new JLabel("Student ID:"));
        panel.add(studentIdField);
        panel.add(new JLabel("Student Name:"));
        panel.add(studentNameField);
        panel.add(new JLabel("Department:"));
        panel.add(deptField);
        panel.add(new JLabel(""));
        panel.add(btnAdd);

        btnAdd.addActionListener(e -> addStudent());

        return panel;
    }

    // ---------------- ISSUE BOOK ----------------
    private JPanel createIssuePanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        issueBookIdField = new JTextField();
        issueStudentIdField = new JTextField();

        JButton btnIssue = new JButton("Issue Book");

        panel.add(new JLabel("Book ID:"));
        panel.add(issueBookIdField);
        panel.add(new JLabel("Student ID:"));
        panel.add(issueStudentIdField);
        panel.add(new JLabel(""));
        panel.add(btnIssue);

        btnIssue.addActionListener(e -> issueBook());

        return panel;
    }

    // ---------------- RETURN BOOK ----------------
    private JPanel createReturnPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        returnBookIdField = new JTextField();
        returnStudentIdField = new JTextField();

        JButton btnReturn = new JButton("Return Book");

        panel.add(new JLabel("Book ID:"));
        panel.add(returnBookIdField);
        panel.add(new JLabel("Student ID:"));
        panel.add(returnStudentIdField);
        panel.add(new JLabel(""));
        panel.add(btnReturn);

        btnReturn.addActionListener(e -> returnBook());

        return panel;
    }

    // ---------------- SHOW BOOKS ----------------
    private JPanel createShowBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        tableModel = dao.getBooksTableModel();
        bookTable = new JTable(tableModel);

        JButton refreshBtn = new JButton("Refresh");

        refreshBtn.addActionListener(e -> refreshTable());

        panel.add(new JScrollPane(bookTable), BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    // ================= BACKEND CONNECTION METHODS =================

    private void addBook() {
        try {
            int id = Integer.parseInt(bookIdField.getText());
            int qty = Integer.parseInt(qtyField.getText());

            boolean success = dao.addBook(
                    id,
                    titleField.getText(),
                    authorField.getText(),
                    qty
            );

            JOptionPane.showMessageDialog(this,
                    success ? "Book Added Successfully!" : "Failed to add book");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Book Data");
        }
    }

    private void addStudent() {
        try {
            int id = Integer.parseInt(studentIdField.getText());

            boolean success = dao.addStudent(
                    id,
                    studentNameField.getText(),
                    deptField.getText()
            );

            JOptionPane.showMessageDialog(this,
                    success ? "Student Added Successfully!" : "Failed to add student");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Student Data");
        }
    }

    private void issueBook() {
        try {
            int bookId = Integer.parseInt(issueBookIdField.getText());
            int studentId = Integer.parseInt(issueStudentIdField.getText());

            boolean success = dao.issueBook(bookId, studentId);

            JOptionPane.showMessageDialog(this,
                    success ? "Book Issued Successfully!" : "Issue Failed");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    private void returnBook() {
        try {
            int bookId = Integer.parseInt(returnBookIdField.getText());
            int studentId = Integer.parseInt(returnStudentIdField.getText());

            boolean success = dao.returnBook(bookId, studentId);

            JOptionPane.showMessageDialog(this,
                    success ? "Book Returned Successfully!" : "Return Failed");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        DefaultTableModel newModel = dao.getBooksTableModel();
        for (int i = 0; i < newModel.getRowCount(); i++) {
            tableModel.addRow(newModel.getDataVector().get(i));
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        new LibraryGUI();
    }
}
