package com.example.springbootexample.service;

import com.example.springbootexample.domain.BoardDTO;
import com.example.springbootexample.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public boolean registerBoard(BoardDTO params) {
        int queryResult;

        if (params.getIdx() == null)
            queryResult = boardMapper.insertBoard(params);
        else
            queryResult = boardMapper.updateBoard(params);

        return (queryResult == 1) ? true : false;
    }

    @Override
    public BoardDTO getBoardDetail(Long idx) {
        return boardMapper.selectBoardDetail(idx);
    }

    @Override
    public boolean updateBoard(BoardDTO params) {
        int queryResult;

        if (params.getIdx() == null) {
            return false;
        } else {
            queryResult = boardMapper.updateBoard(params);
        }

        return (queryResult == 1);
    }

    @Override
    public int deleteBoard(Long idx, String nickname) {
        int queryResult = 0;


        BoardDTO board = boardMapper.selectBoardDetail(idx);
        if (!nickname.equals(board.getWriter())) {
            //2가 리턴되면 해당 게시글에 대한 권한이 없는것이다.
            return 2;
        }

        if (board != null && "N".equals(board.getDeleteYn())) {
            queryResult = boardMapper.deleteBoard(idx);
        }

        return queryResult == 1 ? 1 : 0;
    }

    @Override
    public List<BoardDTO> getBoardList() {
        List<BoardDTO> boardList = Collections.emptyList();

        int boardTotalCount = boardMapper.selectBoardTotalCount();

        if (boardTotalCount > 0) {
            boardList = boardMapper.selectBoardList();
        }

        return boardList;
    }
}
