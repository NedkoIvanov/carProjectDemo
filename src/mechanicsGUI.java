import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class mechanicsGUI extends JFrame{
    private JTextField id;
    private JButton check;

    private JButton returnButton;
    private JButton remove;
    private JButton exit;
    private JTable table1;
    private JPanel mainPanell;
    private Connection connect;
    private PreparedStatement statement;

    public mechanicsGUI(){
        super("mechanicsCheck");
        this.setContentPane(this.mainPanell);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.connection();

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 showTable();
            }
        });



        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carGUI car = new carGUI();
                car.setVisible(true);
                setVisible(false);
            }
        });

        exit.addActionListener(new ActionListener() {
            JFrame jframe;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(jframe,"Do you want to exit?","EXIT"
                ,JOptionPane.YES_NO_OPTION)==0){
                    System.exit(1);
                }
            }
        });
    }

    public void connection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/car", "root", "0144259040eE");
        }catch(ClassNotFoundException e1){
            JOptionPane.showMessageDialog(null,e1.getMessage());
        }catch(SQLException e2){
            JOptionPane.showMessageDialog(null,e2.getMessage());
        }

    }

    public void showTable(){
        final String execute = "select mechanics.firstName,mechanics.secondName,mechanics.phoneNumber,carInfo.model,carInfo.enterDate" +
                " from mechanics join carInfo" +
                " on mechanics.specialBrand = carInfo.brand" +
                " where specialBrand = ?" +
                " order by carInfo.enterDate";
        String thisBrand = id.getText();
        try{
            statement = connect.prepareStatement(execute);
            statement.setString(1,thisBrand);
            ResultSet rs = statement.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            JOptionPane.showMessageDialog(null,"Each car is ready from one to five days!");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }


}
