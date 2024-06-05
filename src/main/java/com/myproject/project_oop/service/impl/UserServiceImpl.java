package com.myproject.project_oop.service.impl;

import com.myproject.project_oop.model.File;
import com.myproject.project_oop.model.Participant;
import com.myproject.project_oop.model.User;
import com.myproject.project_oop.model.constant.FileType;
import com.myproject.project_oop.model.constant.Status;
import com.myproject.project_oop.repository.UserRepository;
import com.myproject.project_oop.dto.request.user.UpdateUserRequest;
import com.myproject.project_oop.dto.response.user.UserDetailsResponse;
import com.myproject.project_oop.dto.response.user.UserResponse;
import com.myproject.project_oop.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final FileService fileService;

    private final ParticipantService participantService;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByIdAndEmailVerificationToken(Integer id, String token) {
        return userRepository.findByIdAndEmailVerificationToken(id, token).orElse(null);
    }

    @Override
    public User findByIdAndEPasswordResetToken(Integer id, String token) {
        return userRepository.findByIdAndPasswordResetToken(id, token).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.isNull(userName)) {
            throw new AccessDeniedException("Invalid access");
        }

        return userRepository.findByUsername(userName).orElse(null);
    }

    @Override
    public UserDetailsResponse updateUserDetails(UpdateUserRequest request) {
        var user = this.getUser();
        if (!Objects.equals(user.getId(), request.getId())) {
            throw new AccessDeniedException("Invalid access");
        } else {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setAbout(request.getAbout());
            user.setCountry(request.getCountry());
            user.setCity(request.getCity());
            user.setAddress(request.getAddress());
            File new_avatar = File.builder()
                    .filename(request.getAvatarName())
                    .url(request.getAvtUrl())
                    .type(FileType.AVATAR_IMAGE)
                    .build();
            var saved_new_avatar = fileService.save(new_avatar);
            user.setAvatar(saved_new_avatar);
            var saved_user = userRepository.save(user);
            return UserDetailsResponse.buildFromUser(saved_user);
        }
    }

    @Override
    public UserDetailsResponse getUserDetails() {
       var user = this.getUser();
        if (user == null) {
            throw new AccessDeniedException("Invalid access");
        } else {
            return UserDetailsResponse.buildFromUser(user);
        }
    }

    @Override
    public List<UserResponse> getAllVerifiedUser() {
//        var current_user = this.getUser();
        var listUser = userRepository.findAllByStatus(Status.VERIFIED);
        return listUser.stream()
                .map(
                        UserResponse::buildFromUser
                ).toList();
    }


    @Override
    public List<User> findByConversationId(Integer conversationId) {
        return participantService.findByConversationId(conversationId).stream()
                .map(Participant::getUser)
                .collect(Collectors.toList());
    }
}
