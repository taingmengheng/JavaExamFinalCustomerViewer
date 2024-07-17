import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustomerViewer extends JFrame implements ActionListener {
    private JTextField idField, lastNameField, firstNameField, phoneField;
    private JButton prevButton, nextButton;
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private int currentIndex;

    public CustomerViewer() {

        setTitle("Customer Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);

        idField = new JTextField(10);
        idField.setEditable(false);
        gbc.gridx = 1;
        add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Last Name:"), gbc);

        lastNameField = new JTextField(10);
        lastNameField.setEditable(false);
        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("First Name:"), gbc);

        firstNameField = new JTextField(10);
        firstNameField.setEditable(false);
        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Phone:"), gbc);

        phoneField = new JTextField(10);
        phoneField.setEditable(false);
        gbc.gridx = 1;
        add(phoneField, gbc);

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(prevButton, gbc);

        gbc.gridx = 1;
        add(nextButton, gbc);

        prevButton.addActionListener(this);
        nextButton.addActionListener(this);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CustomerDB", "TaingmengHeng", "Meng@1234");
            stmt = conn.prepareStatement("SELECT * FROM Customer", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery();
            if (rs.next()) {
                currentIndex = 1;
                showRecord();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setSize(300, 250);
        setVisible(true);
    }

    private void showRecord() throws SQLException {
        rs.absolute(currentIndex);
        idField.setText(rs.getString("customer_id"));
        lastNameField.setText(rs.getString("customer_last_name"));
        firstNameField.setText(rs.getString("customer_first_name"));
        phoneField.setText(rs.getString("customer_phone"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == prevButton && rs.previous()) {
                currentIndex--;
                showRecord();
            } else if (e.getSource() == nextButton && rs.next()) {
                currentIndex++;
                showRecord();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerViewer();
    }
}
