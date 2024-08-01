package com.elice.c4.post.service;

import com.elice.c4.post.domain.Post;
import com.elice.c4.post.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    //임시 데이터 베이스
    List<Post> data= new ArrayList<>();

    private Long generateId(){
        if(data.isEmpty()){
            return 1L;
        }
        return data.get(data.size() -1).getId() +1;
    }
    //자동으로 만들어진 메서드
    public List<Post> getAllPosts() {
//        data.add(new Post("제목","블로그내용"));
        return data;
    }

    public Post savePost(PostDTO postDTO) {
        Post post = new Post(postDTO.getTitle(),postDTO.getContent());
        post.setId(generateId());
        data.add(post);
        return post;
    }
}
