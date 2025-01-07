package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.TicketDTO_In;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/ticket")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/create-complaint-on-post/{post_id}")
    public ResponseEntity createComplaintOnPost (@AuthenticationPrincipal MyUser user, @PathVariable Integer post_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnPost(user.getId(), post_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }

    @PostMapping("/create-complaint-on-center/{center_id}")
    public ResponseEntity createComplaintOnCenter (@AuthenticationPrincipal MyUser user,@PathVariable Integer center_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnCenter(user.getId(), center_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }


    @PostMapping("/create-complaint-on-comment/{comment_id}")
    public ResponseEntity createComplaintOnComment (@AuthenticationPrincipal MyUser user,@PathVariable Integer comment_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnComment(user.getId(), comment_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }

    @PostMapping("/create-complaint-on-parent/{parent_id}")
    public ResponseEntity createComplaintOnParent (@AuthenticationPrincipal MyUser user,@PathVariable Integer parent_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnParent(user.getId(), parent_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }


    @PostMapping("/create-suggestion")
    public ResponseEntity createSuggestion (@AuthenticationPrincipal MyUser user ,@RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createSuggestion(user.getId(), ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }

    //admin
    @PutMapping("/change-status/{ticket_id}")
    public ResponseEntity changeStatus (@AuthenticationPrincipal MyUser user ,@PathVariable Integer ticket_id,@RequestBody String status){
        ticketService.changeStatus(user.getId(), ticket_id,status);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket's status changed successfully!"));
    }

    @GetMapping("/get-open-tickets")
    public ResponseEntity getAllOpenTickets (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(ticketService.getAllOpenTickets());
    }

    @GetMapping("/get-close-tickets")
    public ResponseEntity getAllCloseTickets (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(ticketService.getAllCloseTickets());
    }

    @GetMapping("/get-all-tickets-by-parent")
    public ResponseEntity getAllTicketByParent (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(ticketService.getAllTicketByParent(user.getId()));
    }

    @GetMapping("/get-all-tickets-by-center")
    public ResponseEntity getAllTicketByCenter (@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(ticketService.getAllTicketByCenter(user.getId()));
    }



}
