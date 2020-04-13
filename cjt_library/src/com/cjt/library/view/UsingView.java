package com.cjt.library.view;

import com.cjt.library.controller.SeatController;
import com.cjt.library.entity.Seat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsingView {
    @FXML
    private TextField scoreText;

    @FXML
    private Button goBackButton;

    @FXML
    private Button commentButton;

    @FXML
    private Button scoreButton;

    @FXML
    private TextField commentText;

    String comment;        //从使用界面得到评论
    double score;             //从使用界面得到评分
    SeatController seatController=new SeatController();
    Seat seat=UserView.usingSeat;   //得到当前使用的座位对象

    /**
     * 返回预约系统
     * @param event
     * @throws Exception
     */
    public void goBack(ActionEvent event) throws Exception {
        seatController.goBack(seat);
        Stage primaryStage = (Stage) goBackButton.getScene().getWindow();//将返回按钮与primaryStage(新窗口)绑定
        start(primaryStage);
    }

    /**
     * 提交评论
     * @param event
     */
    public void getComment(ActionEvent event) {
        if(commentText.getText().isEmpty()){
            Result("未输入信息！");
        }
        else {
            comment = commentText.getText();
            Result("提交成功！");
            String allComment=seat.getComment();
            comment=comment+'\n'+allComment;        //连接所有评论，并用回车键分隔
            seat.setComment(comment);               //更新座位的评论
            seatController.newComment(seat);
        }
    }

    /**
     * 提交评分
     * @param event
     */
    public void getScore(ActionEvent event) {
        if(scoreText.getText().isEmpty()){
            Result("未输入信息！");
        }
        if(Integer.parseInt(scoreText.getText())>5||Integer.parseInt(scoreText.getText())<1){
            Result("请输入1-5的整数评分！");
        }
        else {
            int totalScore=seat.getTotalScore()+Integer.parseInt(scoreText.getText());
            int scoreTime=seat.getScoreTime()+1;
            score=totalScore/scoreTime;           //得到平均得分
            seat.setTotalScore(totalScore);
            seat.setScoreTime(scoreTime);        //刷新座位信息
            seat.setScore(score);
            seatController.newScore(seat);
            Result("提交成功！");
        }
    }

    /**
     * 结束使用
     * @param primaryStage
     * @throws Exception
     */

    @FXML
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UserView.fxml"));
        primaryStage.setTitle("图书馆座位预定系统");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    /**
     * 结果信息
     */
    public void Result(String msg){            //封装返回新增座位的结果
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("操作窗口");
        alert.setContentText(msg);
        alert.setHeaderText("操作信息");
        alert.showAndWait();
    }
}
