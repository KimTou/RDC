package com.cjt.library.entity;

import java.time.LocalDate;

public class Seat {
    private int number;
    private String con_dition;
    private String location;
    private double score;          //平均分是有小数的
    private String comment;
    private LocalDate date;
    private int scoreTime;
    private int totalScore;
    private String orderman;

    /**
     * 设置编号
     * @param number
     */
    public void setNumber(int number){
        this.number=number;
    }

    /**
     * 得到编号
     * @return
     */
    public int getNumber(){
        return number;
    }

    /**
     * 设置状态
     * @param con_dition
     */
    public void setCon_dition(String con_dition){
        this.con_dition=con_dition;
    }

    /**
     * 得到状态
     * @return
     */
    public String getCon_dition(){
        return con_dition;
    }

    /**
     * 设置位置
     * @param location
     */
    public void setLocation(String location){
        this.location=location;
    }

    /**
     * 得到位置
     * @return
     */
    public String getLocation(){
        return location;
    }

    /**
     * 设置平均分
     * @param score
     */
    public void setScore(double score){
        this.score=score;
    }

    /**
     * 得到平均分
     * @return
     */
    public double getScore(){
        return score;
    }

    /**
     * 设置评论
     * @param comment
     */
    public void setComment(String comment){
        this.comment=comment;
    }

    /**
     * 得到评论
     * @return
     */
    public String getComment(){
        return comment;
    }

    /**
     * 设置评分次数
     * @param scoreTime
     */
    public void setScoreTime(int scoreTime){
        this.scoreTime=scoreTime;
    }

    /**
     * 得到评分次数
     * @return
     */
    public int getScoreTime(){
        return scoreTime;
    }

    /**
     * 设置总得分
     * @param totalScore
     */
    public void setTotalScore(int totalScore){
        this.totalScore=totalScore;
    }

    /**
     * 得到总得分
     * @return
     */
    public int getTotalScore(){
        return totalScore;
    }

    /**
     * 设置预约人
     * @param orderman
     */
    public void setOrderman(String orderman){
        this.orderman=orderman;
    }

    /**
     * 得到预约人
     * @return
     */
    public String getOrderman(){
        return orderman;
    }

    /**
     * 设置预约时间
     * @param date
     */
    public void setDate(LocalDate date){
        this.date=date;
    }

    /**
     * 得到预约时间
     * @return
     */
    public LocalDate getDate(){
        return date;
    }

    @Override
    public String toString() {               //重写方法
        if (number != 0) {
            return " 座位id :" + number + ", 状态 :" + con_dition + ", 位置 :" + location;
        }
        else
            return null;
    }
}
