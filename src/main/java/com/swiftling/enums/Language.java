package com.swiftling.enums;

import com.swiftling.exception.UnknownLanguageException;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Language {

    AFRIKAANS("af", "Afrikaans"),
    ALBANIAN("sq", "Albanian"),
    AMHARIC("am", "Amharic"),
    ARABIC("ar", "Arabic"),
    ARMENIAN("hy", "Armenian"),
    AZERBAIJANI("az", "Azerbaijani"),
    BASQUE("eu", "Basque"),
    BELARUSIAN("be", "Belarusian"),
    BENGALI("bn", "Bengali"),
    BOSNIAN("bs", "Bosnian"),
    BULGARIAN("bg", "Bulgarian"),
    CATALAN("ca", "Catalan"),
    CEBUANO("ceb", "Cebuano"),
    CHINESE_SIMPLIFIED("zh-CN", "Chinese (Simplified)"),
    CHINESE_TRADITIONAL("zh-TW", "Chinese (Traditional)"),
    CORSICAN("co", "Corsican"),
    CROATIAN("hr", "Croatian"),
    CZECH("cs", "Czech"),
    DANISH("da", "Danish"),
    DUTCH("nl", "Dutch"),
    ENGLISH("en", "English"),
    ESPERANTO("eo", "Esperanto"),
    ESTONIAN("et", "Estonian"),
    FINNISH("fi", "Finnish"),
    FRENCH("fr", "French"),
    FRISIAN("fy", "Frisian"),
    GALICIAN("gl", "Galician"),
    GEORGIAN("ka", "Georgian"),
    GERMAN("de", "German"),
    GREEK("el", "Greek"),
    GUJARATI("gu", "Gujarati"),
    HAITIAN_CREOLE("ht", "Haitian Creole"),
    HAUSA("ha", "Hausa"),
    HAWAIIAN("haw", "Hawaiian"),
    HEBREW("he", "Hebrew"),
    HINDI("hi", "Hindi"),
    HMONG("hmn", "Hmong"),
    HUNGARIAN("hu", "Hungarian"),
    ICELANDIC("is", "Icelandic"),
    IGBO("ig", "Igbo"),
    INDONESIAN("id", "Indonesian"),
    IRISH("ga", "Irish"),
    ITALIAN("it", "Italian"),
    JAPANESE("ja", "Japanese"),
    JAVANESE("jv", "Javanese"),
    KANNADA("kn", "Kannada"),
    KAZAKH("kk", "Kazakh"),
    KHMER("km", "Khmer"),
    KINYARWANDA("rw", "Kinyarwanda"),
    KOREAN("ko", "Korean"),
    KURDISH("ku", "Kurdish"),
    KYRGYZ("ky", "Kyrgyz"),
    LAO("lo", "Lao"),
    LATIN("la", "Latin"),
    LATVIAN("lv", "Latvian"),
    LITHUANIAN("lt", "Lithuanian"),
    LUXEMBOURGISH("lb", "Luxembourgish"),
    MACEDONIAN("mk", "Macedonian"),
    MALAGASY("mg", "Malagasy"),
    MALAY("ms", "Malay"),
    MALAYALAM("ml", "Malayalam"),
    MALTESE("mt", "Maltese"),
    MAORI("mi", "Maori"),
    MARATHI("mr", "Marathi"),
    MONGOLIAN("mn", "Mongolian"),
    MYANMAR_BURMESE("my", "Myanmar (Burmese)"),
    NEPALI("ne", "Nepali"),
    NORWEGIAN("no", "Norwegian"),
    NYANJA_CHICHEWA("ny", "Nyanja (Chichewa)"),
    ODIA_ORIYA("or", "Odia (Oriya)"),
    PASHTO("ps", "Pashto"),
    PERSIAN("fa", "Persian"),
    POLISH("pl", "Polish"),
    PORTUGUESE("pt", "Portuguese"),
    PUNJABI("pa", "Punjabi"),
    ROMANIAN("ro", "Romanian"),
    RUSSIAN("ru", "Russian"),
    SAMOAN("sm", "Samoan"),
    SCOTS_GAELIC("gd", "Scots Gaelic"),
    SERBIAN("sr", "Serbian"),
    SESOTHO("st", "Sesotho"),
    SHONA("sn", "Shona"),
    SINDHI("sd", "Sindhi"),
    SINHALA_SINHALESE("si", "Sinhala (Sinhalese)"),
    SLOVAK("sk", "Slovak"),
    SLOVENIAN("sl", "Slovenian"),
    SOMALI("so", "Somali"),
    SPANISH("es", "Spanish"),
    SUNDANESE("su", "Sundanese"),
    SWAHILI("sw", "Swahili"),
    SWEDISH("sv", "Swedish"),
    TAGALOG_FILIPINO("tl", "Tagalog (Filipino)"),
    TAJIK("tg", "Tajik"),
    TAMIL("ta", "Tamil"),
    TATAR("tt", "Tatar"),
    TELUGU("te", "Telugu"),
    THAI("th", "Thai"),
    TURKISH("tr", "Turkish"),
    TURKMEN("tk", "Turkmen"),
    UKRAINIAN("uk", "Ukrainian"),
    URDU("ur", "Urdu"),
    UYGHUR("ug", "Uyghur"),
    UZBEK("uz", "Uzbek"),
    VIETNAMESE("vi", "Vietnamese"),
    WELSH("cy", "Welsh"),
    XHOSA("xh", "Xhosa"),
    YIDDISH("yi", "Yiddish"),
    YORUBA("yo", "Yoruba"),
    ZULU("zu", "Zulu");

    private final String code;
    private final String value;

    Language(String code, String value) {
        this.code = code;
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
