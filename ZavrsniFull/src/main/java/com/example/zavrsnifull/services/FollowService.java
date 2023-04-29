package com.example.zavrsnifull.services;

import com.example.zavrsnifull.Repositiries.FollowRepository;
import com.example.zavrsnifull.entities.Follow;
import com.example.zavrsnifull.entities.Notification;
import jakarta.servlet.http.PushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private NotificationService notificationService;

    public Follow createNew(Follow follow){
        notificationService.createNew(new Notification(
                null, LocalDateTime.now(),
                follow.getFollower().getUsername() + " started following you.",
                false,
                follow.getFollowee()
        ));
        return followRepository.save(follow);
    }

    public Map<String, List<Follow>> getFollowData(Integer id){
        return Map.of("followers", followRepository.getFollowers(id), "followees", followRepository.getFollowees(id));
    }

    public boolean delete(Integer followerid, Integer followeeid){
        if (followRepository.get(followerid, followeeid).isEmpty())
            return false;
        followRepository.deleteById(followRepository.get(followerid, followeeid).get().getId());
        return true;
    }

    public boolean isFollowing(Integer followerid, Integer followeeid){
        return followRepository.get(followerid, followeeid).isPresent();
    }
}
