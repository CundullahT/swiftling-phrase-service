package com.swiftling.enums;

import com.swiftling.exception.UnknownLanguageException;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Language {

    AFRIKAANS("Afrikaans"),
    ALBANIAN("Albanian"),
    AMHARIC("Amharic"),
    ARABIC("Arabic"),
    ARMENIAN("Armenian"),
    AZERBAIJANI("Azerbaijani"),
    BASQUE("Basque"),
    BELARUSIAN("Belarusian"),
    BENGALI("Bengali"),
    BOSNIAN("Bosnian"),
    BULGARIAN("Bulgarian"),
    CATALAN("Catalan"),
    CEBUANO("Cebuano"),
    CHINESE_SIMPLIFIED("Chinese (Simplified)"),
    CHINESE_TRADITIONAL("Chinese (Traditional)"),
    CORSICAN("Corsican"),
    CROATIAN("Croatian"),
    CZECH("Czech"),
    DANISH("Danish"),
    DUTCH("Dutch"),
    ENGLISH("English"),
    ESPERANTO("Esperanto"),
    ESTONIAN("Estonian"),
    FINNISH("Finnish"),
    FRENCH("French"),
    FRISIAN("Frisian"),
    GALICIAN("Galician"),
    GEORGIAN("Georgian"),
    GERMAN("German"),
    GREEK("Greek"),
    GUJARATI("Gujarati"),
    HAITIAN_CREOLE("Haitian Creole"),
    HAUSA("Hausa"),
    HAWAIIAN("Hawaiian"),
    HEBREW("Hebrew"),
    HINDI("Hindi"),
    HMONG("Hmong"),
    HUNGARIAN("Hungarian"),
    ICELANDIC("Icelandic"),
    IGBO("Igbo"),
    INDONESIAN("Indonesian"),
    IRISH("Irish"),
    ITALIAN("Italian"),
    JAPANESE("Japanese"),
    JAVANESE("Javanese"),
    KANNADA("Kannada"),
    KAZAKH("Kazakh"),
    KHMER("Khmer"),
    KINYARWANDA("Kinyarwanda"),
    KOREAN("Korean"),
    KURDISH("Kurdish"),
    KYRGYZ("Kyrgyz"),
    LAO("Lao"),
    LATIN("Latin"),
    LATVIAN("Latvian"),
    LITHUANIAN("Lithuanian"),
    LUXEMBOURGISH("Luxembourgish"),
    MACEDONIAN("Macedonian"),
    MALAGASY("Malagasy"),
    MALAY("Malay"),
    MALAYALAM("Malayalam"),
    MALTESE("Maltese"),
    MAORI("Maori"),
    MARATHI("Marathi"),
    MONGOLIAN("Mongolian"),
    MYANMAR_BURMESE("Myanmar (Burmese)"),
    NEPALI("Nepali"),
    NORWEGIAN("Norwegian"),
    NYANJA_CHICHEWA("Nyanja (Chichewa)"),
    ODIA_ORIYA("Odia (Oriya)"),
    PASHTO("Pashto"),
    PERSIAN("Persian"),
    POLISH("Polish"),
    PORTUGUESE("Portuguese"),
    PUNJABI("Punjabi"),
    ROMANIAN("Romanian"),
    RUSSIAN("Russian"),
    SAMOAN("Samoan"),
    SCOTS_GAELIC("Scots Gaelic"),
    SERBIAN("Serbian"),
    SESOTHO("Sesotho"),
    SHONA("Shona"),
    SINDHI("Sindhi"),
    SINHALA_SINHALESE("Sinhala (Sinhalese)"),
    SLOVAK("Slovak"),
    SLOVENIAN("Slovenian"),
    SOMALI("Somali"),
    SPANISH("Spanish"),
    SUNDANESE("Sundanese"),
    SWAHILI("Swahili"),
    SWEDISH("Swedish"),
    TAGALOG_FILIPINO("Tagalog (Filipino)"),
    TAJIK("Tajik"),
    TAMIL("Tamil"),
    TATAR("Tatar"),
    TELUGU("Telugu"),
    THAI("Thai"),
    TURKISH("Turkish"),
    TURKMEN("Turkmen"),
    UKRAINIAN("Ukrainian"),
    URDU("Urdu"),
    UYGHUR("Uyghur"),
    UZBEK("Uzbek"),
    VIETNAMESE("Vietnamese"),
    WELSH("Welsh"),
    XHOSA("Xhosa"),
    YIDDISH("Yiddish"),
    YORUBA("Yoruba"),
    ZULU("Zulu");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    private static final Map<String,Language> BY_VALUE =
            Stream.of(values())
                    .collect(Collectors.toMap(Language::getValue, s -> s));

    public static Language findByValue(String value) {
        Language language = BY_VALUE.get(value);
        if (language == null) {
            throw new UnknownLanguageException("Unknown Language: " + value);
        }
        return language;
    }

}
