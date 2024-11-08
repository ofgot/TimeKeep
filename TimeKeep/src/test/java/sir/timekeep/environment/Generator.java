package sir.timekeep.environment;

import sir.timekeep.model.Group;
import sir.timekeep.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {

    private static final Random random = new Random();

    public static int generateRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int generateRandomInt(){
        return random.nextInt();
    }

    public static User generateUser() {
        final User user = new User();
        user.setName("FirstName" + generateRandomInt());
        user.setSurname("Surname" + generateRandomInt());
        user.setUsername("username" + generateRandomInt());
        user.setEmail(user.getUsername() + "@gmail.com");
        user.setPassword(Integer.toString(generateRandomInt()));

        return user;
    }

    public static List<Group> generateGroupsForUser() {
        final List<Group> groups = new ArrayList<>();
        for (int i = 0; i < generateRandomInt(2, 10); i++) {
            Group newGroup = new Group();
            newGroup.setName("Name" + generateRandomInt());
            groups.add(newGroup);
        }
        return groups;
    }
}
