package code.acadConecta.model.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashItem{
    public static String defineHash(String entry) {
        String stringInHash = null;
        try {
            //pegando instância do algoritmo de criptografia
            MessageDigest algorithmSha256 = MessageDigest.getInstance("SHA-256");

            //pegando bytes criptografados com SHA-256
            byte messageDigestHash[] = algorithmSha256.digest(entry.getBytes("UTF-8"));

            StringBuilder stringBuilder = new StringBuilder();

            //convertendo em string
            for (byte itemByte : messageDigestHash) {
                stringBuilder.append(String.format("%02X", 0xFF & itemByte));
            }

            stringInHash = stringBuilder.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException error) {
            System.out.println("Error to convert param in hash: " + error.getMessage());
        }
        return stringInHash;
    }
}