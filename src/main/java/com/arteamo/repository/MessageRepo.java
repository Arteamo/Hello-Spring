package com.arteamo.repository;

import com.arteamo.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MessageRepo extends CrudRepository<Message, Long> {
    @Transactional
    void deleteMessageById(Long id);

    Message findMessageById(Long id);
}
