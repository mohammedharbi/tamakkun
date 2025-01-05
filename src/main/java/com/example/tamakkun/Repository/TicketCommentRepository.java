package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketCommentRepository extends JpaRepository<TicketComment,Integer> {
    List<TicketComment> findByTicketId (Integer ticket_id);

}
