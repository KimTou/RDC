package com.cjt.library.service;
import com.cjt.library.bean.Msg;
import com.cjt.library.dao.SeatDao;
import com.cjt.library.entity.Seat;
import com.cjt.library.entity.User;

import java.time.LocalDate;

public class SeatService {
    private SeatDao seatDao=new SeatDao();

    /**
     * 管理员新增座位
     * @param location
     * @return
     */
    public Msg addSeat(String location){
        Seat seat=new Seat();
        seat.setLocation(location);
        return seatDao.addSeat(seat);
    }

    /**
     * 管理员删除座位
     * @param number
     * @return
     */
    public Msg deleteSeat(int number){
        return seatDao.deleteSeat(number);
    }

    /**
     * 用户预约座位
     * @param number
     * @param date
     * @param user
     * @return
     */
    public Msg orderSeat(int number, LocalDate date, User user){
        return seatDao.orderSeat(number,date,user);
    }

    /**
     * 用户取消预约
     * @param number
     * @param user
     * @return
     */
    public Msg cancelSeat(int number,User user){
        return seatDao.cancelSeat(number,user);
    }

    /**
     * 用户使用座位
     */
    public Seat useSeat(User user){
        return seatDao.useSeat(user);
    }

    /**
     * 更新评论
     */
    public void newComment(Seat seat){
        seatDao.newComment(seat);
    }

    /**
     * 更新评分
     */
    public void newScore(Seat seat){
        seatDao.newScore(seat);
    }

    /**
     * 结束使用
     */
    public void goBack(Seat seat){
        seatDao.goBack(seat);
    }
}
