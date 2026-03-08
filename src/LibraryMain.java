import java.util.Scanner;

public class LibraryMain {
    public static void main(String[] args) {

        LibraryDAO dao = new LibraryDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Add Book  2.Add Student  3.Show Books");
            System.out.println("4.Issue Book  5.Return Book  6.Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (ch) {

                case 1:
                    System.out.print("Enter Book ID: ");
                    int bid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    dao.addBook(bid, title, author, qty);
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Student Name: ");
                    String sname = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();

                    dao.addStudent(sid, sname, dept);
                    break;

                case 3:
                    dao.showBooks();
                    break;

                case 4:
                    System.out.print("Enter Book ID: ");
                    int issueBookId = sc.nextInt();

                    System.out.print("Enter Student ID: ");
                    int issueStudentId = sc.nextInt();

                    dao.issueBook(issueBookId, issueStudentId);
                    break;

                case 5:
                    System.out.print("Enter Book ID: ");
                    int returnBookId = sc.nextInt();

                    System.out.print("Enter Student ID: ");
                    int returnStudentId = sc.nextInt();

                    dao.returnBook(returnBookId, returnStudentId);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
