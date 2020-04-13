package com.cjt.library.view;
import com.cjt.library.bean.Msg;
import com.cjt.library.dao.SeatDao;
import com.cjt.library.entity.Seat;
import com.cjt.library.controller.SeatController;

import com.cjt.library.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ManagerView {
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private ComboBox<String> choice1;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField deleteText;
    @FXML
    private TableView<Seat> table1;
    @FXML
    private TableView<Seat> table2;
    @FXML
    private TableColumn<Seat, Integer> column1;
    @FXML
    private TableColumn<Seat, String> column2;
    @FXML
    private TableColumn<Seat, String> column3;
    @FXML
    private TableColumn<Seat, String> column4;
    @FXML
    private TableColumn<Seat, String> column6;
    @FXML
    private TableColumn<Seat, String> column7;


    Msg msg = null;                        //设置全局变量
    SeatController seatController = new SeatController();
    SeatDao seatDao = new SeatDao();
    Seat seat = null;
    User user=Controller.user;
    ObservableList<Seat> cellData;

    public void seatCondition(javafx.event.ActionEvent actionEvent) {
        cellData = seatDao.learnCondition(1,user);
        column1.setCellValueFactory(new PropertyValueFactory("number"));
        column2.setCellValueFactory(new PropertyValueFactory("con_dition"));
        column3.setCellValueFactory(new PropertyValueFactory("location"));
        column4.setCellValueFactory(new PropertyValueFactory("score"));
        table1.setItems(cellData);
        table1.setRowFactory(tv -> {
            TableRow<Seat> row = new TableRow<Seat>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    seat = new Seat();
                    seat = row.getItem();
                    ObservableList<Seat> cellData2= FXCollections.observableArrayList();
                    cellData2.add(seat);
                    column7.setCellValueFactory(new PropertyValueFactory("comment"));
                    table2.setItems(cellData2);
                }
            });
            return row;
        });
    }

   public void delete(javafx.event.ActionEvent actionEvent){
        try {
            int number = Integer.parseInt(deleteText.getText());
            msg = seatController.deleteSeat(number);
            Result();
        }catch(NumberFormatException e){
            Result();
        }
}

    public void add1(javafx.event.ActionEvent actionEvent) {
        msg=seatController.addSeat("靠窗");
        Result();
    }
    public void add2(javafx.event.ActionEvent actionEvent) {
        msg=seatController.addSeat("靠走道");
        Result();
    }
    public void add3(javafx.event.ActionEvent actionEvent) {
        msg=seatController.addSeat("电脑");
        Result();
    }
    public void add4(javafx.event.ActionEvent actionEvent) {
        msg=seatController.addSeat("沙发");
        Result();
    }

    public void Result(){            //封装返回新增座位的结果
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("操作窗口");
        alert.setContentText(msg.getResult());
        alert.setHeaderText("操作信息");
        alert.showAndWait();
    }

    public void exit(javafx.event.ActionEvent actionEvent){
        System.exit(0);                     //结束程序
    }


}
