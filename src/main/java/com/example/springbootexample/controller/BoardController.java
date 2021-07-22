package com.example.springbootexample.controller;

import com.example.springbootexample.domain.BoardDTO;
import com.example.springbootexample.service.BoardService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/write.do")
    public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {

        if (idx == null) {
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = boardService.getBoardDetail(idx);
            if (board == null) {
                return "redirect:/board/list.do";
            }
            model.addAttribute("board", board);
        }
        return "/board/write";
    }


    @GetMapping("/detail")
    public ResponseEntity<String> getBoardDetail(@RequestParam(value = "idx", required = true) Long idx) {

        JsonObject obj = new JsonObject();
        BoardDTO board = boardService.getBoardDetail(idx);

        if (board.getDeleteYn().equals("Y")) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        System.out.println(new Gson().toJson(board));
        System.out.println(HttpStatus.OK);
        return new ResponseEntity<>(new Gson().toJson(board), HttpStatus.OK);
    }

    @PostMapping("/register")
//    public String registerBoard(@RequestBody Map<String, Object> params) {
    public ResponseEntity<String> registerBoard(@RequestBody String params) {
        try {
            System.out.println(params);
            JSONObject json = new JSONObject(params).getJSONObject("params");
            System.out.println(json);

            BoardDTO newBoard = new BoardDTO();
            newBoard.setTitle(json.get("title").toString());
            newBoard.setContent(json.get("content").toString());
            newBoard.setWriter(json.get("writer").toString());

            boolean isRegisterd = boardService.registerBoard(newBoard);

            if (isRegisterd) {
                return new ResponseEntity<>("register Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("register failed", HttpStatus.SERVICE_UNAVAILABLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("register failed", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateBoard(@RequestBody String params) {

        try {
            System.out.println(params);
            JSONObject json = new JSONObject(params).getJSONObject("params");
            System.out.println(json);

            BoardDTO updaters = new BoardDTO();

            System.out.println(json.get("idx"));
            updaters.setIdx(Long.parseLong(json.get("idx").toString()));
            updaters.setTitle(json.get("title").toString());
            updaters.setContent(json.get("content").toString());
            updaters.setWriter(json.get("writer").toString());

            if (boardService.updateBoard(updaters)) {
                return new ResponseEntity<>("update Success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("update failed", HttpStatus.SERVICE_UNAVAILABLE);
            }

        } catch (JSONException e) {
            System.out.println("Json Exception Occurred");
            e.printStackTrace();
            return new ResponseEntity<>("update failed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println("Other Exception Occurred");
            e.printStackTrace();
            return new ResponseEntity<>("update failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteBoard(@RequestParam(value = "user-nickname") String nickname, @RequestParam(value = "idx") Long idx) {
        try {
            System.out.println(idx);
            System.out.println(nickname);

            int result = boardService.deleteBoard(idx, nickname);

            if (result == 1) {
                return new ResponseEntity<>("delete Success", HttpStatus.OK);
            } else if (result == 2) {
                return new ResponseEntity<>("does not have authorization", HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>("delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            System.out.println("Other Exception Occurred");
            e.printStackTrace();
            return new ResponseEntity<>("delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/list")
    public ResponseEntity<List> listBoard() {
        try {
            List<BoardDTO> boards = boardService.getBoardList();

            System.out.println(boards);

            return new ResponseEntity<>(boards, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}