package sir.timekeep.environment;

import sir.timekeep.model.*;

import java.time.LocalDateTime;
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

    public static Memory generateMemory() {
        int count = generateRandomInt();
        String name = "Test memory No." + count;
        String description = "This is test memory No." + count;
        User postCreator = generateUser();
        return new Memory(name, description, null, postCreator, null);
    }

    public static Memory generateMemory(User postCreator) {
        int count = generateRandomInt();
        String name = "Test memory No." + count;
        String description = "This is test memory No." + count;
        return new Memory(name, description, null, postCreator, null);
    }

    public static Capsule generateCapsule(User postCreator, LocalDateTime dateTime) {
        int count = generateRandomInt();
        String name = "Test capsule No." + count;
        String description = "This is test capsule No." + count;
        return new Capsule(name, description, null, postCreator, null, dateTime);
    }

    public static Capsule generateOpenedCapsule(User postCreator){
        return generateCapsule(postCreator, LocalDateTime.now().plusDays(generateRandomInt(30, 7300)));
    }

    public static Capsule generateClosedCapsule(User postCreator){
        return generateCapsule(postCreator, LocalDateTime.now().minusDays(generateRandomInt(30, 7300)));
    }

    public static Group generateGroup(){
        Group group = new Group();
        group.setName("Group No." + generateRandomInt());
        User creator = generateUser();
        creator.setRole(Role.PREMIUM);
        List <User> users = new ArrayList<>();
        users.add(creator);
        for (int i = 0; i < generateRandomInt(2,5); i++){
            User tmpUser = generateUser();
            tmpUser.setRole(Role.USUAL);
            users.add(tmpUser);
        }
        group.setUsers(users);
        group.setGroupCreator(creator);
        return group;
    }

    public static Group generateGroupWithCreator(User creator){
        Group group = new Group();
        group.setName("Group No." + generateRandomInt());
        List <User> users = new ArrayList<>();
        users.add(creator);
        for (int i = 0; i < generateRandomInt(2,5); i++){
            User tmpUser = generateUser();
            tmpUser.setRole(Role.USUAL);
            users.add(tmpUser);
        }
        group.setUsers(users);
        group.setGroupCreator(creator);
        return group;
    }
}
