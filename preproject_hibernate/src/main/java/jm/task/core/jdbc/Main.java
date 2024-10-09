package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoHibernateImpl();
        UserService userService = new UserServiceImpl(userDao);


        userService.createUsersTable();

        userService.saveUser("Shrekes", "Ogrovoch", (byte) 42);
        userService.saveUser("Biba", "Bobova", (byte) 23);
        userService.saveUser("Natasha", "Rostova", (byte) 16);
        userService.saveUser("Barash", "The Author", (byte) 19);
        userService.saveUser("Nyusha", "Pepa", (byte) 17);
        userService.saveUser("Krosh", "The Rabbit", (byte) 16);
        userService.saveUser("Ezhik", "Indica", (byte) 16);
        userService.saveUser("Bobr", "Kurwa", (byte) 21);
        userService.saveUser("Belka", "Mini Kurwa", (byte) 26);

        userService.getAllUsers();
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.removeUserById(1);

        userService.cleanUsersTable();

        userService.dropUsersTable();




    }
}
