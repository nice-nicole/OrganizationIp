package models;

import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class Department implements DBManagement {
    private int id;
    private String name;
    private String description;
    private int numberOfEmployees;

    public Department(String name, String description, int numberOfEmployees) {
        this.name = name;
        this.description = description;
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }
    @Override
    public boolean equals(Object otherDepartment) {
        if(!(otherDepartment instanceof Department)){return false;}
        else {
            Department newDepartment = (Department) otherDepartment;
            return this.getDescription().equals(newDepartment.getDescription())&&
                    this.getNumberOfEmployees()==newDepartment.getNumberOfEmployees()&&
                    this.getName().equals(newDepartment.getName());
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(name,description,numberOfEmployees);
    }

    @Override
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO departments (name, description, numberOfEmployees) VALUES (:name, :description, :numberOfEmployees)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("description", this.description)
                    .addParameter("numberOfEmployees", this.numberOfEmployees)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Department> all() {
        String sql = "SELECT * FROM departments";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Department.class);
        }
    }
    @Override
    public void delete() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE * FROM departments;";
            con.createQuery(sql)
                    .executeUpdate();
        }
    }
    public static void deletebyId(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM departments WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }
    public static Department findById(int id){
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM departments where id=:id";
            Department department = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Department.class);
            return department;

        }
    }
}
