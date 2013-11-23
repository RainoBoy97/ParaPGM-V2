package me.parapenguin.parapgm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lombok.Getter;

import org.bukkit.ChatColor;

public enum Chars {

    INVALID(ChatColor.RED + "" + ChatColor.BOLD + "Error"),
    RAQUO("\u00BB"),
    LAQUO("\u00AB"),
    BLACK_STAR("\u2605"),
    WHITE_STAR("\u2606"),
    TRI_LINE("\u2630"),
    LEFT_ARROW_BLACK("\u25C4"),
    RIGHT_ARROW_BLACK("\u25BA"),
    CIRCLED_ASTERISK("\u229B"),
    LEFT_MUCH_GREATER("\u22D8"),
    RIGHT_MUCH_GREATER("\u22D9"),
    SNOWFLAKE("\u2744"),
    COPYRIGHT("\u00A9"),
    REGISTERED_TRADEMARK("\u00AE"),
    ROMAN_ONE("\u2160"),
    ROMAN_TWO("\u2161"),
    ROMAN_THREE("\u2162"),
    ROMAN_FOUR("\u2163"),
    ROMAN_FIVE("\u2164"),
    ROMAN_SIX("\u2165"),
    ROMAN_SEVEN("\u2166"),
    ROMAN_EIGHT("\u2167"),
    ROMAN_NINE("\u2168"),
    ROMAN_TEN("\u2169"),
    ROMAN_ELEVEN("\u216A"),
    ROMAN_TWELVE("\u216B");

    @Getter String utf;

    Chars(String utf) {
        this.utf = utf;
    }

    public static Chars getChar(String title) {
        title = title.replaceAll(" ", "_");
        for (Chars chars : values())
            if (title.equalsIgnoreCase(chars.name()) || title.toLowerCase().contains(chars.name().toLowerCase()))
                return chars;

        return INVALID;
    }

    @Override
    public String toString() {
        return utf;
    }

    /**
     * @param allow Set of Unicode Characters, non seperated. For example "\\uu0BB\\00AB", Raquo and Laquo.
     *              In this example double backslashes were used to prevent Incorrect Unicode Errors from appearing in Eclipse.
     *              NOTE: Do not use Double backslashes (\) in your actual use.
     */
    public static void allowCharacters(String allow) {
        try {
            Field allowed = NMSUtils.getClass("SharedConstants").getDeclaredField("allowedCharacters");
            allowed.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(allowed, allowed.getModifiers() & ~Modifier.FINAL);
            String oldallowedchars = (String) allowed.get(null);
            StringBuilder sb = new StringBuilder();
            sb.append(oldallowedchars);
            allowed.set(null, sb.toString());
        } catch (Exception ex) {
            //
        }
    }

}
