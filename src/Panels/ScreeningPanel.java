package Panels;

import DBconnection.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Objects;

public class ScreeningPanel extends JPanel {

    static int idScreening = -1;
    static int idMovie = -1;
    static int idCinema = -1;

    static Connection conn = null;
    static Statement state = null;

    static PreparedStatement preparedStatement = null;
    static ResultSet result = null;
    JPanel upPanel = new JPanel();
    JPanel centerPanel = new JPanel();

    JPanel downPanel = new JPanel();

    JComboBox<Object> cinemaCombo = new JComboBox<>();
    JComboBox<Object> movieCombo = new JComboBox<Object>();
    static JSpinner dayPicker = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
    static String[] months = { "Януари", "Февруари", "Март",
            "Април", "Май", "Юни", "Юли", "Август",
            "Септември", "Октомври", "Ноември", "Декември" };
    static JComboBox<String> monthsCombo = new JComboBox<>(months);

    static String[] years = { "2024", "2025", "2026",
            "2027", "2028", "2029", "2030", };
    static JComboBox<String> yearPicker = new JComboBox<>(years);

    static String[] time = { "10:30 ч", "12:40 ч", "14:20 ч",
            "15:00 ч", "16:30 ч", "17:50 ч",
            "21:20 ч", "22:20 ч" };

    static JComboBox<String> timePicker = new JComboBox<>(time);

    JLabel cinemaLabel = new JLabel("Кино: ")   ;
    JLabel movieLabel = new JLabel("Филм: ")    ;
    JLabel dayLabel = new JLabel("Ден: ");
    JLabel monthLabel = new JLabel("Месец: ");
    JLabel yearLabel = new JLabel("Година: ");
    JLabel timeLabel = new JLabel("Час на прожекцията: ");

    JLabel screeningsLabel = new JLabel("Прожекции: ");

    static JTable table=new JTable();
    static JScrollPane scroll=new JScrollPane(table);
    static String[] colsNames = { "Номер на прожекция", "Кино", "Филм",
            "Ден", "Месец", "Година",
            "Час"};

    JButton addButton = new JButton("Добавяне");
    JButton deleteButton = new JButton("Изтриване на маркираната прожекция");


