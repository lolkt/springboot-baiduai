package cn.lilq.baiduai.pojo;

public class User {
    private String groupId;
    private String id;
    private String name;


    public User() {
    }

    public User(String groupId, String id, String name) {
        this.groupId = groupId;
        this.id = id;
        this.name = name;
    }

    public User(String groupId, String id) {
        this.groupId = groupId;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "groupId='" + groupId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
