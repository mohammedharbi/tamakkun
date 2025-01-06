package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_In.TicketCommentDTO_In;
import com.example.tamakkun.DTO_In.TicketDTO_In;
import com.example.tamakkun.DTO_Out.TicketCommentDTO_Out;
import com.example.tamakkun.DTO_Out.TicketDTO_out;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Model.Ticket;
import com.example.tamakkun.Model.TicketComment;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.TicketCommentRepository;
import com.example.tamakkun.Repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketCommentService {
    private final TicketRepository ticketRepository;
    private final AuthRepository authRepository;
    private final TicketCommentRepository ticketCommentRepository;

    public void addComment(Integer user_id,Integer ticket_id, TicketCommentDTO_In ticketCommentDTOIn) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");
        }
        Ticket ticket = ticketRepository.findTicketById(ticket_id);
        if (ticket == null) {
            throw new ApiException("ticket not found");
        }
        // only Admin or the ticket creator is allowed to comment
        if (!ticket.getCreatedBy().getId().equals(user.getId()) && !user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ApiException("Only the ticket creator or an admin can comment on this ticket.");
        }
        if (ticket.getStatus().equalsIgnoreCase("close")){
            throw new ApiException("Comment cannot be added to closed ticket");
        }
        TicketComment ticketComment= new TicketComment();
        ticketComment.setContent(ticketCommentDTOIn.getContent());
        ticketComment.setTicket(ticket);
        ticketComment.setCreatedAt(LocalDate.now());
        ticketComment.setCreatedBy(user);

        ticketCommentRepository.save(ticketComment);
    }

    public List<TicketCommentDTO_Out> getCommentsByTicket(Integer user_id ,Integer ticket_id) {
        MyUser user=authRepository.findMyUserById(user_id);
        if (user==null){
            throw new ApiException("user not found ");
        }
        Ticket ticket= ticketRepository.findTicketById(ticket_id);
        // only Admin or the ticket creator is allowed to comment
        if (!ticket.getCreatedBy().getId().equals(user.getId()) && !user.getRole().equalsIgnoreCase("ADMIN")) {
            throw new ApiException("Only the ticket creator or an admin can show comment on this ticket.");
        }
        return convertTicketCommentToDTO(ticketCommentRepository.findTicketCommentByTicketId(ticket_id));}



    public List<TicketCommentDTO_Out> convertTicketCommentToDTO (Collection<TicketComment> ticketComments){
        List<TicketCommentDTO_Out> ticketCommentDTOOuts = new ArrayList<>();
        for (TicketComment tc:ticketComments){
            ticketCommentDTOOuts.add(new TicketCommentDTO_Out(tc.getContent(),tc.getCreatedAt(),tc.getCreatedBy().getUsername()));}
        return ticketCommentDTOOuts;
    }
}
