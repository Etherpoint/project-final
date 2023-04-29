package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.UserBelong;
import com.javarush.jira.bugtracking.internal.repository.UserBelongRepository;
import com.javarush.jira.bugtracking.to.ObjectType;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import org.springframework.stereotype.Service;

@Service
public class UserBelongService {
    private final UserBelongRepository belongRepository;

    public UserBelongService(UserBelongRepository repository) {
        this.belongRepository = repository;
    }

    //todo added method for subscribing to a task
    //todo added description field initialization
    public void subscribeToTask(User user, Long taskId) {
        UserBelong userBelong = new UserBelong();
        userBelong.setUserId(user.id());
        if (user.hasRole(Role.ADMIN)) {
            userBelong.setUserTypeCode("admin");
            userBelong.setObjectId(taskId);
            userBelong.setObjectType(ObjectType.TASK);
            userBelong.setDescription("subscription");
            belongRepository.save(userBelong);
        } else if (user.hasRole(Role.DEV)) {
            userBelong.setUserTypeCode("user");
            userBelong.setObjectId(taskId);
            userBelong.setObjectType(ObjectType.TASK);
            userBelong.setDescription("subscription");
            belongRepository.save(userBelong);
        }
    }
}
