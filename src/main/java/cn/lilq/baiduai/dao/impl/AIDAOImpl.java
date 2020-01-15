package cn.lilq.baiduai.dao.impl;

import cn.lilq.baiduai.dao.AIDAO;
import cn.lilq.baiduai.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository("bookDAO")
public class AIDAOImpl implements AIDAO {
    private Map<String,User> idToUser;
    private Map<String,User> nameToUser;
    private List<User> users;

    public AIDAOImpl() {
        idToUser = new ConcurrentHashMap<>();
        nameToUser = new ConcurrentHashMap<>();
        users = new CopyOnWriteArrayList<>();
    }

    @Override
    public User insertUser(User user) {
        if (user==null) {
            return null;
        }
        //判断用于数量 生成分组规则
        String group = "test";
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        User userNew = new User(group,uuid,user.getName());
        users.add(userNew);
        idToUser.put(uuid,userNew);
        nameToUser.put(user.getName(),userNew);
        return userNew;
    }

    @Override
    public User getUser(User user) {
        if (user==null) {
            return null;
        }
        return idToUser.get(user.getId());
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean addUser(List<User> users) {
        try{
            users.forEach(user -> {
                this.users.add(user);
                this.idToUser.put(user.getId(),user);
                this.nameToUser.put(user.getName(),user);
            });
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeUser(User user) {
        idToUser.remove(user.getId());
        nameToUser.remove(user.getName());
        users.remove(user);
    }

    @Override
    public String getGroup() {
        return "test";
    }
}
