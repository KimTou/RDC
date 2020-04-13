package com.cjt.library.view;

import com.cjt.library.bean.Msg;
import com.cjt.library.controller.SeatController;
import com.cjt.library.dao.SeatDao;
import com.cjt.library.entity.Seat;
import com.cjt.library.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.time.LocalDate;

public class UserView {
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button useseat;
    @FXML
    private AnchorPane pane;
    @FXML
    private TextField orderText;
    @FXML
    private TextField cancelText;
    @FXML
    private TableView<Seat> table1;
    @FXML
    private TableView<Seat> table2;
    @FXML
    private DatePicker datepicker;
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
        static Seat usingSeat=null;         //以便UsingView能得到要使用的座位的对象
        User user=Controller.user;
        ObservableList<Seat> cellData;

    /**
     * 查看座位情况
     * @param actionEvent
     */
    public void seatCondition(javafx.event.ActionEvent actionEvent) {
            cellData = seatDao.learnCondition(2,user);
            column1.setCellValueFactory(new PropertyValueFactory("number"));
            column2.setCellValueFactory(new PropertyValueFactory("con_dition"));
            column3.setCellValueFactory(new PropertyValueFactory("location"));
            column4.setCellValueFactory(new PropertyValueFactory("score"));
            table1.setItems(cellData);                                      //在tableview中列出这些数据
            table1.setRowFactory(tv -> {
                TableRow<Seat> row = new TableRow<Seat>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {         //添加双击事件，双击可以查看该座位的评论
                        seat = new Seat();
                        seat = row.getItem();                 //得到被双击所在行的座位对象
                        ObservableList<Seat> cellData2= FXCollections.observableArrayList();
                        cellData2.add(seat);
                        column7.setCellValueFactory(new PropertyValueFactory("comment"));
                        table2.setItems(cellData2);
                    }
                });
                return row;
            });
        }

    /**
     * 预约座位
     * @param actionEvent
     */
    public void order(javafx.event.ActionEvent actionEvent){
            try {
                if(orderText.getText().isEmpty()||datepicker.getValue()==null){
                    Result("未输入信息！");
                }
                else {
                    int number = Integer.parseInt(orderText.getText());
                    LocalDate date = datepicker.getValue();        //获得预约日期
                    msg = seatController.orderSeat(number, date, user);
                    Result();
                }
            }catch(NumberFormatException e){
                Result();
            }
        }

    /**
     * 取消预约
     * @param actionEvent
     */
    public void cancel(javafx.event.ActionEvent actionEvent){
        try {
            if(cancelText.getText().isEmpty()){
                Result("未输入信息！");
            }
            else {
                int number = Integer.parseInt(cancelText.getText());      //获得取消预约位置的编号
                msg = seatController.cancelSeat(number, user);
                Result();
            }
        }catch(NumberFormatException e){
            Result();
        }
    }

    /**
     * 使用座位
     * @param actionEvent
     */
    public void useSeat(javafx.event.ActionEvent actionEvent) throws Exception {
        seat=seatController.useSeat(user);             //获取当前用户使用的座位对象
        if(seat==null){
            Result("您预约的座位不在使用时间内或您没有预约座位！");
        }
        else {
            usingSeat=seat;               //将要用的座位传给UsingView里的usingSeat
            Stage primaryStage = (Stage) useseat.getScene().getWindow();
            start2(primaryStage);
        }
    }

    /**
     * 结果信息
     */
    public void Result(){            //封装返回新增座位的结果
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("操作窗口");
        alert.setContentText(msg.getResult());
        alert.setHeaderText("操作信息");
        alert.showAndWait();
    }

    /**
     * 结果信息
     */                                        //方法重载
    public void Result(String msg){            //封装返回新增座位的结果
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("操作窗口");
        alert.setContentText(msg);
        alert.setHeaderText("操作信息");
        alert.showAndWait();
    }

    /**
     * 返回登录界面
     * @param actionEvent
     */
    public void exit(javafx.event.ActionEvent actionEvent) throws Exception {
        Stage primaryStage = (Stage) button2.getScene().getWindow();
        start(primaryStage);
        }

    @FXML
    public void start(Stage primaryStage) throws Exception {      //登录界面
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("登录页面");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
    }

    @FXML
    public void start2(Stage primaryStage) throws Exception{     //使用座位的界面
        Parent root = FXMLLoader.load(getClass().getResource("UsingView.fxml"));
        primaryStage.setTitle("图书馆");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
    }

}

