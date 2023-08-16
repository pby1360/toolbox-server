package com.toolbox.toolboxserver.domain.memo.controller;

import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import com.toolbox.toolboxserver.domain.memo.service.MemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/memo")
@Slf4j
public class MemoController {


    private MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/{id}")
    public Memo getMemo (@PathVariable Long id) {
        return memoService.findById(id);
    }

    @GetMapping("/users/{userId}")
    public List<Memo> getList (@PathVariable Long userId) {
        log.info(":: getList");
        return memoService.list(userId);
    }

    @PostMapping
    public ResponseEntity saveMemo (@RequestBody Memo memo) {
        log.info(":: saveMemo");
        Memo newMemo = memoService.save(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMemo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMemo (@PathVariable Long id) {
        log.info(":: deleteMemo");
        memoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