    public ScreeningPanel() {

        this.setLayout(new GridLayout(4, 1));

        //up panel
        upPanel.setLayout(new GridLayout(6, 2));
        this.add(upPanel);
        upPanel.add(cinemaLabel);
        upPanel.add(cinemaCombo);
        upPanel.add(movieLabel);
        upPanel.add(movieCombo);
        upPanel.add(dayLabel);
        upPanel.add(dayPicker);
        upPanel.add(monthLabel);
        upPanel.add(monthsCombo);
        upPanel.add(yearLabel);
        upPanel.add(yearPicker);
        upPanel.add(timeLabel);
        upPanel.add(timePicker);


        // center panel
        centerPanel.setLayout(new FlowLayout());
        this.add(centerPanel);
        centerPanel.add(addButton);
        addButton.addActionListener(new AddScreeningAction());
        centerPanel.add(deleteButton);
        deleteButton.addActionListener(new DeleteScreeningAction());


        //down panel
        this.add(downPanel);
        downPanel.add(screeningsLabel);
        scroll.setPreferredSize(new Dimension(450, 200));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        downPanel.add(scroll);


        //добавяне от таблиците в comboBox-oвете
        this.showTheComboBoxMovies();
        this.showTheComboBoxCinemas();


        //suzdavane na tablicata
        buildTable();

        //mouse listener za vzimane na pravilnoto id pri clickvane v tablicata
        table.addMouseListener(new TableMouseAction());


        //action listener (beshe zamesten s lambda expression) s koito se vzima id-to na markiraniq film
        movieCombo.addActionListener(e -> {
            if (idMovie > 0) {
                String str = "select * from Movies where movie_name='" + Objects.requireNonNull(movieCombo.getSelectedItem()).toString() + "'";
                try {
                    preparedStatement = conn.prepareStatement(str);
                    result = preparedStatement.executeQuery();
                    result.next();
                    idMovie = Integer.parseInt(result.getObject(1).toString());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                System.out.println("izbran film: " + movieCombo.getSelectedItem().toString());
                System.out.println("commandant : " + str);
                System.out.println("id na filama: " + idMovie);
            }
        });

        //action listener (beshe zamesten s lambda expression) s koito se vzima id-to na markiranoto kino
        cinemaCombo.addActionListener(e -> {
            if (idCinema > 0) {
                String str = "select * from Cinemas where cinema_name='" + Objects.requireNonNull(cinemaCombo.getSelectedItem()).toString() + "'";
                try {
                    preparedStatement = conn.prepareStatement(str);
                    result = preparedStatement.executeQuery();
                    result.next();
                    idCinema = Integer.parseInt(result.getObject(1).toString());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                System.out.println("izbrano kino: " + cinemaCombo.getSelectedItem().toString());
                System.out.println("komanda : " + str);
                System.out.println("id na kinoto: " + idCinema);

            }
        });
    }





    public void showTheComboBoxMovies() {
        try {

            //izchistva za da nqma povtoreniq
            idMovie = -1;
            movieCombo.removeAllItems();



            //връзка с database
            conn = DBConnection.getConnection();
            state = conn.createStatement();
            String sql = "SELECT movie_id, movie_name FROM MOVIES";
            result = state.executeQuery(sql);

            String item ;

            //cikul za dobavqne na neshtata v combobox-a
            if (result.next()) {
                idMovie = Integer.parseInt(result.getObject(1).toString());
                do {
                    item = result.getObject(2).toString();
                    movieCombo.addItem(item);
                } while (result.next());

            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
        public void showTheComboBoxCinemas(){
            try {
                //izchistva za da nqma povtoreniq
                cinemaCombo.removeAllItems();
                idCinema = -1;

                //връзка с database
                conn = DBConnection.getConnection();
                state = conn.createStatement();
                String sql = "SELECT cinema_id,cinema_name FROM CINEMAS";
                result = state.executeQuery(sql);

                String item ;

                //cikul za dobavqne na neshtata v combobox-a
                if (result.next()) {
                    idCinema = Integer.parseInt(result.getObject(1).toString());
                    do {
                        item = result.getObject(2).toString();
                        cinemaCombo.addItem(item);
                    } while (result.next());

                }

            } catch (SQLException e) {
                e.printStackTrace();

            }




    }



    static class AddScreeningAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "INSERT INTO SCREENINGS (cinema_id, movie, screening_day, screening_month, screening_year, screening_time) VALUES(?, ?, ?, ?, ?, ?)";
            try {
                preparedStatement =conn.prepareStatement(sql);

                preparedStatement.setInt(1, idCinema);
                preparedStatement.setInt(2, idMovie);
                int day = (int) dayPicker.getValue();
                preparedStatement.setInt(3, day);
                String month = (String) monthsCombo.getSelectedItem();
                preparedStatement.setString(4, month);
                int year = Integer.parseInt((String) Objects.requireNonNull(yearPicker.getSelectedItem()));
                preparedStatement.setInt(5, year);
                String time = (String) timePicker.getSelectedItem();
                preparedStatement.setString(6, time);

                preparedStatement.execute();

                System.out.println("dobaveno uspeshno");
                buildTable();


            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    static class DeleteScreeningAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            conn = DBConnection.getConnection();
            String sql = "delete from screenings where screening_id=" + idScreening;

            try {
                preparedStatement =conn.prepareStatement(sql);
                preparedStatement.execute();
                System.out.println("iztrito uspeshno");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


            buildTable();

        }
    }



    public static void buildTable() {
        String str;
        str="SELECT Screenings.screening_id, Cinemas.cinema_name, Movies.movie_name, Screenings.screening_day, Screenings.screening_month, Screenings.screening_year, Screenings.screening_time from SCREENINGS,CINEMAS, Movies where Screenings.cinema_id=Cinemas.cinema_id and Screenings.movie=movie_id";
        try {
            preparedStatement=conn.prepareStatement(str);
            result=preparedStatement.executeQuery();

            table.setModel(new TModel(result, colsNames));
            //razmer na kolonite
            table.getColumnModel().getColumn(0).setPreferredWidth(140);
            table.getColumnModel().getColumn(1).setPreferredWidth(140);
            table.getColumnModel().getColumn(2).setPreferredWidth(140);
            table.getColumnModel().getColumn(3).setPreferredWidth(140);
            table.getColumnModel().getColumn(4).setPreferredWidth(120);
            table.getColumnModel().getColumn(5).setPreferredWidth(140);
            table.getColumnModel().getColumn(6).setPreferredWidth(140);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class TableMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {


        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int row=table.getSelectedRow();
            idScreening=Integer.parseInt(table.getValueAt(row, 0).toString());
            System.out.println("id-to na clicknatata projekciq: " + idScreening);

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

    }


}
