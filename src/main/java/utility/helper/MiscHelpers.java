package utility.helper;

import commons.constants.Constants;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

public class MiscHelpers {

    public static String currentDateTime(String format) {
        if(format==null|| format.isEmpty()){
            format = "MMddyyyyHHmmss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        Date date = new Date();
         return sdf.format(date);
    }

    public static String specificPastDateTime(String format,int daysMinus) {
        if(format==null|| format.isEmpty()){
            format = "MMddyyyyHHmmss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -daysMinus);
        return sdf.format(calendar.getTime());
    }

    public static String specificFutureDateTime(String format,int daysPlus) {
        if(format==null|| format.isEmpty()){
            format = "MMddyyyyHHmmss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, daysPlus);
        return sdf.format(calendar.getTime());
    }

    public static String getRandomString(int length, Boolean includeIntegers) {
        String allowedChars = includeIntegers ? "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890" : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return getRandomString(allowedChars, length);
    }

    public static String getRandomNumber(int length)
    {
        return getRandomString("1234567890", length);
    }

    public static String getRandomString(String allowedChars, int length) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int)(rnd.nextFloat() * (float)allowedChars.length());
            salt.append(allowedChars.charAt(index));
        }
        return salt.toString();
    }

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static IvParameterSpec generateIv() {
            byte[] iv = new byte[16];
            System.out.println("iv "+ new String(iv, StandardCharsets.UTF_8));
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static void generateCsvSingleLineItem(HashMap<String,String> fieldDataMap,String filePath) throws IOException {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        Iterator hmIterator = fieldDataMap.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            list1.add(mapElement.getKey().toString());
            list2.add(mapElement.getValue().toString());
        }
            List<List<String>> records = Arrays.asList(
                    list1,list2
            );

        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
        BufferedWriter writer = Files.newBufferedWriter(path);
        for (List<String> record : records) {
            writer.write(String.join(",", record));
            writer.newLine();
        }
        writer.close();
    }

    public static void generateCsvMultipleLineItem(List<HashMap<String,String>> fieldDataMap,String filePath) throws IOException {
        List<String> list1 = new ArrayList<>();

        List<String> list2 = new ArrayList<>();
        List<List<String>> mapList = new ArrayList<>();
        for (int i = 0 ;i<fieldDataMap.size();i++){
            if(i==0){
                Iterator hmIterator = fieldDataMap.get(i).entrySet().iterator();
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry) hmIterator.next();
                    list1.add(mapElement.getKey().toString());
                }
                mapList.add(list1);
            }
            else{
                Iterator hmIterator = fieldDataMap.get(i).entrySet().iterator();
                while (hmIterator.hasNext()) {
                    Map.Entry mapElement = (Map.Entry) hmIterator.next();
                    list2.add(mapElement.getValue().toString());
                }
                mapList.add(list2);
            }

        }
        List<List<String>> records = mapList;
        Path path = Paths.get(filePath);
        Files.deleteIfExists(path);
        BufferedWriter writer = Files.newBufferedWriter(path);
        for (List<String> record : records) {
            writer.write(String.join(",", record));
            writer.newLine();
        }
        writer.close();
    }
}
