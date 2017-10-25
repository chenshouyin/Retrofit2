package com.cypoem.retrofit.utils;

import android.util.Log;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zhpan on 2017/10/20.
 * Description:
 */

public class KeyTools {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String TAG = "KeyTools";

    /**
     * 获取MD5加密的之后的hex字符串
     * @param info
     * @return
     */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes());
            byte[] encryption = md5.digest();
            return DataConversionTools.bytesToHexString(encryption);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
