package Panels;

import DBconnection.DBConnection;
import Panels.ScreeningPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;



public class MoviePanel extends JPanel {

    static int idMovie = -1;

    static Connection conn = null;
    static PreparedStatement state = null;
    static ResultSet result = null;
    static JTable table = new JTable();
    JScrollPane scroll = new JScrollPane(table);

    JButton addButton = new JButton("Добави филм");
    JButton deleteButton = new JButton("Изтрий маркирания филм");
    JPanel firstPanel = new JPanel();
    JPanel secondPanel = new JPanel();
    JPanel thirdPanel = new JPanel();

    JLabel nameLabel = new JLabel("Име на филм: ");
    JLabel label = new JLabel("Филми: ");
    JTextField nameTF = new JTextField("",20)    ;


    public MoviePanel() {
        this.setLayout(new GridLayout(3, 1));

        //first panel
       firstPanel.setLayout(new FlowLayout());
        this.add(firstPanel);
        firstPanel.add(nameLabel);
        firstPanel.add(nameTF);

        //second panel
        secondPanel.setLayout(new FlowLayout());
        secondPanel.add(addButton);
        addButton.addActionListener(new addAction());
        secondPanel.add(deleteButton);
        deleteButton.addActionListener(new deleteAction());
        this.add(secondPanel);

        //third panel
        this.add(thirdPanel);
        thirdPanel.add(label);
        thirdPanel.add(scroll);
        createTable();

        table.addMouseListener(new TableMouseListener());


    }

    public static void createTable(){

        conn=DBConnection.getConnection();
        String str;
        str="SELECT * from Movies";
        try {
            state=conn.prepareStatement(str);
            result=state.executeQuery();

            String[] colsNames = {"ID","Име"};
            table.setModel(new TModel(result, colsNames));


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }






    class addAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Movies(movie_name) VALUES(?)";
            try {
                state =conn.prepareStatement(sql);
                String name = String.valueOf(nameTF.getText());

                state.setString(1, name);

                state.execute();

                System.out.println("dobaveno uspeshno");
                createTable();


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    static class deleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "delete from MOVIES where movie_id=" +idMovie ;
            try {
                state =conn.prepareStatement(sql);
                state.execute();

                System.out.println("iztrito uspeshno");
                createTable();



            } catch (SQLException ex) {
                JFrame frame = new JFrame();
                frame.setSize(500,200);
                frame.setLocationRelativeTo(null);
                JButton okbutton = new JButton("ok");
                frame.setLayout(new GridLayout(2,1));
                okbutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                JLabel label = new JLabel("Не може да изтрието този филм защото той вече е част от някоя прожекция");
                frame.add(label);
                frame.add(okbutton);

                frame.setVisible(true);

                throw new RuntimeException(ex);
            }

        }
    }

    static class TableMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int row=table.getSelectedRow();

            idMovie=Integer.parseInt(table.getValueAt(row, 0).toString());
            System.out.println("id-to na clicknatiq film: " + idMovie);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}