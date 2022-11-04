import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class carGUI extends JFrame{
    private JPanel panelWithLabel;
    private JLabel Label;
    private JTextField vin;
    private JTextField brand;
    private JTextField model;
    private JTextField fuel;
    private JTextField horsePower;
    private JTextField transmission;

    private JTextField enterDate;
    private JButton RESETButton;
    private JButton EXITButton;
    private JButton DELETEButton;
    private JButton UPDATEButton;
    private JButton ADDButton;

    private JButton checkMechanics;
    private JTable table;
    private JPanel mainPanel;
    private Connection connect;
    private PreparedStatement statement;


    public carGUI(){
        super("CarSetupMenu");
        this.setContentPane(this.mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.connection();
        this.showTable();

        RESETButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    setToNull();
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null,e1.getMessage());
                }
            }
        });

        EXITButton.addActionListener(new ActionListener() {
            private JFrame frame;
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                if(JOptionPane.showConfirmDialog(frame,"Do you want to close the application?","EXIT",
                        JOptionPane.YES_NO_OPTION)==0){
                    System.exit(0);
                }
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null,e1.getMessage());
                }
            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String setBrand,setEnterDate,setModel,setFuel,setTransmission,setVin,setHorsepower;
                final String insert = "insert into carInfo(vin,enterDate,brand,model,fuel,horsepower,transmission)" +
                        "values(?,?,?,?,?,?,?)";
                setBrand = brand.getText();
                setEnterDate = enterDate.getText();
                setModel = model.getText();
                setFuel = fuel.getText();
                setTransmission = transmission.getText();
                setVin = vin.getText();
                setHorsepower = horsePower.getText();
                try{
                    statement = connect.prepareStatement(insert);
                    statement.setString(1,setVin);
                    statement.setString(2,setEnterDate);
                    statement.setString(3,setBrand);
                    statement.setString(4,setModel);
                    statement.setString(5,setFuel);
                    statement.setString(6,setHorsepower);
                    statement.setString(7,setTransmission);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record was added successfully!");
                    showTable();
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null,e1.getMessage());
                }
            }
        });
        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String update = "update carInfo set enterDate = ?,brand = ?,model = ?,fuel = ?,horsepower = ?,transmission = ?" +
                        " where vin = ?" ;
                String setEnterDate,setBrand,setModel,setFuel,setTransmission,setVin,setHorsepower;
                setEnterDate = enterDate.getText();
                setBrand = brand.getText();
                setModel = model.getText();
                setFuel = fuel.getText();
                setTransmission = transmission.getText();
                setVin = vin.getText();
                setHorsepower = horsePower.getText();

                try {
                    if(!setVin.equals(connect.prepareStatement("select vin from carInfo"))){
                     JOptionPane.showMessageDialog(null,"VIN not available!");
                    }else {
                        statement = connect.prepareStatement(update);
                        statement.setString(1,setEnterDate);
                        statement.setString(2, setBrand);
                        statement.setString(3, setModel);
                        statement.setString(4, setFuel);
                        statement.setString(5, setHorsepower);
                        statement.setString(6, setTransmission);
                        statement.setString(7, setVin);
                        statement.executeUpdate();
                        showTable();
                    }

                }catch(SQLException e1){
                    JOptionPane.showMessageDialog(null,e1.getMessage());
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String delete = "delete from carInfo where vin = ?";
                String getVin = vin.getText();
                try{
                    if(!getVin.equals(connect.prepareStatement("select vin from carInfo"))){
                        JOptionPane.showMessageDialog(null,"Wrong input!");
                    }else {
                        statement = connect.prepareStatement(delete);
                        statement.setString(1, getVin);
                        statement.executeUpdate();
                        showTable();
                    }

                }catch(SQLException e1){
                    JOptionPane.showMessageDialog(null,e1.getMessage());
                }
            }
        });

        checkMechanics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    mechanicsGUI mechanics = new mechanicsGUI();
                    mechanics.setVisible(true);
                    setVisible(false);
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null,e1.getMessage());
                }
            }

        });
    }
    public void connection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connect = DriverManager.getConnection("jdbc:mysql://localhost/car", "root", "0144259040eE");
            System.out.println("Connection successfully!");
        }catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null,"ERROR");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

    }
    private void setToNull(){
        vin.setText(null);
        brand.setText(null);
        model.setText(null);
        fuel.setText(null);
        horsePower.setText(null);
        transmission.setText(null);
    }

    private void showTable(){
        final String show = "select * from carInfo";
        try{
            statement = connect.prepareStatement(show);
            ResultSet result = statement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(result));

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    private void setColumns(){
        String setBrand,setModel,setFuel,setTransmission,setVin,setHorsepower;
        setBrand = brand.getText();
        setModel = model.getText();
        setFuel = fuel.getText();
        setTransmission = transmission.getText();
        setVin = vin.getText();
        setHorsepower = horsePower.getText();
    }


}

