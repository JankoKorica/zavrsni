package com.example.zavrsnifull.services;

import com.example.zavrsnifull.Repositiries.UserRepository;
import com.example.zavrsnifull.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createNew(User user, MultipartFile image) throws IOException {
        if (userRepository.getUserByUsername(user.getUsername()).isPresent())
            return null;
        if(image == null){
            user.setImageName("DefaultAvatar");
            return userRepository.save(user);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS");
        String imgName = dateFormat.format(new Date());
        user.setImageName(imgName);
        File uploadImage = new File("src/main/resources/profile_pics/" + imgName + ".jpg");
        image.transferTo(new File(uploadImage.getAbsolutePath()));
        return userRepository.save(user);
    }

    public InputStreamResource getImage(String name) throws IOException{
        return new InputStreamResource(
                new FileInputStream("src/main/resources/profile_pics/" + name + ".jpg")
        );
    }

    public User getById(Integer id){
        if (userRepository.findById(id).isEmpty())
            return null;
        return userRepository.findById(id).get();
    }

    public List<User> search(String username){
        return userRepository.search(username);
    }

    @Transactional
    public User editUser(Integer userid, User editedUser, MultipartFile image) throws IOException{

        User user = userRepository.findById(userid).orElse(null);

        if (user == null)
            return null;

        if (!(editedUser.getUsername().equals("") || editedUser.getUsername() == null || editedUser.getUsername().equals(user.getUsername()))){
            if (userRepository.getUserByUsername(editedUser.getUsername()).isPresent())
                return null;
            user.setUsername(editedUser.getUsername());
        }

        if (!(editedUser.getPassword() == null || editedUser.getPassword().equals(user.getPassword())))
            user.setPassword(editedUser.getPassword());

        if (!(editedUser.getEmail().equals("") || editedUser.getEmail() == null || editedUser.getEmail().equals(user.getEmail())))
            user.setEmail(editedUser.getEmail());

        if (!(editedUser.getDob() == null || editedUser.getDob().equals(user.getDob())))
            user.setDob(editedUser.getDob());

        if (!(editedUser.getAbout().equals("") || editedUser.getAbout() == null || editedUser.getAbout().equals(user.getAbout())))
            user.setAbout(editedUser.getAbout());

        if (image == null)
            return user;

        File newImage = new File("src/main/resources/profile_pics/" + user.getImageName() + ".jpg");
        FileCopyUtils.copy(image.getBytes(), newImage);

        return user;
    }

    public User logIn(String username, String password){
        if(userRepository.logIn(username, password).isEmpty()){
            return null;
        }
        return userRepository.logIn(username,password).get();
    }

    public boolean delete(Integer id){
        if (!userRepository.existsById(id))
            return false;
        userRepository.deleteById(id);
        return true;
    }
}
