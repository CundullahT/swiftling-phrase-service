package com.swiftling.controller;

import com.swiftling.dto.PhraseDTO;
import com.swiftling.dto.PhraseResultDTO;
import com.swiftling.dto.ProgressDTO;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
//@CrossOrigin({"http://localhost:8762", "http://cundi.onthewifi.com:8762", "http://localhost:5000", "http://cundi.onthewifi.com:5000", "https://swiftlingapp.com", "https://www.swiftlingapp.com"})
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
                                                      @RequestParam(value = "langCode", required = false) String languageCode) {

        List<PhraseDTO> phrases = phraseService.getPhrases(status, languageCode);

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

    @GetMapping("/phrase-details")
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
    public ResponseEntity<ResponseWrapper> getPhraseDetails(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) {

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

        Set<String> tags = phraseService.getTags();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The tags have been retrieved successfully.")
                .data(tags)
                .build());

    }

    @GetMapping("/progress")
    @Operation(summary = "Get the progress of the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The progress has been retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROGRESS_GET_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "503", description = "The external ID of the user account could not be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_EXTERNAL_ID_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getProgress() {

        Map<String, ProgressDTO> progress = phraseService.getProgress();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The progress has been retrieved successfully.")
                .data(progress)
                .build());

    }

    @PutMapping("/update-phrase")
    @Operation(summary = "Update an existing phrase created by the logged in user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PhraseDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_UPDATE_REQUEST_EXAMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The phrase has been updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_UPDATE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "The given phrase does not exist: demo phrase",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid Input(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.VALIDATION_EXCEPTION_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> update(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId, @Valid @RequestBody PhraseDTO phraseDTO) {

        PhraseDTO updatedPhrase = phraseService.update(externalPhraseId, phraseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The phrase has been updated successfully.")
                .data(updatedPhrase)
                .build());

    }

    @PutMapping("/quiz-result")
    @Operation(summary = "Update the statuses of the phrases answered in quiz.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(value = SwaggerExamples.UPDATE_PHRASE_STATUSES_REQUEST_EXAMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The statuses of the phrases have been updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_STATUS_UPDATE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "The given phrase does not exist: demo phrase",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid Input(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.VALIDATION_EXCEPTION_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    ResponseEntity<ResponseWrapper> changePhraseStatuses(@RequestBody Map<UUID, PhraseResultDTO> resultForEachPhrase) {

        phraseService.updateStatuses(resultForEachPhrase);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .message("The statuses of the phrases have been updated successfully.")
                .build());

    }

    @DeleteMapping("/delete-phrase")
    @Operation(summary = "Delete an existing phrase belongs to the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The phrase has been deleted successfully."),
            @ApiResponse(responseCode = "404", description = "The phrase does not exist: 550e8400-e29b-41d4-a716-446655440000",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "The phrase can not be deleted: + 550e8400-e29b-41d4-a716-446655440000",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_DELETED_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<Void> delete(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) {

        phraseService.delete(externalPhraseId);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/delete-all-user-phrases")
    @Operation(summary = "Delete all existing phrase belongs to an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The phrases have been deleted successfully."),
            @ApiResponse(responseCode = "404", description = "The user does not exist: 550e8400-e29b-41d4-a716-446655440000",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                                    examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "The phrases can not be deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASES_NOT_DELETED_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<Void> deleteAllByUser(@RequestParam(value = "external-user-id", required = true) UUID externalOwnerUserAccountId) {

        phraseService.deleteAllByUser(externalOwnerUserAccountId);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("pronunciation/original")
    @Operation(summary = "Get a pronunciation of the original phrase created by the logged in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The pronunciation of the original phrase has been retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "The phrase does not exist: 550e8400-e29b-41d4-a716-446655440000",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PHRASE_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "/some/file/location.txt (No such file or directory)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.FILE_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "/some/file/location.txt (No such file or directory)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.IOEXCEPTION_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getOriginalPronunciation(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) throws Exception {

        String audioFileName = "pronunciation.mp3";

        phraseService.originalPhraseSynthesizeSpeech(externalPhraseId, audioFileName);

        byte[] audioBytes = Files.readAllBytes(Paths.get(audioFileName));

        return ResponseEntity.ok(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .data(audioBytes)
                .build());

    }

    @PostMapping("pronunciation/meaning")
    public ResponseEntity<ResponseWrapper> getMeaningPronunciation(@RequestParam(value = "phrase-id", required = true) UUID externalPhraseId) throws Exception {

        String audioFileName = "pronunciation.mp3";

        phraseService.meaningPhraseSynthesizeSpeech(externalPhraseId, audioFileName);

        byte[] audioBytes = Files.readAllBytes(Paths.get(audioFileName));

        return ResponseEntity.ok(ResponseWrapper.builder()
                .statusCode(HttpStatus.OK)
                .success(true)
                .data(audioBytes)
                .build());

    }

}
