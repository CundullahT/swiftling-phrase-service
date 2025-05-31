package com.swiftling.util;

public class SwaggerExamples {

    private SwaggerExamples() {}

    public static final String PHRASE_CREATE_REQUEST_EXAMPLE = "{\n" +
            "  \"originalPhrase\": \"Bonjour\",\n" +
            "  \"originalLanguage\": \"FRENCH\",\n" +
            "  \"meaning\": \"Hello\",\n" +
            "  \"meaningLanguage\": \"ENGLISH\",\n" +
            "  \"phraseTags\": [\n" +
            "    \"greeting\",\n" +
            "    \"basic\"\n" +
            "  ],\n" +
            "  \"notes\": \"Common polite greeting in French.\",\n" +
            "  \"ownerUserAccountId\": \"123e4567-e89b-12d3-a456-426614174000\"\n" +
            "}";

    public static final String USER_UPDATE_REQUEST_EXAMPLE = "{\n" +
            "  \"firstname\": \"John\",\n" +
            "  \"lastname\": \"Doe\",\n" +
            "  \"email\": \"john.doe@example.com\",\n" +
            "}";

    public static final String RESET_PASSWORD_REQUEST_EXAMPLE = "{\n" +
            "  \"newPassword\": \"Pa$$w0rd123\"\n" +
            "}";

    public static final String CHANGE_PASSWORD_REQUEST_EXAMPLE = "{\n" +
            "  \"currentPassword\": \"Pa$$w0rd123\"\n" +
            "  \"newPassword\": \"Pa$$w0rd123\"\n" +
            "}";

    public static final String RESET_PASSWORD_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Password has been reset successfully.\"\n" +
            "}";

    public static final String CHANGE_PASSWORD_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"Password has been changed successfully.\"\n" +
            "}";

    public static final String PHRASE_CREATE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"CREATED\",\n" +
            "  \"message\": \"The phrase has been created successfully.\",\n" +
            "  \"data\": {\n" +
            "    \"externalPhraseId\": \"550e8400-e29b-41d4-a716-446655440000\",\n" +
            "    \"originalPhrase\": \"Bonjour\",\n" +
            "    \"originalLanguage\": \"FRENCH\",\n" +
            "    \"meaning\": \"Hello\",\n" +
            "    \"meaningLanguage\": \"ENGLISH\",\n" +
            "    \"phraseTags\": [\n" +
            "      \"greeting\",\n" +
            "      \"basic\"\n" +
            "    ],\n" +
            "    \"notes\": \"Common polite greeting in French.\",\n" +
            "    \"ownerUserAccountId\": \"123e4567-e89b-12d3-a456-426614174000\"\n" +
            "  }\n" +
            "}";

    public static final String LANGUAGE_GET_ALL_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The languages have been retrieved successfully.\",\n" +
            "  \"data\": [\n" +
            "    \"Afrikaans\",\n" +
            "    \"Albanian\",\n" +
            "    \"Amharic\",\n" +
            "    \"Arabic\",\n" +
            "    \"Armenian\",\n" +
            "    \"Azerbaijani\",\n" +
            "    \"Basque\",\n" +
            "    \"Belarusian\",\n" +
            "    \"Bengali\",\n" +
            "    \"Bosnian\",\n" +
            "    \"Bulgarian\",\n" +
            "    \"Catalan\",\n" +
            "    \"Cebuano\",\n" +
            "    \"Chinese (Simplified)\",\n" +
            "    \"Chinese (Traditional)\",\n" +
            "    \"Corsican\",\n" +
            "    \"Croatian\",\n" +
            "    \"Czech\",\n" +
            "    \"Danish\",\n" +
            "    \"Dutch\",\n" +
            "    \"English\",\n" +
            "    \"Esperanto\",\n" +
            "    \"Estonian\",\n" +
            "    \"Finnish\",\n" +
            "    \"French\",\n" +
            "    \"Frisian\",\n" +
            "    \"Galician\",\n" +
            "    \"Georgian\",\n" +
            "    \"German\",\n" +
            "    \"Greek\",\n" +
            "    \"Gujarati\",\n" +
            "    \"Haitian Creole\",\n" +
            "    \"Hausa\",\n" +
            "    \"Hawaiian\",\n" +
            "    \"Hebrew\",\n" +
            "    \"Hindi\",\n" +
            "    \"Hmong\",\n" +
            "    \"Hungarian\",\n" +
            "    \"Icelandic\",\n" +
            "    \"Igbo\",\n" +
            "    \"Indonesian\",\n" +
            "    \"Irish\",\n" +
            "    \"Italian\",\n" +
            "    \"Japanese\",\n" +
            "    \"Javanese\",\n" +
            "    \"Kannada\",\n" +
            "    \"Kazakh\",\n" +
            "    \"Khmer\",\n" +
            "    \"Kinyarwanda\",\n" +
            "    \"Korean\",\n" +
            "    \"Kurdish\",\n" +
            "    \"Kyrgyz\",\n" +
            "    \"Lao\",\n" +
            "    \"Latin\",\n" +
            "    \"Latvian\",\n" +
            "    \"Lithuanian\",\n" +
            "    \"Luxembourgish\",\n" +
            "    \"Macedonian\",\n" +
            "    \"Malagasy\",\n" +
            "    \"Malay\",\n" +
            "    \"Malayalam\",\n" +
            "    \"Maltese\",\n" +
            "    \"Maori\",\n" +
            "    \"Marathi\",\n" +
            "    \"Mongolian\",\n" +
            "    \"Myanmar (Burmese)\",\n" +
            "    \"Nepali\",\n" +
            "    \"Norwegian\",\n" +
            "    \"Nyanja (Chichewa)\",\n" +
            "    \"Odia (Oriya)\",\n" +
            "    \"Pashto\",\n" +
            "    \"Persian\",\n" +
            "    \"Polish\",\n" +
            "    \"Portuguese\",\n" +
            "    \"Punjabi\",\n" +
            "    \"Romanian\",\n" +
            "    \"Russian\",\n" +
            "    \"Samoan\",\n" +
            "    \"Scots Gaelic\",\n" +
            "    \"Serbian\",\n" +
            "    \"Sesotho\",\n" +
            "    \"Shona\",\n" +
            "    \"Sindhi\",\n" +
            "    \"Sinhala (Sinhalese)\",\n" +
            "    \"Slovak\",\n" +
            "    \"Slovenian\",\n" +
            "    \"Somali\",\n" +
            "    \"Spanish\",\n" +
            "    \"Sundanese\",\n" +
            "    \"Swahili\",\n" +
            "    \"Swedish\",\n" +
            "    \"Tagalog (Filipino)\",\n" +
            "    \"Tajik\",\n" +
            "    \"Tamil\",\n" +
            "    \"Tatar\",\n" +
            "    \"Telugu\",\n" +
            "    \"Thai\",\n" +
            "    \"Turkish\",\n" +
            "    \"Turkmen\",\n" +
            "    \"Ukrainian\",\n" +
            "    \"Urdu\",\n" +
            "    \"Uyghur\",\n" +
            "    \"Uzbek\",\n" +
            "    \"Vietnamese\",\n" +
            "    \"Welsh\",\n" +
            "    \"Xhosa\",\n" +
            "    \"Yiddish\",\n" +
            "    \"Yoruba\",\n" +
            "    \"Zulu\"\n" +
            "  ]\n" +
            "}";

