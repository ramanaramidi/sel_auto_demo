package testData;

import common.LoadPropertiesFiles;
import commons.objects.Trackers;
import commons.objects.Users;
import utility.helper.MiscHelpers;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

public final class UserData {
  //  public UserData userDetails = new UserData();
    private static String token;
    static Properties userProperties;
    static Properties alphaUserProperties;
    //TODO Need to find a better solution for this also test the use-case more
//    static {
//        try {
//            alphaUserProperties = LoadPropertiesFiles.loadProperties("C:\\driver\\userData.properties");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    static {
        try {
            userProperties = LoadPropertiesFiles.loadProperties(System.getProperty("user.dir") + "\\src\\test\\resources\\testData\\userData.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Users setAlphaUserBasicDetails(String email,String password,String ntCode, Users userDetails){
        System.out.println("setting the right values");
        userDetails.setUserName(email);
        userDetails.setPassword(password);
        userDetails.setNtCode(ntCode);
        userDetails.setUser(userProperties.getProperty("Alpha.user"));
        userDetails.setIsContractor(userProperties.getProperty("Alpha.isContractor"));
        userDetails.setIsServiceAccount(userProperties.getProperty("Alpha.isServiceAccount"));
        return userDetails;
    }
    public static String getInstance(){
        return token;
    }
    public static void getToken(String privateKey)throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {
        System.out.println("Getting token");
        String tokenCipher = userProperties.getProperty("Super.token");
        String publicKey = userProperties.getProperty("Super.publicKey");
        String iv = null;
        if(privateKey ==null){
            try {
                alphaUserProperties = LoadPropertiesFiles.loadProperties("C:\\driver\\userData.properties");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Getting alfa");
            iv = alphaUserProperties.getProperty("Super.privateKey");
        }
        else
            iv = privateKey;

        byte[] bytes = iv.getBytes();
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(bytes);
        String algorithm = "AES/CBC/PKCS5Padding";
        SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(publicKey), 0, Base64.getDecoder().decode(publicKey).length, "AES");
        String plainText = MiscHelpers.decrypt(algorithm, tokenCipher, key, ivParameterSpec);
        token =  plainText;
    }

    public static Users getAlphaUserDetails(Users userDetails) {
        try {
            alphaUserProperties = LoadPropertiesFiles.loadProperties("C:\\driver\\userData.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userDetails.setUserName(alphaUserProperties.getProperty("Alpha.user_name"));
        userDetails.setPassword(alphaUserProperties.getProperty("Alpha.pass_code"));
        userDetails.setNtCode(alphaUserProperties.getProperty("Alpha.nt_code"));
        userDetails.setUser(userProperties.getProperty("Alpha.user"));
        userDetails.setIsContractor(userProperties.getProperty("Alpha.isContractor"));
        userDetails.setIsServiceAccount(userProperties.getProperty("Alpha.isServiceAccount"));
        return userDetails;
    }

    public static Users getSuperUserDetails(Users userDetails) {
        System.out.println("getting super");
        userDetails.setUserName(userProperties.getProperty("Super.user_name"));
        //userDetails.setPassword(userProperties.getProperty("Super.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("Super.nt_code"));
        userDetails.setUser(userProperties.getProperty("Super.user"));
        return userDetails;
    }

    public static Users getRfEngUserDetails(Users userDetails) {
        System.out.println("getting RF Engineer");
        userDetails.setUserName(userProperties.getProperty("RfEng.user_name"));
        //userDetails.setPassword(userProperties.getProperty("Super.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("RfEng.nt_code"));
        userDetails.setUser(userProperties.getProperty("RfEng.user"));
        return userDetails;
    }

    public static Users getNonSuperUserDetails(Users userDetails) {
        System.out.println("getting nonsuper");
        userDetails.setUserName(userProperties.getProperty("NonSuper.user_name"));
        //userDetails.setPassword(userProperties.getProperty("NonSuper.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("NonSuper.nt_code"));
        userDetails.setUser(userProperties.getProperty("NonSuper.user"));
        return userDetails;
    }
    public static Users getSdm_CrownCastleUserDetail(Users userDetails) {
        userDetails.setUserName(userProperties.getProperty("Dan_SDM.user_name"));
        //userDetails.setPassword(userProperties.getProperty("Dan_SDM.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("Dan_SDM.nt_code"));
        userDetails.setUser(userProperties.getProperty("Dan_SDM.user"));
        return userDetails;
    }

    public static Users getAtcUserDetail(Users userDetails) {
        userDetails.setUserName(userProperties.getProperty("ATC_AGonzal.user_name"));
        //userDetails.setPassword(userProperties.getProperty("ATC_AGonzal.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("ATC_AGonzal.nt_code"));
        userDetails.setUser(userProperties.getProperty("ATC_AGonzal.user"));
        return userDetails;
    }

    public static Users getSBAUserDetail(Users userDetails) {
        userDetails.setUserName(userProperties.getProperty("SBA_AMaldonado.user_name"));
        //userDetails.setPassword(userProperties.getProperty("SBA_AMaldonado.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("SBA_AMaldonado.nt_code"));
        userDetails.setUser(userProperties.getProperty("SBA_AMaldonado.user"));
        return userDetails;
    }

    public static Users getSite_DevUserDetails(Users userDetails) {
        userDetails.setUserName(userProperties.getProperty("Site_Dev.user_name"));
        //userDetails.setPassword(userProperties.getProperty("Site_Dev.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("Site_Dev.nt_code"));
        userDetails.setUser(userProperties.getProperty("Site_Dev.user"));
        return userDetails;
    }
    public static Users getAtcUserDetails(Users userDetails) {
        userDetails.setUserName(userProperties.getProperty("VendorDoc.user_name"));
        //userDetails.setPassword(userProperties.getProperty("NonSuper.pass_code"));
        userDetails.setNtCode(userProperties.getProperty("VendorDoc.nt_code"));
        userDetails.setUser(userProperties.getProperty("VendorDoc.user"));
        return userDetails;
    }

}
