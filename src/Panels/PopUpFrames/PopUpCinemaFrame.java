package Panels.PopUpFrames;

import DBconnection.DBConnection;
import Panels.TModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopUpCinemaFrame extends JFrame {

    String idCinema;
    String selectedCinema ;
    static Connection conn = null;
    static ResultSet resultSet = null;
    static PreparedStatement statement = null;
    static JTable table = new JTable();
    JScrollPane scroll = new JScrollPane(table);

    JLabel label = new JLabel("Double click върху киното, което искате да промените");


    JButton closeButton = new JButton("Затвори");

    public PopUpCinemaFrame(){
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(3,1));

        this.add(label);
        this.add(scroll);
        this.add(closeButton);

        table.addMouseListener(new TableDoubleClick());
        closeButton.addActionListener(new CloseAction());

        buildTable();

    this.setVisible(true);

    }

    void buildTable(){
        conn= DBConnection.getConnection();
        String str;
        str="SELECT * from Cinemas";
        try {
            statement=conn.prepareStatement(str);
            resultSet=statement.executeQuery();

            String[] colsNames = {"ID","Име"};
            table.setModel(new TModel(resultSet, colsNames));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    class TableDoubleClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {

                idCinema = null;
                selectedCinema = null;

                int row = table.getSelectedRow();
                if (row != -1) {

                    Object idObject = table.getValueAt(row, 0);
                    Object nameObject = table.getValueAt(row, 1);

                    idCinema = String.valueOf(idObject);
                    selectedCinema = (String) nameObject;

                   System.out.println(idCinema);
                   System.out.println(selectedCinema);
                }

                UpdateCinemaNameFrame updateWindow = new UpdateCinemaNameFrame(idCinema, selectedCinema);
                updateWindow.setVisible(true);
                System.out.println("double clicked");
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

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



    class CloseAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }



    public class UpdateCinemaNameFrame extends JFrame {

        Connection conn = null;
        PreparedStatement statement = null;

        JTextField newnameTF =new JTextField("",10)    ;
        JLabel nameL = new JLabel("Въведете новото име: ");

        JButton editButton = new JButton("ОК");
        JButton closeButton = new JButton("Отмяна");
        private String id;


        public UpdateCinemaNameFrame(String idPr , String name){

            this.setTitle("Промяна на името " +"'" +name + "'");
            this.setSize(500,150);
            this.setLocationRelativeTo(null);
            this.setLayout(new FlowLayout());

            this.add(nameL);
            this.add(newnameTF);

            this.add(editButton);
            this.add(closeButton);

            setId(idPr);


            editButton.addActionListener(new EditButtonAction());
            closeButton.addActionListener(e -> dispose());

            this.setVisible(true);

        }

        class EditButtonAction implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {

                conn= DBConnection.getConnection();
                String sql="update Cinemas set cinema_name=? where cinema_id=?";

                try {
                    statement= conn.prepareStatement(sql);
                    String name = newnameTF.getText();
                    statement.setString(1, name);

                    statement.setInt(2, Integer.parseInt(id));

                    statement.execute();
                    dispose();

                    buildTable();
                    System.out.println("бе май има промяна");



                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        }


        public void setId(String id){

            this.id = id;
        }

        public String getId(){
            return id;
        }




    }


}
