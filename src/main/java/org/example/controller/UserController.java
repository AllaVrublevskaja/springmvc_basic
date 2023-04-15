package org.example.controller;

import org.example.dao.UserDao;
import org.example.dao.UserDbDao;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class UserController {

//    private final UserDao userDao;
    private final UserDbDao userDao;

    @Autowired
    public UserController(UserDbDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userDao.getAll());
        return "users";
    }
    @GetMapping("/users/new")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new_user";
    }
    @PostMapping
    public String createUser(@ModelAttribute User user) {
        userDao.save(user);
        return "index";
    }
    @GetMapping("/find")
    public String createIdForm(Model model) {
        model.addAttribute("user", new User());
        return "showForm";
    }
    @PostMapping("/find/showUser")
    public String showUser(@ModelAttribute User user,Model model) {
        model.addAttribute("user", userDao.findById(user.getId()));
        return "showUser";
    }
    @PostMapping(value = "/find/showuserform", params="home")
    public String home() {
        return "index";
    }

    @PostMapping(value = "/find/showuserform", params="delete")
    public String delete(@ModelAttribute User user,Model model) {
        model.addAttribute("user", userDao.delete(user.getId()));
        return"index";
    }
    @PostMapping(value = "/find/showuserform", params="update")
    public String update(@ModelAttribute User user,Model model) {
        model.addAttribute("user", userDao.update(user.getId(),user));
        return"index";
    }
}
