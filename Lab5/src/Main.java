import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//TODO: new edit text and button; read from file to edit text

public class Main extends JFrame {
    private Vector<String> stock = new Vector<>();
    private Vector<String> customer = new Vector<>();

    private JLabel onStockLb = new JLabel("Salon");
    private JLabel customerLb = new JLabel("Customers");
    private JList<String> stockJList = new JList<>(stock);
    private JList<String> customerJList = new JList<>(customer);
    private JButton addBtn = new JButton("+");
    private JButton removeBtn = new JButton("-");
    private JButton saveBtn = new JButton("Save");
    private JCheckBox updateJCheckBox = new JCheckBox("Update");
    private JTextField customerTF = new JTextField("Customer");
    private JTextField timeTF;
    private JTextArea clientAddressTA = new JTextArea("City Street House");

    private JTextArea toReadFromFileTA = new JTextArea();
    private JButton readFromFileBtn = new JButton("Read");


    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    private Main() {

        super("Some kind of Salon");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        timeTF = new JTextField(formatter.format(date));

        initWindow();

        readFromFileBtn.addActionListener(e -> {
            //TODO
            getFromFile().forEach(
                    s -> {
            toReadFromFileTA.append(s + "\n");
                });
        });

        removeBtn.addActionListener(e -> {
            customer.remove(customerJList.getSelectedIndex());
            customerJList.setListData(customer);
        });

        addBtn.addActionListener(e -> {
            customer.add(stockJList.getSelectedValue());
            customerJList.setListData(customer);
        });

        updateJCheckBox.addActionListener(e -> {
            if (updateJCheckBox.isSelected()) {
                clientAddressTA.setEnabled(true);
                customerTF.setEnabled(true);
                timeTF.setEnabled(true);
            } else {
                clientAddressTA.setEnabled(false);
                customerTF.setEnabled(false);
                timeTF.setEnabled(false);
            }
        });

        saveBtn.addActionListener(e -> {
            saveOrderToFile();
        });

        getItemsFromStockFile();

    }

    @SuppressWarnings("Duplicates")
    private void initWindow() {
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        getContentPane().add(onStockLb);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, onStockLb, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, onStockLb, 10, SpringLayout.NORTH, getContentPane());
        //------------------

        toReadFromFileTA.setPreferredSize(new Dimension(300, 300));
        toReadFromFileTA.setLineWrap(true);
        getContentPane().add(toReadFromFileTA);

       /* springLayout.putConstraint(SpringLayout.WEST, toReadFromFileTA , 50, SpringLayout.EAST, customerTF);
        springLayout.putConstraint(SpringLayout.NORTH, toReadFromFileTA, 20, SpringLayout.SOUTH, updateJCheckBox);*/

        readFromFileBtn.setPreferredSize(new Dimension(50, 20));
        getContentPane().add(readFromFileBtn);
        springLayout.putConstraint(SpringLayout.WEST, readFromFileBtn, 30, SpringLayout.SOUTH, toReadFromFileTA);


        //----------------------

        stockJList.setFixedCellWidth(250);
        getContentPane().add(stockJList);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, stockJList, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, stockJList, 10, SpringLayout.SOUTH, onStockLb);

        getContentPane().add(customerLb);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, customerLb, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, customerLb, 50, SpringLayout.SOUTH, stockJList);

        customerJList.setFixedCellWidth(250);
        getContentPane().add(customerJList);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, customerJList, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, customerJList, 10, SpringLayout.SOUTH, customerLb);

        addBtn.setPreferredSize(new Dimension(50, 20));
        getContentPane().add(addBtn);
        springLayout.putConstraint(SpringLayout.WEST, addBtn, 0, SpringLayout.WEST, customerJList);
        springLayout.putConstraint(SpringLayout.NORTH, addBtn, 0, SpringLayout.SOUTH, customerJList);

        removeBtn.setPreferredSize(new Dimension(50, 20));
        getContentPane().add(removeBtn);
        springLayout.putConstraint(SpringLayout.WEST, removeBtn, 0, SpringLayout.EAST, addBtn);
        springLayout.putConstraint(SpringLayout.NORTH, removeBtn, 0, SpringLayout.SOUTH, customerJList);

        getContentPane().add(updateJCheckBox);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, updateJCheckBox, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, updateJCheckBox, 50, SpringLayout.SOUTH, removeBtn);

        customerTF.setPreferredSize(new Dimension(200, 20));
        customerTF.setEnabled(false);
        getContentPane().add(customerTF);
        springLayout.putConstraint(SpringLayout.WEST, customerTF, 50, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, customerTF, 20, SpringLayout.SOUTH, updateJCheckBox);

        timeTF.setPreferredSize(new Dimension(200,20));
        timeTF.setEnabled(false);
        getContentPane().add(timeTF);
        springLayout.putConstraint(SpringLayout.WEST, timeTF, 50, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, timeTF, 20, SpringLayout.SOUTH, customerTF);

        clientAddressTA.setPreferredSize(new Dimension(200, 100));
        clientAddressTA.setEnabled(false);
        getContentPane().add(clientAddressTA);
        springLayout.putConstraint(SpringLayout.WEST, clientAddressTA, 50, SpringLayout.EAST, customerTF);
        springLayout.putConstraint(SpringLayout.NORTH, clientAddressTA, 20, SpringLayout.SOUTH, updateJCheckBox);

        getContentPane().add(saveBtn);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, saveBtn, 0, SpringLayout.HORIZONTAL_CENTER, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, saveBtn, 20, SpringLayout.SOUTH, clientAddressTA);


        setSize(900, 900);



    }

    private void getItemsFromStockFile() {
        try {
            File file = new File("customers.txt");
            if(!file.exists()) file.createNewFile();
            else {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    stock.add(data);
                }
                scanner.close();
                stockJList.setListData(stock);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getFromFile()
    {
        ArrayList<String> strings = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("customers.txt"));
            while (scanner.hasNextLine()) {
                strings.add(scanner.nextLine());
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return strings;
    }

    private void saveOrderToFile() {
        try {

            FileWriter writer = new FileWriter("customers.txt",true);
            writer.write("Customer name: " + customerTF.getText() + "\n");
            writer.write("Address: " + clientAddressTA.getText() + "\n");
            writer.write("Time: " + timeTF.getText() + "\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}