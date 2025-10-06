package poersch.minecraft.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BGALStringHelper {
    public static List<String> splitTrimToList(String text, char delimiter) {
        ArrayList<String> tokens = new ArrayList<>();
        int first = -1;
        int last = -1;
        for (int n = 0; n < text.length(); n++) {
            if (text.charAt(n) == delimiter) {
                if (first > -1 && last >= first) {
                    tokens.add(text.substring(first, last + 1));
                }
                last = -1;
                first = -1;
            } else if (!Character.isWhitespace(text.charAt(n))) {
                if (first == -1) {
                    first = n;
                }
                last = n;
            }
        }
        if (first > -1 && last >= first) {
            tokens.add(text.substring(first, last + 1));
        }
        return tokens;
    }

    public static String[] splitTrimToArray(String text, char delimiter) {
        List<String> tokens = splitTrimToList(text, delimiter);
        return (String[]) tokens.toArray(new String[tokens.size()]);
    }

    public static List<Integer> splitTrimIntegerToList(String text, char delimiter) {
        ArrayList<Integer> tokens = new ArrayList<>();
        int first = -1;
        int last = -1;
        for (int n = 0; n < text.length(); n++) {
            if (text.charAt(n) == delimiter) {
                if (first > -1 && last >= first) {
                    try {
                        tokens.add(Integer.valueOf(Integer.parseInt(text.substring(first, last + 1))));
                    } catch (Exception e) {
                    }
                }
                last = -1;
                first = -1;
            } else if (!Character.isWhitespace(text.charAt(n))) {
                if (first == -1) {
                    first = n;
                }
                last = n;
            }
        }
        if (first > -1 && last >= first) {
            try {
                tokens.add(Integer.valueOf(Integer.parseInt(text.substring(first, last + 1))));
            } catch (Exception e2) {
            }
        }
        return tokens;
    }

    public static int[] splitTrimIntegerToArray(String text, char delimiter) {
        List<Integer> tokens = splitTrimIntegerToList(text, delimiter);
        return tokens.stream().mapToInt(Integer::intValue).toArray();
    }

    public static Map<String, String> readStreamIntoMap(InputStream inputStream, Map<String, String> valueMap) {
        return readStreamIntoMap(inputStream, valueMap, '=', ',', "#");
    }

    public static Map<String, String> readStreamIntoMap(InputStream inputStream, Map<String, String> valueMap, char separator, char delimiter, String commentTag) {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            List<String> tokens = splitTrimToList(scanner.nextLine(), separator);
            if (tokens.size() > 1 && !tokens.get(0).startsWith(commentTag)) {
                String str = tokens.get(0);
                String value = valueMap.get(tokens.get(0));
                valueMap.put(str, value == null ? tokens.get(1) : value + delimiter + tokens.get(1));
            }
        }
        scanner.close();
        return valueMap;
    }
}
