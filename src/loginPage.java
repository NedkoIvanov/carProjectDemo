import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class loginPage extends JFrame{
    private JTextField userName;
    private JTextField pass;
    private JButton SUBMITButton;
    private JButton CANCELButton;
    private JPanel mainPanell;
    private Connection connect;


    public loginPage(){
     super("login");
     this.setContentPane(this.mainPanell);
     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     this.pack();

        SUBMITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String name = userName.getText();
              String password = pass.getText();
              connection(name,password);

            }
        });


        CANCELButton.addActionListener(new ActionListener() {
            private JFrame frame;
            @Override
            public void actionPerformed(ActionEvent e) {

                if(JOptionPane.showConfirmDialog(frame,"Do you want to cancel registration?","Cancel"
                ,JOptionPane.YES_NO_OPTION)==0){
                    System.exit(1);
                }
            }
        });
    }

    public void connection(String username , String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connect = DriverManager.getConnection("jdbc:mysql://localhost/car", username,password);
            System.out.println("Connection successfully!");
            if(!connect.isClosed()) {
                carGUI car = new carGUI();
                if(car!=null){
                    car.setVisible(true);
                    this.setVisible(false);

                }
            }


        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"ERROR");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

    }

}
