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

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:8762")
@RequestMapping("/api/v1/phrase")
public class PhraseController {

    private final PhraseService phraseService;

    public PhraseController(PhraseService phraseService) {
        this.phraseService = phraseService;
    }

    @PostMapping("/add-phrase")
    @Operation(summary = "Add a new phrase to learn for the logged in user.",
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
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> create(@Valid @RequestBody PhraseDTO phraseDTO) {

        PhraseDTO createdPhrase = phraseService.create(phraseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.CREATED)
                .success(true)
                .message("The phrase has been created successfully.")
                .data(createdPhrase)
                .build());

    }

    @GetMapping("/phrases")
    @Operation(summary = "Get all the phrases created by the currently logged in user, with/without status and language filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The phrases have been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_GET_ALL_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getPhrases(@RequestParam(value = "status", required = false) String status,
                                                      @RequestParam(value = "lang", required = false) String language) {

        List<PhraseDTO> phrases = phraseService.getPhrases(status, language);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The phrases have been retrieved successfully.")
                .data(phrases)
                .build());

    }

    @GetMapping("/last-ten-phrases")
    @Operation(summary = "Get the last 10 phrases created by the currently logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The last 10 phrases have been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_GET_LAST_TEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getLastTenPhrases() {

        List<PhraseDTO> phrases = phraseService.getLastTenPhrases();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The last 10 phrases have been retrieved successfully.")
                .data(phrases)
                .build());

    }

    @GetMapping("/phrase-details/{phrase-id}")
    @Operation(summary = "Get the details of a phrase by using the External Phrase ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The phrase has been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_GET_SINGLE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "The user account does not exist: + sample@email.com",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getPhrases(@PathVariable("phrase-id") UUID externalPhraseId) {

        PhraseDTO phrase = phraseService.getPhraseDetails(externalPhraseId);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The phrase has been retrieved successfully.")
                .data(phrase)
                .build());

    }

    @GetMapping("/languages")
    @Operation(summary = "Get all the language options.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The languages have been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.LANGUAGE_GET_ALL_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getLanguages() {

        List<String> languages = phraseService.getLanguages();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The languages have been retrieved successfully.")
                .data(languages)
                .build());

    }

    @GetMapping("/quiz-languages")
    @Operation(summary = "Get all the quiz language options for the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The quiz languages have been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.QUIZ_LANGUAGE_GET_ALL_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getQuizLanguages() {

        List<String> languages = phraseService.getQuizLanguages();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The quiz languages have been retrieved successfully.")
                .data(languages)
                .build());

    }

    @GetMapping("/tags")
    @Operation(summary = "Get all the tag options for the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The tags have been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.TAG_GET_ALL_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getTags() {

        List<String> tags = phraseService.getTags();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The tags have been retrieved successfully.")
                .data(tags)
                .build());

    }

}
