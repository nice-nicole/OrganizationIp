package models;

import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static models.DB.sql2o;

public class User implements DBManagement{
    private int id;
    private String name;
    private String position;
    private String role;
    private int departmentId;


    public User(String name, String position, String role, int departmentId) {
        this.name = name;
        this.position = position;
        this.role = role;
        this.departmentId= departmentId;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    @Override
    public boolean equals(Object otherUser) {
        if(!(otherUser instanceof User)){return false;}
        else {
            User newUser = (User) otherUser;
            return this.getPosition().equals(newUser.getPosition())&&
                    this.getRole().equals(newUser.getRole())&&
                    this.getName().equals(newUser.getName());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,position,role);
    }
    @Override
    public void save() {
        try(Connection con = sql2o.open()) {
            String sql = "INSERT INTO users (name, position, role, departmentId) VALUES (:name, :position, :role, :departmentId)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("position", this.position)
                    .addParameter("role", this.role)
                    .addParameter("departmentId", this.departmentId)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<User> all() {
        String sql = "SELECT * FROM users";
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
    }
    @Override
    public void delete() {
        try(Connection con = sql2o.open()) {
            String sql = "DELETE * FROM users";
            con.createQuery(sql)
                    .executeUpdate();
        }
    }
    public static void deletebyId(int id) {
        try(Connection con = sql2o.open()) {
            String sql = "DELETE FROM users WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }
    public static User findById(int id){
        try(Connection con = sql2o.open()) {
            String sql = "SELECT * FROM users where id=:id";
            User user = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(User.class);
            return user;

        }
    }



    
}
