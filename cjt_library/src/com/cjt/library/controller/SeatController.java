package com.cjt.library.controller;

import com.cjt.library.bean.Msg;
import com.cjt.library.entity.Seat;
import com.cjt.library.entity.User;
import com.cjt.library.service.SeatService;

import java.time.LocalDate;

public class SeatController {
    private SeatService seatService=new SeatService();
    /**
     * 新增座位
     */
    public Msg addSeat(String location){
        return seatService.addSeat(location);
    }

    /**
     * 删除座位
     */
    public Msg deleteSeat(int number){
        return seatService.deleteSeat(number);
    }

    /**
     * 预约座位
     */
    public Msg orderSeat(int number, LocalDate date, User user){
        return seatService.orderSeat(number,date,user);
    }

    /**
     * 取消预约
     */
    public Msg cancelSeat(int number,User user){
        return seatService.cancelSeat(number,user);
    }

    /**
     * 用户使用座位
     */
    public Seat useSeat(User user){
        return seatService.useSeat(user);
    }

    /**
     * 更新评论
     */
    public void newComment(Seat seat){
        seatService.newComment(seat);
    }

    /**
     * 更新评分
     */
    public void newScore(Seat seat){
        seatService.newScore(seat);
    }

    /**
     * 结束使用
     */
    public void goBack(Seat seat){
        seatService.goBack(seat);
    }
}
