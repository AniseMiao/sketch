package com.edu.nju.sketch.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdUtil {

    // 获得0000-9999形式的数字编号
    public static String getFormatId(int num) {
        String id = "";
        if (num >= 1000) {
            id = id + num;
            id = id.substring(id.length() - 4);
        } else if (num >= 100) {
            id = "0" + num;
        } else if (num >= 10) {
            id = "00" + num;
        } else {
            id = "000" + num;
        }
        return id;
    }

    // 获得0001数组编号（编号的起始值
    public static String getFormatFirst() {
        return "0001";
    }

    public static Date getDate() {
        return new Date();
    }

    //获得yyyymmdd形式的日期
    public static String getFormatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    //某关键字的最新编号是属于今天的吗
    public static boolean isToday(String keyword) {
        return getFormatDate(getDate()).equals(getFormatDate(getLastDate(keyword)));
    }

    //获取最新构造的aid，下类似
    public static String getAccountId() {
        if (isToday("account")) {
            updateNum("account");
            return getFormatDate(getDate()) + getFormatId(getNum("account"));
        } else {
            resetNum("account");
            return getFormatDate(getDate()) + getFormatFirst();
        }
    }

    public static String getPictureId() {
        if (isToday("picture")) {
            updateNum("picture");
            return getFormatDate(getDate()) + getFormatId(getNum("picture"));
        } else {
            resetNum("picture");
            return getFormatDate(getDate()) + getFormatFirst();
        }
    }

    public static String getLineId() {
        if (isToday("line")) {
            updateNum("line");
            return getFormatDate(getDate()) + getFormatId(getNum("line"));
        } else {
            resetNum("line");
            return getFormatDate(getDate()) + getFormatFirst();
        }
    }

    public static String getDotId() {
        if (isToday("dot")) {
            updateNum("dot");
            return getFormatDate(getDate()) + getFormatId(getNum("dot"));
        } else {
            resetNum("dot");
            return getFormatDate(getDate()) + getFormatFirst();
        }
    }

    //获取某关键字今日下一个构造值
    public static int getNum(String keyword) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        int ret = (int)session.createQuery("select lastid from Constructor where keyword = ?1 ")
                .setParameter(1, keyword).uniqueResult();
        transaction.commit();
        session.close();
        return ret;
    }

    //获取某关键字最近的日期
    public static Date getLastDate(String keyword) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Date ret = (Date)session.createQuery("select lasttime from Constructor where keyword = ?1 ")
                .setParameter(1, keyword).uniqueResult();
        transaction.commit();
        session.close();
        return ret;
    }

    //更新某关键字下一个构造值
    public static void updateNum(String keyword) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("update Constructor set lastid = lastid + 1 where keyword = ?1 ")
                .setParameter(1, keyword).executeUpdate();
        transaction.commit();
        session.close();
    }

    //更新某关键字下一个构造值为初始值1
    public static void resetNum(String keyword) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("update Constructor set lastid = 1 where keyword = ?1 ")
                .setParameter(1, keyword).executeUpdate();
        transaction.commit();
        session.close();
    }
}
