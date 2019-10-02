package models;

import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class New implements DBManagement {
    private int id;
    private String title;
    private String body;
    private int departmentId;

    public New(String title, String body, int departmentId) {
        this.title = title;
        this.body = body;
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    @Override
    public boolean equals(Object otherNew) {
        if(!(otherNew instanceof New)){return false;}
        else {
            New newNew = (New) otherNew;
            return this.getBody().equals(newNew.getBody())&&
                    this.getDepartmentId()==(newNew.getDepartmentId())&&
                    this.getTitle().equals(newNew.getTitle());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title,body, departmentId);
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO news (title, body, departmentId) VALUES (:title, :body, :departmentId)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("title", this.title)
                    .addParameter("body", this.body)
                    .addParameter("departmentId", this.departmentId)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<New> all() {
        String sql = "SELECT * FROM news";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(New.class);
        }
    }
    @Override
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE * FROM news";
            con.createQuery(sql)
                    .executeUpdate();
        }
    }
    public static void deletebyId(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM news WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }
    public static New findById(int id){
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM news where id=:id";
            New new1 = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(New.class);
            return new1;

        }
    }
}
