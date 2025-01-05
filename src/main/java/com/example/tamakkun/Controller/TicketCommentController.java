package com.example.tamakkun.Controller;

import com.example.tamakkun.API.ApiResponse;
import com.example.tamakkun.DTO_In.TicketCommentDTO_In;
import com.example.tamakkun.Service.TicketCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tamakkun-system/ticket-comment")
public class TicketCommentController {
    private final TicketCommentService ticketCommentService;


    @PostMapping("/add-comment/{user_id}/{ticket_id}")
    public ResponseEntity addComment (@PathVariable Integer user_id ,@PathVariable Integer ticket_id,@RequestBody @Valid TicketCommentDTO_In ticketCommentDTOIn){
        ticketCommentService.addComment(user_id,ticket_id, ticketCommentDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Comment added successfully!"));
    }


    @GetMapping("/get-comments-by-ticket/{user_id}/{ticket_id}")
    public ResponseEntity getCommentsByTicket (@PathVariable Integer user_id,@PathVariable Integer ticket_id){
        return ResponseEntity.status(200).body(ticketCommentService.getCommentsByTicket(user_id,ticket_id));
    }

}
