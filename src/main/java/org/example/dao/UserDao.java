package org.example.dao;

import org.example.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    private final List<User> users = new ArrayList<>(List.of(
        User.builder()
            .id(1)
            .email("qwerty@mail.ru")
            .password("123")
            .build()
    ));

    public List<User> getAll() {
        return users;
    }

    public User findById(int id) {
        return users
                .stream()
                .filter(u -> u.getId() == id).findFirst().orElseThrow();
    }

    public void save(User user) {users.add(user);}

    public Object delete(int id) {
        users.removeIf(u -> u.getId() == id);
        return null;
    }

    public Object update(int id, User user){
        User userToUpdate = findById(id);
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        return null;
    }
}
