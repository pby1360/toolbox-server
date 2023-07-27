package com.toolbox.toolboxserver.domain.memo.controller;

import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import com.toolbox.toolboxserver.domain.memo.service.MemoService;
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
public class MemoController {

    private MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping
    public List<Memo> getList () {
        return memoService.list();
    }

    @PostMapping
    public ResponseEntity saveMemo (@RequestBody Memo memo) {
        memoService.save(memo);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMemo (@PathVariable long id) {
        memoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
