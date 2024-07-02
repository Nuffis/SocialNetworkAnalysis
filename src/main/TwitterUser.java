package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TwitterUser {
    private final int id;
    private final Set<Integer> following;

    public TwitterUser(int id) {
        this.id = id;
        this.following = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void addFollowing(int userId) {
        following.add(userId);
    }

    public Set<Integer> getFollowing() {
        return following;
    }

    
    public List<Integer> getNeighborhood(int depth, Map<Integer, TwitterUser> users, Set<Integer> visited) {
        if (depth == 0) return Collections.emptyList();
        visited.add(this.id);

        List<Integer> neighborhood = new ArrayList<>();
        for (int followeeId : following) {
            if (!visited.contains(followeeId)) {
                neighborhood.add(followeeId);
                TwitterUser followee = users.get(followeeId);
                if (followee != null) {
                    neighborhood.addAll(followee.getNeighborhood(depth - 1, users, visited));
                }
            }
        }
        return neighborhood;
    }

    
    public List<Integer> getFollowers(Map<Integer, TwitterUser> users) {
        List<Integer> followers = new ArrayList<>();
        for (TwitterUser user : users.values()) {
            if (user.getFollowing().contains(this.id)) {
                followers.add(user.getId());
            }
        }
        return followers;
    }
}