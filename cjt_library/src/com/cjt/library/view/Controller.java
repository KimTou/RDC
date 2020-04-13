package com.cjt.library.view;

import com.cjt.library.bean.Msg;
import com.cjt.library.controller.UserController;
import com.cjt.library.entity.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private TextField myTestField;
    @FXML
    private PasswordField myPasswordField;
    @FXML
    private Button myButton;
    @FXML
    private Button myButton2;
    @FXML
    private TextField condition;

    UserController userController=new UserController();
    Msg msg=null;
    public static User user;          //登陆后这个用户就是固定的

    /**
     * 用户登录
     * @param event
     * @throws Exception
     */
    @FXML
    public void login(ActionEvent event) throws Exception {
        if(myTestField.getText().isEmpty()||myPasswordField.getText().isEmpty()){
            condition.setText("请输入完整信息进行登录");
        }
        else {
            msg = userController.login(myTestField.getText(), myPasswordField.getText());
            condition.setText(String.valueOf(msg));
            user= (User) msg.getMessage();            //得到用户
            if(msg.getResult()=="登录成功"&&user.getUserId()==1) {
                Stage primaryStage = (Stage) myButton.getScene().getWindow();//将login(登录按钮)与Main类中的primaryStage(新窗口)绑定
                Result();
                start1(primaryStage);
            }                                       //区分管理员和普通用户的界面
            if(msg.getResult()=="登录成功"&&user.getUserId()!=1) {
                Stage primaryStage = (Stage) myButton.getScene().getWindow();//将login(登录按钮)与Main类中的primaryStage(新窗口)绑定
                Result();
                start2(primaryStage);
            }
            else
                condition.setText("登录失败！用户名或密码有误！");
        }
    }

    /**
     * 用户注册
     * @param event
     */
    @FXML
    public void register(ActionEvent event) {
        if(myTestField.getText().isEmpty()||myPasswordField.getText().isEmpty()){
            condition.setText("请输入完整信息进行注册");
        }
        else{
            msg=userController.register(myTestField.getText(), myPasswordField.getText());
            condition.setText(String.valueOf(msg));
        }
    }

    /**
     * 返回结果
     */
    public void Result(){            //返回新增座位的结果
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("登录窗口");
        alert.setContentText(msg.getResult());
        alert.setHeaderText("登录信息");
        alert.showAndWait();
    }

    @FXML
    public void start1(Stage primaryStage) throws Exception{         //管理员界面
        Parent root = FXMLLoader.load(getClass().getResource("ManagerView.fxml"));
        primaryStage.setTitle("图书馆座位预定系统");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @FXML
    public void start2(Stage primaryStage) throws Exception{           //用户界面
        Parent root = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        primaryStage.setTitle("图书馆座位预定系统");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
