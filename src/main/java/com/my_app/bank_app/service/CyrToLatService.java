package com.my_app.bank_app.service;

public class CyrToLatService {

    private static final String[] CYRILLIC = {
            "А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й",
            "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф",
            "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я",
            "а", "б", "в", "г", "д", "е", "ё", "ж", "з", "и", "й",
            "к", "л", "м", "н", "о", "п", "р", "с", "т", "у", "ф",
            "х", "ц", "ч", "ш", "щ", "ъ", "ы", "ь", "э", "ю", "я"
    };

    private static final String[] LATIN = {
            "A", "B", "V", "G", "D", "E", "E", "ZH", "Z", "I", "Y",
            "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F",
            "KH", "TS", "CH", "SH", "SHCH", "", "Y", "", "E", "YU", "YA",
            "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y",
            "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f",
            "kh", "ts", "ch", "sh", "shch", "", "y", "", "e", "yu", "ya"
    };

    public static String toLatin(String cyrillic) {
        if (cyrillic == null) return null;
        StringBuilder latin = new StringBuilder();
        for (char c : cyrillic.toCharArray()) {
            String charStr = String.valueOf(c);
            boolean replaced = false;
            for (int i = 0; i < CYRILLIC.length; i++) {
                if (charStr.equals(CYRILLIC[i])) {
                    latin.append(LATIN[i]);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                latin.append(c);
            }
        }
        return latin.toString().toUpperCase();
    }
}
