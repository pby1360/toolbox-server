package com.toolbox.toolboxserver.domain.memo.service;

import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemoService {
    List<Memo> list(Long userId);
    Memo findById(Long id);
    Memo save(Memo memo);
    void delete(Long id);
}
