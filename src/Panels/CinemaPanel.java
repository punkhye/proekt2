package Panels;

import DBconnection.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class CinemaPanel extends JPanel {

    static int idCinema = -1;

    static Connection conn = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet result = null;


    JPanel upPanel = new JPanel(new FlowLayout());

    JPanel middlePanel = new JPanel();

    JPanel downPanel = new JPanel();

    JLabel cinemaLabel = new JLabel("Име на кино: ");

    static JTextField cinemaTF = new JTextField();

    static JTable table = new JTable();

    JScrollPane scroll = new JScrollPane(table);

    JButton addButton = new JButton("Добави");

    JButton deleteButton = new JButton("Изтрий маркираното кино") ;

    public CinemaPanel(){

        this.setLayout(new GridLayout(3,1));

        //up panel
        this.add(upPanel);
        upPanel.add(cinemaLabel);
        cinemaTF.setPreferredSize( new Dimension( 200, 24 ) );
        upPanel.add(cinemaTF);

        //middle panel
        this.add(middlePanel);
        middlePanel.add(addButton);
        addButton.addActionListener(new AddCinemaAction());
        middlePanel.add(deleteButton);
        deleteButton.addActionListener(new DeleteCinemaAction());

        //down panel
        this.add(downPanel);
        downPanel.add(scroll);


        table.addMouseListener(new TableMouseListener());
        buildTable();


    }


    static class DeleteCinemaAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "delete from Cinemas where cinema_id="+idCinema;
            try {
                preparedStatement =conn.prepareStatement(sql);
                preparedStatement.execute();

                System.out.println("iztrito uspeshno");
                buildTable();

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
                JLabel label = new JLabel("Не може да изтрието това кино защото то вече е част от някоя прожекция");
                frame.add(label);
                frame.add(okbutton);

                frame.setVisible(true);

                throw new RuntimeException(ex);
            }

        }
    }


    static class AddCinemaAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "INSERT INTO Cinemas(cinema_name) VALUES(?)";
            try {
                preparedStatement =conn.prepareStatement(sql);
                String name = String.valueOf(cinemaTF.getText());

                preparedStatement.setString(1, name);

                preparedStatement.execute();

                System.out.println("dobaveno uspeshno");
                buildTable();


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    public static void buildTable() {
        conn=DBConnection.getConnection();
        String str;
        str="SELECT * from Cinemas";
        try {
            preparedStatement=conn.prepareStatement(str);
            result=preparedStatement.executeQuery();

            String[] colsNames = {"ID","Име"};
            table.setModel(new TModel(result, colsNames));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class TableMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int row=table.getSelectedRow();
            idCinema=Integer.parseInt(table.getValueAt(row, 0).toString());
            System.out.println("id-to na clicknatoto kino: " + idCinema);
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
