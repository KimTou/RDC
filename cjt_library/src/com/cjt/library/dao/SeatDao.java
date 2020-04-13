package com.cjt.library.dao;

import com.cjt.library.bean.Msg;
import com.cjt.library.entity.Seat;
import com.cjt.library.entity.User;
import com.cjt.library.util.DbUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class SeatDao {
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    /**
     * 添加座位
     * @param seat
     * @return
     */
    public Msg addSeat(Seat seat){
        try {
            con = DbUtil.getCon();
            String sql = "insert into seat (con_dition,location,score,score_time,total_score) values (?,?,?,?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "空闲");       //新增的座位都为空闲，号码依次递增，每个座位都有唯一的编号
            stmt.setString(2, seat.getLocation());
            stmt.setDouble(3, 3.00);             //初始座位评分为3，给个平均分
            stmt.setInt(4,1);              //初始座位评分次数为0
            stmt.setInt(5,3);              //初始座位总评分为0
            stmt.executeUpdate();
            return new Msg("添加成功",seat);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new Msg("添加失败",null);
    }

    /**
     * 查看座位情况
     * @param type
     * @param user
     * @return
     */
    public ObservableList learnCondition(int type,User user){
        try {
            Seat seat=null;
            String sql;
            ObservableList<Seat> list = FXCollections.observableArrayList();     //该集合必须是符合javafx形式的集合
            con = DbUtil.getCon();
            if(type==1) {
                sql = "SELECT * FROM seat ";
                stmt = con.prepareStatement(sql);
            }                          //管理员返回所有座位的情况，用户则返回空闲座位和已被自己预约的座位
            if(type==2) {
                sql = "SELECT * FROM seat where con_dition='空闲' or order_man=?";
                stmt = con.prepareStatement(sql);                     //用户预约的时候用的
                stmt.setString(1,user.getUserName());
            }
            if(type==3){
                sql="select * from seat where order_man=?";
                stmt = con.prepareStatement(sql);                    //用户使用座位时用的
                stmt.setString(1,user.getUserName());
            }
            rs = stmt.executeQuery();
            while(rs.next()){
                seat = new Seat();                  //必须new对象，否则传回空指针
                seat.setNumber(rs.getInt(1));
                seat.setCon_dition(rs.getString(2));     //将座位的属性赋给对象
                seat.setLocation(rs.getString(3));
                seat.setScore(rs.getDouble(4));
                seat.setComment(rs.getString(5));
                if(rs.getDate(6)==null){
                    seat.setDate(null);
                }
                else {
                    Date date = rs.getDate(6);
                    String strData = date.toString();            //通过格式化来获取mysql中的date类型数据
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    seat.setDate(LocalDate.parse(strData, fmt));
                    //seat.setDate((LocalDate) rs.getObject(6));  (java.sql.Date cannot be cast to java.time.LocalDate)
                }
                seat.setOrderman(rs.getString(7));
                seat.setScoreTime(rs.getInt(8));
                seat.setTotalScore(rs.getInt(9));
                list.add(seat);                 //把对象放进集合中去
            }
            return list;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除座位
     * @param number
     * @return
     */
    public Msg deleteSeat(int number){
        try {
            int yesNo=0;
            con = DbUtil.getCon();
            String sql = "SELECT * FROM seat ";     //检测所有座位，如果有该编号的座位，则删除
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                if (rs.getInt(1) == number) {
                    yesNo = 1;
                    break;
                }
            }
                if(yesNo==1) {
                    sql = "delete from seat where number=?";
                    stmt = con.prepareStatement(sql);
                    stmt.setInt(1,number);
                    stmt.executeUpdate();
                    return new Msg("删除成功", null);
                }
                else
                    return new Msg("没有寻找到该编号的座位，请输入有效编号", null);
            } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new Msg("删除失败",null);
    }

    /**
     * 预约座位
     * @param number
     * @param date
     * @param user
     * @return
     */
    public Msg orderSeat(int number, LocalDate date, User user){
        try {
            int yesNo=0;           //判断是否有预约到座位
            Seat seat=null;
            con = DbUtil.getCon();
            List<Seat> list =learnCondition(2,user) ;       //接收空闲以及已经被自己预约的座位
            Iterator<Seat> it=list.iterator();                    //遍历这些符合条件的座位
            while(it.hasNext()) {
                seat=it.next();
                if(seat.getNumber()==number) {         //如果找到用户选择的编号的座位，则预约该座位
                    if(con.isClosed()){
                        con=DbUtil.getCon();      //否则会报出con没连接的错误
                    }
                    String sql = "update seat set con_dition=? where number=?";
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, "已预约");       //改状态
                    stmt.setInt(2, number);
                    stmt.executeUpdate();
                    sql = "update seat set locadate=? where number=?";
                    stmt = con.prepareStatement(sql);
                    stmt.setObject(1, date);             //添加预约时间，到时间预约人则可使用
                    stmt.setObject(2, number);
                    stmt.executeUpdate();
                    sql = "update seat set order_man=? where number=?";
                    stmt = con.prepareStatement(sql);                    //添加预约人
                    stmt.setObject(1, user.getUserName());
                    stmt.setObject(2, number);
                    stmt.executeUpdate();
                    yesNo=1;
                    break;
                }
            }
            if(yesNo==1){
                return new Msg("预约成功", null);
            }
            else
                return new Msg("座位已被预约或编号有误", null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new Msg("预约失败",null);
    }

    /**
     * 取消预约
     */
    public Msg cancelSeat(int number,User user){
        try {
            int yesNo=0;           //判断是否有预约到座位
            Seat seat=null;
            con = DbUtil.getCon();
            List<Seat> list =learnCondition(3,user) ;       //接收已经被自己预约的座位
            Iterator<Seat> it=list.iterator();                    //遍历这些符合条件的座位
            while(it.hasNext()) {
                seat=it.next();
                if(seat.getNumber()==number) {         //如果找到用户选择的编号的座位，则取消预约该座位
                    if(con.isClosed()){
                        con=DbUtil.getCon();      //否则会报出con没连接的错误
                    }
                    String sql = "update seat set con_dition=? where number=?";
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, "空闲");       //改状态
                    stmt.setInt(2, number);
                    stmt.executeUpdate();
                    sql = "update seat set locadate=? where number=?";
                    stmt = con.prepareStatement(sql);
                    stmt.setObject(1, null);             //清空预约时间
                    stmt.setObject(2, number);
                    stmt.executeUpdate();
                    sql = "update seat set order_man=? where number=?";
                    stmt = con.prepareStatement(sql);                    //清空预约人
                    stmt.setObject(1, null);
                    stmt.setObject(2, number);
                    stmt.executeUpdate();
                    yesNo=1;
                    break;
                }
            }
            if(yesNo==1){
                return new Msg("取消成功", null);
            }
            else
                return new Msg("编号有误或你没有预约该座位", null);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new Msg("取消预约失败",null);
    }

    /**
     * 用户使用座位
     * @param user
     * @return
     */
    public Seat useSeat(User user){
        try {
            int yesNo=0;           //判断是否有使用到座位
            int number=0;          //得到使用座位的编号
            Seat seat=null;
            con = DbUtil.getCon();
            List<Seat> list =learnCondition(3,user) ;       //接收已经被自己预约的座位
            Iterator<Seat> it=list.iterator();                    //遍历这些符合条件的座位
            while(it.hasNext()) {
                seat = it.next();
                if (seat.getDate().isEqual(LocalDate.now())) {         //如果座位的预约时间就是今天，则可以使用
                    if (con.isClosed()) {                 //日期相等用isEqual
                        con = DbUtil.getCon();      //否则会报出con没连接的错误
                    }
                    number = seat.getNumber();               //得到该座位的编号
                    String sql = "update seat set con_dition=? where number=?";
                    stmt = con.prepareStatement(sql);
                    stmt.setString(1, "使用中");       //改状态
                    stmt.setInt(2, number);
                    stmt.executeUpdate();
                    yesNo = 1;
                    break;
                }
            }
            if(yesNo==1){
                return seat;      //返回可使用的座位
            }
            else
                return null;     //没有可使用的座位则返回null
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public void newComment(Seat seat){
        try {
            con = DbUtil.getCon();
            String sql = "update seat set comment=? where number=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, seat.getComment());       //改状态
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 结束使用
     * @param seat
     */
    public void goBack(Seat seat){
        try {
            con = DbUtil.getCon();
            String sql = "update seat set con_dition=? where number=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "空闲");       //改状态
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
            sql = "update seat set order_man=? where number=?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, null);       //清空预约人
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
            sql = "update seat set locadate=? where number=?";
            stmt = con.prepareStatement(sql);
            stmt.setObject(1, null);       //清空预约日期
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新评分
     */
    public void newScore(Seat seat){
        try {
            con = DbUtil.getCon();
            String sql = "update seat set score=? where number=?";
            stmt = con.prepareStatement(sql);
            stmt.setDouble(1, seat.getScore());       //改状态
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
            sql = "update seat set score_time=? where number=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, seat.getScoreTime());       //改状态
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
            sql = "update seat set total_score=? where number=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, seat.getTotalScore());       //改状态
            stmt.setInt(2, seat.getNumber());
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
