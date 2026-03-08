import javax.swing.*;

public class LoginGUI extends JFrame {

    LibraryDAO dao = new LibraryDAO();

    public LoginGUI() {
        setTitle("Library Login");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField();
        JButton btn = new JButton("Login");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Username"));
        add(user);
        add(new JLabel("Password"));
        add(pass);
        add(btn);

        btn.addActionListener(e -> {
            if (dao.validateUser(user.getText(), new String(pass.getPassword()))) {
                dispose();
                new LibraryGUI().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }
        });
    }

    public static void main(String[] args) {
        new LoginGUI().setVisible(true);
    }
}
