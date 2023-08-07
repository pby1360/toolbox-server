package com.toolbox.toolboxserver.domain.memo.service.impl;

import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import com.toolbox.toolboxserver.domain.memo.repository.MemoRepository;
import com.toolbox.toolboxserver.domain.memo.service.MemoService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    private MemoRepository repository;

    public MemoServiceImpl(MemoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Memo> list(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Memo findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public void save(Memo memo) {
        repository.save(memo);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