    public static final String TAG_GET_ALL_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The tags have been retrieved successfully.\",\n" +
            "  \"data\": [\n" +
            "    \"noun\",\n" +
            "    \"verb\",\n" +
            "    \"adjective\",\n" +
            "    \"adverb\",\n" +
            "    \"pronoun\"\n" +
            "  ]\n" +
            "}";

    public static final String USER_UPDATE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The regular user is successfully updated.\",\n" +
            "  \"data\": {\n" +
            "    \"firstname\": \"John\",\n" +
            "    \"lastname\": \"Doe\",\n" +
            "    \"username\": \"john.doe\",\n" +
            "    \"email\": \"john.doe@example.com\",\n" +
            "    \"enabled\": true,\n" +
            "    \"role\": {\n" +
            "      \"description\": \"User\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
    
    public static final String USER_ENABLE_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The user account has been enabled successfully.\"\n" +
            "}";
    
    public static final String USER_GET_RESPONSE_SINGLE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The regular user is successfully retrieved.\",\n" +
            "  \"data\": {\n" +
            "    \"firstname\": \"John\",\n" +
            "    \"lastname\": \"Doe\",\n" +
            "    \"username\": \"john.doe\",\n" +
            "    \"email\": \"john.doe@example.com\",\n" +
            "    \"enabled\": true,\n" +
            "    \"role\": {\n" +
            "      \"description\": \"User\"\n" +
            "    }\n" +
            "  }\n" +
            "}";
    
    public static final String ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Access is denied\",\n" +
            "  \"httpStatus\": \"FORBIDDEN\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String USER_FORGOT_PASSWORD_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": true,\n" +
            "  \"statusCode\": \"OK\",\n" +
            "  \"message\": \"The forgot password email was sent successfully.\"\n" +
            "}";

    public static final String PHRASE_ALREADY_EXISTS_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The given phrase already exists: demo phrase\",\n" +
            "  \"httpStatus\": \"CONFLICT\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";
    
    public static final String USER_NOT_FOUND_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The user account does not exist: + sample@email.com\",\n" +
            "  \"httpStatus\": \"NOT_FOUND\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String TOKEN_NOT_FOUND_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The token does not exist.\",\n" +
            "  \"httpStatus\": \"NOT_FOUND\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String TOKEN_EXPIRED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The token has expired.\",\n" +
            "  \"httpStatus\": \"NOT_FOUND\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String USER_ALREADY_ENABLED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"The user account is already enabled/verified.\",\n" +
            "  \"httpStatus\": \"NOT_FOUND\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";

    public static final String USER_NOT_DELETED_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"User can not be deleted.\",\n" +
            "  \"httpStatus\": \"CONFLICT\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\"\n" +
            "}";
    
    public static final String VALIDATION_EXCEPTION_RESPONSE_EXAMPLE = "{\n" +
            "  \"success\": false,\n" +
            "  \"message\": \"Invalid Input(s)\",\n" +
            "  \"httpStatus\": \"BAD_REQUEST\",\n" +
            "  \"localDateTime\": \"2024-01-01T00:00:00.0000000\",\n" +
            "  \"errorCount\": 1,\n" +
            "  \"validationExceptions\": [\n" +
            "    {\n" +
            "      \"errorField\": \"email\",\n" +
            "      \"rejectedValue\": \"john.doe.123456\",\n" +
            "      \"reason\": \"The email address must be in a valid email format.\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

}
