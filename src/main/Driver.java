package main;

import java.io.*;
import java.util.*;

public class Driver {
    private static final String FILENAME = "social_network.edgelist";

    public static void main(String[] args) {
        String filePath = findFilePath();
        if (filePath == null) {
            System.err.println("File not found.");
            return;
        }

        Map<Integer, TwitterUser> users = readUsersFromFile(filePath);
        if (users == null) return;

        testMethods(users);
    }

    private static String findFilePath() {
        File file = new File(FILENAME);
        if (file.exists()) return FILENAME;

        file = new File("src/" + FILENAME);
        if (file.exists()) return "src/" + FILENAME;

        return null;
    }

    // Time Complexity: O(E)
    private static Map<Integer, TwitterUser> readUsersFromFile(String filePath) {
        Map<Integer, TwitterUser> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length < 2) continue;

                int userId = Integer.parseInt(parts[0]);
                int followingId = Integer.parseInt(parts[1]);

                TwitterUser user = users.computeIfAbsent(userId, k -> new TwitterUser(userId));
                user.addFollowing(followingId);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        return users;
    }

    private static void testMethods(Map<Integer, TwitterUser> users) {
        int testDepth = 3;

        TwitterUser user = users.get(0);
        if (user != null) {
            System.out.println("Neighborhood of user 0: " + user.getNeighborhood(testDepth, users, new HashSet<>()));
        } else {
            System.out.println("User with ID 0 not found.");
        }

        user = users.get(1);
        if (user != null) {
            System.out.println("Followers of user 1: " + user.getFollowers(users));
        } else {
            System.out.println("User with ID 1 not found.");
        }
    }
}
