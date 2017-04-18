package com.example.yannis.dianming.base;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Set;
import com.activeandroid.query.Update;
import com.example.yannis.dianming.bean.DBEntity;

import java.util.List;
import java.util.List;

/**
 * Created by yannis on 2017/2/21.
 */

public class DBManager {
    //添加数据
    public static void insertUser(DBEntity user){
        //操作数据库的对象就是实体类本身
        user.save();
    }

    //删除数据
    public static void deleteUser(DBEntity user){
        user.delete();
    }

    //条件更新
    public static void update(int id, boolean tick){
        Update update = new Update(DBEntity.class);
        Set set = update.set("solved = ?",tick)
                .where("student_id = ?",id);
        Util.logHelper(set.toSql());
                set.execute();
    }

    //更新数据
    public static void updateUser(DBEntity user, boolean flag){
//        user.setUserName("关羽");
//        user.setAddr("北京");
        //在activeandroid中save既可以创建，也可以修改
        user.setSolved(flag);
        user.save();
    }

    //查询所有的数据
    public static List<DBEntity> queryUser(){
        ActiveAndroid.beginTransaction();
        List<DBEntity> execute = new Select()
                .from(DBEntity.class) //model类
                .execute();
        return execute;
    }

    //条件查询
    public static List<DBEntity> query(int id){
        List<DBEntity> queryName = new Select()
                .from(DBEntity.class)
                .where("teacher_id = ?", id) //查询条件
                .execute();
        return queryName;
    }

    //条件删除
    public static void delete(String userName){
        new Delete()
                .from(DBEntity.class)
                .where("userName = ?", userName)
                .execute();
    }
}
