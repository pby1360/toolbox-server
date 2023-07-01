package com.toolbox.toolboxserver.domain.memo.service.impl;

import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import com.toolbox.toolboxserver.domain.memo.repository.MemoRepository;
import com.toolbox.toolboxserver.domain.memo.service.MemoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    private MemoRepository repository;

    public MemoServiceImpl(MemoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Memo> list() {
        return repository.findAll();
    }
}
