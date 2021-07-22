package com.example.springbootexample;

import com.example.springbootexample.service.BoardServiceImpl;
import com.example.springbootexample.domain.BoardDTO;
import com.example.springbootexample.mapper.BoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceTest {
    @Autowired
    private BoardMapper boardMapper;

    @Test
    public void testOfRegister(){
        BoardDTO params = new BoardDTO();
        params.setTitle("register test title");
        params.setContent("register test content");
        params.setWriter("register test user");

    }
}
