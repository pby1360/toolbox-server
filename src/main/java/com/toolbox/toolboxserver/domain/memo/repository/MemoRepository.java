package com.toolbox.toolboxserver.domain.memo.repository;

import com.toolbox.toolboxserver.domain.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByUserId(Long id);
}
