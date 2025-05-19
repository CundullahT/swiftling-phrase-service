package com.swiftling.controller;

import com.swiftling.dto.PhraseDTO;
import com.swiftling.dto.wrapper.ExceptionWrapper;
import com.swiftling.dto.wrapper.ResponseWrapper;
import com.swiftling.service.PhraseService;
import com.swiftling.util.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:8762")
@RequestMapping("/api/v1/phrase")
public class PhraseController {

    private final PhraseService phraseService;

    public PhraseController(PhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new phrase to learn.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhraseDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_CREATE_REQUEST_EXAMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The phrase has been created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_CREATE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "The given phrase already exists: demo phrase",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_ALREADY_EXISTS_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid Input(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.VALIDATION_EXCEPTION_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> create(@Valid @RequestBody PhraseDTO phraseDTO) {

        PhraseDTO createdPhrase = phraseService.create(phraseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.CREATED)
                .success(true)
                .message("The phrase has been created successfully.")
                .data(createdPhrase)
                .build());

    }

}
