package com.interview.receipt_processor.controller;

import com.interview.receipt_processor.dto.ErrorResponseDto;
import com.interview.receipt_processor.entity.Receipt;
import com.interview.receipt_processor.exception.ResourceNotFoundException;
import com.interview.receipt_processor.service.IReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.Map;

@Tag(
        name = "CRUD REST APIs for Processing Receipts ",
        description = "CRUD REST APIs for Processing Receipts to CREATE, and FETCH the ammount of points based on generated "
)
@RestController
@RequestMapping(path="/receipts", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class ReceiptController {

    private final IReceiptService iReceiptService;
    @Operation(
            summary = "Create Points ID",
            description = "Processes a receipt and returns a generated ID for tracking earned points."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Returns the ID assigned to the receipt."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "The receipt is invalid. Please verify input.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error. Contact support if the issue persists.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody Receipt receipt) {
        String receiptId = iReceiptService.createPointsId(receipt);
        return ResponseEntity.ok(Map.of("id", receiptId));
    }

    @Operation(
            summary = "Create Points ID",
            description = "Processes a receipt and returns a generated ID for tracking earned points."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns the number of points awarded."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No receipt found for that ID.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error. Contact support if the issue persists.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        Double points = iReceiptService.getPointsById(id);

        if (points == null) {
            throw new ResourceNotFoundException("No receipt found for that ID.");
        }

        int roundedPoints = points.intValue();

        return ResponseEntity.ok(Map.of("points", roundedPoints));
    }
}
