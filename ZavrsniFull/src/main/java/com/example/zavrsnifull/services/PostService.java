package com.example.zavrsnifull.services;

import com.example.zavrsnifull.Repositiries.PostRepository;
import com.example.zavrsnifull.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createNew(Post post, MultipartFile image) throws IOException {
        if (image == null)
            return postRepository.save(post);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS");
        String imgName = dateFormat.format(new Date());
        post.setImageName(imgName);
        File uploadImage = new File("src/main/resources/post_images/" + imgName + ".jpg");
        image.transferTo(new File(uploadImage.getAbsolutePath()));
        return postRepository.save(post);
    }

    public List<Post> getBy(String what, Integer id, Integer offset) {
        return postRepository.getBy(what, id, offset);
    }

    public InputStreamResource getImage(String name) throws IOException{
        return new InputStreamResource(
                new FileInputStream("src/main/resources/post_images/" + name + ".jpg")
        );
    }

}

