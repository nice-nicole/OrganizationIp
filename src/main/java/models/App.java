package models;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args){
        staticFileLocation("/public");

        //get the first page
        get("/", (req, resp)->{
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/success", (req, resp)->{
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
        //get: show a form to create a new department
        get("/departments/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = Department.all();
            model.put("departments", departments);
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new user
        get("/users/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department>departments = Department.all();
            List<User>users = User.all();
            model.put("departments", departments);
            model.put("users",users);
            return new ModelAndView(model, "user-form.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new news
        get("/news/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Department> departments = Department.all();
            List<New> news = New.all();
            model.put("departments", departments);
            model.put("news", news);
            return new ModelAndView(model, "news-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new department
        post("/success1", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String description = req.queryParams("description");
            int numberOfEmployees = Integer.parseInt(req.queryParams("numberOfEmployees"));
            Department newDepartment = new Department(name,description, numberOfEmployees);
            newDepartment.save();
            System.out.println(newDepartment);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new user
        post("/success", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            String position = req.queryParams("position");
            String role = req.queryParams("role");
            int departmentId= Integer.parseInt(req.queryParams("departmentId"));
            User newUser = new User(name,position, role, departmentId);
            newUser.save();
            System.out.println(newUser);
            res.redirect("/success");
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to create a new news
        post("/success2", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String title = req.queryParams("title");
            String body = req.queryParams("body");
            int departmentId= Integer.parseInt(req.queryParams("departmentId"));
            New newNews = new New(title,body, departmentId);
            newNews.save();
            System.out.println(newNews);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //
        //get: delete a department by id
        get("/departments/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("deleteDepartment", true);
            int depId = Integer.parseInt("int");
//            int idToDelete= Department.findById(depId);
//            Department department= Department.deletebyId(idToDelete);
            return new ModelAndView(model, "department-form.hbs");
        }, new HandlebarsTemplateEngine());

    }

}
