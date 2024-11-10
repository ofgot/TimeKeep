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

    public static LocalDateTime generateDateTimeInPast(){
        LocalDateTime time = LocalDateTime.now();
        Integer year = generateRandomInt(2000, time.getYear());
        Integer month = generateRandomInt(1, time.getMonthValue());
        Integer day = generateRandomInt(1, time.getDayOfMonth() - 1);
        Integer hour = generateRandomInt(1, 23);
        Integer minute = generateRandomInt(1, 59);
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    public static LocalDateTime generateDateTimeInFuture(){
        LocalDateTime time = LocalDateTime.now();
        Integer year = generateRandomInt(time.getYear(), 2030);
        Integer month = generateRandomInt(time.getMonthValue(), 12);
        Integer day = generateRandomInt(time.getDayOfMonth(), 31);
        Integer hour = generateRandomInt(1, 23);
        Integer minute = generateRandomInt(1, 60);
        return LocalDateTime.of(year, month, day, hour, minute);
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

    public static Capsule generateCapsule() {
        int count = generateRandomInt();
        String name = "Test capsule No." + count;
        String description = "This is test capsule No." + count;
        User postCreator = generateUser();
        LocalDateTime time = LocalDateTime.of(2030, 10, 1, 10, 56);
        return new Capsule(name, description, null, postCreator, null, time);
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

    public static Capsule generateClosedCapsule(User postCreator) {
        int count = generateRandomInt();
        String name = "Test capsule No." + count;
        String description = "This is test capsule No." + count;
        LocalDateTime time = generateDateTimeInFuture();
        return new Capsule(name, description, null, postCreator, null, time);
    }

    public static Capsule generateOpenedCapsule(User postCreator) {
        int count = generateRandomInt();
        String name = "Test capsule No." + count;
        String description = "This is test capsule No." + count;
        LocalDateTime time = generateDateTimeInPast();
        return new Capsule(name, description, null, postCreator, null, time);
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
