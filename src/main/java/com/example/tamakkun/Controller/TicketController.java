package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.TicketDTO_In;
import com.example.tamakkun.Service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/ticket")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/create-complaint-on-post/{user_id}/{post_id}")
    public ResponseEntity createComplaintOnPost (@PathVariable Integer user_id,@PathVariable Integer post_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnPost(user_id,post_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }

    @PostMapping("/create-complaint-on-center/{user_id}/{center_id}")
    public ResponseEntity createComplaintOnCenter (@PathVariable Integer user_id,@PathVariable Integer center_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnCenter(user_id,center_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }


    @PostMapping("/create-complaint-on-comment/{user_id}/{comment_id}")
    public ResponseEntity createComplaintOnComment (@PathVariable Integer user_id,@PathVariable Integer comment_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnComment(user_id,comment_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }

    @PostMapping("/create-complaint-on-parent/{user_id}/{parent_id}")
    public ResponseEntity createComplaintOnParent (@PathVariable Integer user_id,@PathVariable Integer parent_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createComplaintOnParent(user_id,parent_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }


    @PostMapping("/create-suggestion/{user_id}")
    public ResponseEntity createSuggestion (@PathVariable Integer user_id, @RequestBody @Valid TicketDTO_In ticketDTOIn){
        ticketService.createSuggestion(user_id, ticketDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket created successfully!"));
    }

    //admin
    @PutMapping("/change-status/{user_id}/{ticket_id}")
    public ResponseEntity changeStatus (@PathVariable Integer user_id ,@PathVariable Integer ticket_id,@RequestBody String status){
        ticketService.changeStatus(user_id,ticket_id,status);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket's status changed successfully!"));
    }

    @GetMapping("/get-open-tickets")
    public ResponseEntity getAllOpenTickets (){
        return ResponseEntity.status(200).body(ticketService.getAllOpenTickets());
    }

    @GetMapping("/get-close-tickets")
    public ResponseEntity getAllCloseTickets (){
        return ResponseEntity.status(200).body(ticketService.getAllCloseTickets());
    }

    @GetMapping("/get-all-tickets-by-parent/{parent_id}")
    public ResponseEntity getAllTicketByParent (@PathVariable Integer parent_id){
        return ResponseEntity.status(200).body(ticketService.getAllTicketByParent(parent_id));
    }

    @GetMapping("/get-all-tickets-by-center/{center_id}")
    public ResponseEntity getAllTicketByCenter (@PathVariable Integer center_id){
        return ResponseEntity.status(200).body(ticketService.getAllTicketByCenter(center_id));
    }



}
