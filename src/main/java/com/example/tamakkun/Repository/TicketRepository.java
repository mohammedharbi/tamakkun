package com.example.tamakkun.Repository;

import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    Ticket findTicketById (Integer id);
    List<Ticket> findByStatus (String status);
    List<Ticket> findByCreatedBy (MyUser user);
}
