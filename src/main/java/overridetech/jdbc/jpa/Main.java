package overridetech.jdbc.jpa;

import overridetech.jdbc.jpa.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Антон", "Кутявин", (byte) 22);
        userService.saveUser("Паша", "Соломин", (byte) 28);
        userService.saveUser("Катя", "Миронова", (byte) 55);
        userService.saveUser("Маша", "Фомина", (byte) 34);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
