package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_In.TicketDTO_In;
import com.example.tamakkun.DTO_Out.TicketDTO_out;
import com.example.tamakkun.Model.*;
import com.example.tamakkun.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final AuthRepository authRepository;
    private final CentreRepository centreRepository;
    private final PostRepository postRepository;
    private final ParentRepository parentRepository;
    private final CommentRepository commentRepository;



    public void createComplaintOnPost(Integer user_id, Integer post_id, TicketDTO_In ticketDTOIn) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Post post = postRepository.findPostById(post_id);
        if (post == null) {
            throw new ApiException("post not found");}

        Ticket ticket = new Ticket();
        ticket.setType("COMPLAINT");
        ticket.setDescription(ticketDTOIn.getDescription());
        ticket.setCreatedBy(user);
        ticket.setPost(post);
        ticket.setStatus("open");
        ticket.setCreatedAt(LocalDate.now());

        ticketRepository.save(ticket);
    }

    public void createComplaintOnCenter(Integer user_id, Integer center_id, TicketDTO_In ticketDTOIn) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Centre centre = centreRepository.findCentreById(center_id);
        if (centre == null) {
            throw new ApiException("centre not found");}
        Ticket ticket = new Ticket();
        ticket.setType("COMPLAINT");
        ticket.setDescription(ticketDTOIn.getDescription());
        ticket.setCreatedBy(user);
        ticket.setCentre(centre);
        ticket.setStatus("open");
        ticket.setCreatedAt(LocalDate.now());

        ticketRepository.save(ticket);
    }


    public void createComplaintOnComment(Integer user_id, Integer comment_id, TicketDTO_In ticketDTOIn) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Comment comment = commentRepository.findCommentById(comment_id);
        if (comment == null) {
            throw new ApiException("comment not found");}

        Ticket ticket = new Ticket();
        ticket.setType("COMPLAINT");
        ticket.setDescription(ticketDTOIn.getDescription());
        ticket.setCreatedBy(user);
        ticket.setComment(comment);
        ticket.setStatus("open");
        ticket.setCreatedAt(LocalDate.now());

        ticketRepository.save(ticket);
    }


    public void createComplaintOnParent(Integer user_id, Integer parent_id, TicketDTO_In ticketDTOIn) {
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Parent parent = parentRepository.findParentById(parent_id);
        if (parent == null) {
            throw new ApiException("parent not found");}
        Ticket ticket = new Ticket();
        ticket.setType("COMPLAINT");
        ticket.setDescription(ticketDTOIn.getDescription());
        ticket.setCreatedBy(user);
        ticket.setParent(parent);
        ticket.setStatus("open");
        ticket.setCreatedAt(LocalDate.now());

        ticketRepository.save(ticket);
    }

    public void createSuggestion (Integer user_id , TicketDTO_In ticketDTOIn){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Ticket ticket= new Ticket();
        ticket.setType("SUGGESTION");
        ticket.setDescription(ticketDTOIn.getDescription());
        ticket.setCreatedBy(user);
        ticket.setStatus("open");
        ticket.setCreatedAt(LocalDate.now());

        ticketRepository.save(ticket);
    }



    public void changeStatus (Integer user_id , Integer ticket_id,String status){
        MyUser user = authRepository.findMyUserById(user_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Ticket ticket = ticketRepository.findTicketById(ticket_id);
        if (ticket == null) {
            throw new ApiException("ticket not found");}
        if (!status.matches("close|in_progress")){
            throw new ApiException("Invalid status. Allowed value are: close, in_progress");}
        ticket.setStatus(status);
        ticket.setHandledAt(LocalDate.now());
        ticketRepository.save(ticket);
    }


    //Admin
    public List<TicketDTO_out> getAllOpenTickets (){
        return convertToDTOList(ticketRepository.findByStatus("open")) ;}

    public List<TicketDTO_out> getAllCloseTickets (){
        return convertToDTOList(ticketRepository.findByStatus("close")) ;}

    public List<TicketDTO_out> getAllTicketByParent (Integer parent_id){
        MyUser user =authRepository.findMyUserById(parent_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Parent parent=parentRepository.findParentByMyUser(user);
        if (parent == null) {
            throw new ApiException("parent not found");}
        return convertToDTOList(ticketRepository.findByCreatedBy(user));
    }

    public List<TicketDTO_out> getAllTicketByCenter (Integer center_id){
        MyUser user =authRepository.findMyUserById(center_id);
        if (user == null) {
            throw new ApiException("user not found");}
        Centre centre=centreRepository.findCentreByMyUser(user);
        if (centre == null) {
            throw new ApiException("centre not found");}
        return convertToDTOList(ticketRepository.findByCreatedBy(user));
    }



    private List<TicketDTO_out> convertToDTOList(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private TicketDTO_out convertToDTO(Ticket ticket) {
        String target = null;

        if (ticket.getPost() != null) {
            target = "Post: " + ticket.getPost().getTitle();
        } else if (ticket.getCentre() != null) {
            target = "Centre: " + ticket.getCentre().getName();
        } else if (ticket.getComment() != null) {
            target = "Comment: " + ticket.getComment().getContent();
        } else if (ticket.getParent() != null) {
            target = "Parent: " + ticket.getParent().getFullName();
        }

        return new TicketDTO_out(
                ticket.getType(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getCreatedBy().getUsername(),
                target
        );
    }
}
