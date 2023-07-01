package com.toolbox.toolboxserver.domain.memo.service;

import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemoService {
    List<Memo> list();
}
