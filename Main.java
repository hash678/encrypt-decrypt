import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class Main {
    static ArrayList<Integer> stringChuncksSizes=new ArrayList<>();

    public static void main(String[] args) throws Exception {
encrypt();
decrypt();

/*testEncrypt();
testDecrypt();*/


    }

/*
    private static void testDecrypt() throws Exception {
        byte[] ivBytes = {0x00, 0x53, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x00};
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        File encryptedFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample_encrypted.html");
        File decryptedFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample_decrypted.html");
        String s = FileUtils.readFileToString(encryptedFile);
        OutputStream outputStream = new FileOutputStream(decryptedFile);
        int stringLength = s.length();
        int remainder = stringLength % 16;
        for (int i = 0; i < stringLength; i += 16) {
            if (stringLength - i >= 16 || stringLength == 16) {
                String temp = s.substring(i, i + 16);
                byte[] tempCipherText=AESManager.decrypt("AHYT42_PPO18tas9".getBytes(),ivBytes,temp.getBytes());
                outputStream.write(tempCipherText);
            }
        }
        String temp = s.substring(stringLength - remainder, stringLength);
        byte[] tempCipherText=AESManager.decrypt("AHYT42_PPO18tas9".getBytes(),ivBytes,temp.getBytes());
        outputStream.write(tempCipherText);

    }

    private static void testEncrypt() throws Exception {
        byte[] ivBytes = {0x00, 0x53, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x00};
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec secretKeySpec = new SecretKeySpec("AHYT42_PPO18tas9".getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        File originalFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample.html");
        File encryptedFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample_encrypted.html");
        FileOutputStream outputStream = new FileOutputStream(encryptedFile);
        byte[] s = FileUtils.readFileToByteArray(originalFile);
        int byteArrayLength=s.length;
        for(int i=0;i<byteArrayLength;i++){
            if (byteArrayLength - i >= 16 || byteArrayLength == 16)
                cipher.update(s,i,i+16);
        }
    }*/
    static void encrypt() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        File originalFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample.html");
        File encryptedFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample_encrypted.html");
        FileOutputStream outputStream = new FileOutputStream(encryptedFile);
        String s = FileUtils.readFileToString(originalFile);
        // System.out.println(originalData);
        byte[] ivBytes = {0x00, 0x53, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x00};
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec secretKeySpec = new SecretKeySpec("AHYT42_PPO18tas9".getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        int stringLength = s.length();
        //int quotient = stringLength / 16;
        int remainder = stringLength % 16;
        //System.out.println("LENGTH IS: "+stringLength);
        //System.out.println("Qotient IS: "+quotient);
        int count = 0;
        for (int i = 0; i < stringLength; i += 16) {
            count++;
            if (stringLength - i >= 16 || stringLength == 16) {
                String temp = s.substring(i, i + 16);
                byte[] tempCipherText = cipher.doFinal(temp.getBytes("UTF-8"));
                stringChuncksSizes.add(new String(tempCipherText).length());
                outputStream.write(tempCipherText);
            } else {
                String temp = s.substring(stringLength - remainder, stringLength);
                byte[] tempCipherText = cipher.doFinal(temp.getBytes("UTF-8"));
                outputStream.write(temp.getBytes("UTF-8"));
            }
        }
        System.out.println("TOTAL COUNT IS: " + count);
        outputStream.close();
    }


    static void decrypt() throws InvalidAlgorithmParameterException, InvalidKeyException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        File encryptedFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample_encrypted.html");
        File decryptedFile = new File("C:\\Users\\talha\\Desktop\\jaihg\\Pdf sample_decrypted.html");
        String s = FileUtils.readFileToString(encryptedFile);
        OutputStream outputStream = new FileOutputStream(decryptedFile);
        byte[] ivBytes = {0x00, 0x53, 0x00, 0x00, 0x00, 0x00, 0x00, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x00};
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec secretKeySpec = new SecretKeySpec("AHYT42_PPO18tas9".getBytes(), "AES");
        int stringLength = s.length();
        int remainder = stringLength % 16;
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        /*for (int i = 0; i < stringLength; i += 16) {
            if (stringLength - i >= 16 || stringLength == 16) {
                String temp = s.substring(i, i + 16);
                byte[] tempDecrypted = cipher.update(temp.getBytes("UTF-8"));
                System.out.println(temp);
                System.out.println(new String(tempDecrypted));
                outputStream.write(tempDecrypted);
            } else {
                cipher.doFinal();
                String temp=s.substring(stringLength-remainder,stringLength);
                outputStream.write(temp.getBytes("UTF-8"));
            }
        }*/
        int count=0;
        for(int strLen : stringChuncksSizes){
            String temp=s.substring(count,strLen);
            count+=temp.length();
            byte[] decryptedText=cipher.doFinal(temp.getBytes());
            outputStream.write(decryptedText);
        }
        outputStream.close();
    }


}
