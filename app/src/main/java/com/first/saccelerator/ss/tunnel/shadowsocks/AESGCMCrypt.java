package com.first.saccelerator.ss.tunnel.shadowsocks;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;

import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * GCMCrypt implementation
 * Created by XQ on 2017/7/3.
 */
public class AESGCMCrypt extends GCMCryptBase {

    public final static String CIPHER_AES_128_GCM = "aes-128-gcm";
    public final static String CIPHER_AES_192_GCM = "aes-192-gcm";
    public final static String CIPHER_AES_256_GCM = "aes-256-gcm";

    public static Map<String, String> getCiphers() {
        Map<String, String> ciphers = new HashMap<String, String>();
        ciphers.put(CIPHER_AES_128_GCM, AESGCMCrypt.class.getName());
        ciphers.put(CIPHER_AES_192_GCM, AESGCMCrypt.class.getName());
        ciphers.put(CIPHER_AES_256_GCM, AESGCMCrypt.class.getName());
        return ciphers;
    }


    public AESGCMCrypt(String name, String password) {
        super(name, password);
    }

    @Override
    public int getKeyLength() {
        if (_name.equals(CIPHER_AES_128_GCM)) {
            return 16;
        } else if (_name.equals(CIPHER_AES_192_GCM)) {
            return 24;
        } else if (_name.equals(CIPHER_AES_256_GCM)) {
            return 32;
        }
        return 0;
    }

    @Override
    protected GCMBlockCipher getCipher(boolean isEncrypted) throws InvalidAlgorithmParameterException {
        AESFastEngine engine = new AESFastEngine();
        GCMBlockCipher encCipher;
        if (_name.equals(CIPHER_AES_128_GCM)) {
            encCipher = new GCMBlockCipher(engine);
        } else if (_name.equals(CIPHER_AES_192_GCM)) {
            encCipher = new GCMBlockCipher(engine);
        } else if (_name.equals(CIPHER_AES_256_GCM)) {
            encCipher = new GCMBlockCipher(engine);
        } else {
            throw new InvalidAlgorithmParameterException(_name);
        }
        return encCipher;
    }

    @Override
    public int getIVLength() {
        return 16;
    }

    @Override
    protected SecretKey getKey() {
        return new SecretKeySpec(_ssKey.getEncoded(), "AES");
    }

    @Override
    protected void _encrypt(byte[] data, ByteArrayOutputStream stream) {
//        int noBytesProcessed;
//        byte[] buffer = new byte[data.length];
//        noBytesProcessed = encCipher.processBytes(data, 0, data.length, buffer, 0);
//        stream.write(buffer, 0, noBytesProcessed);
        byte[] encMsg = new byte[encCipher.getOutputSize(data.length)];
        int encLen = encCipher.processBytes(data, 0, data.length, encMsg, 0);
        try {
            encLen += encCipher.doFinal(encMsg, encLen);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        stream.write(encMsg, 0, encLen);
    }

    @Override
    protected void _decrypt(byte[] data, ByteArrayOutputStream stream) {
//        int noBytesProcessed;
//        byte[] buffer = new byte[data.length];
//        noBytesProcessed = decCipher.processBytes(data, 0, data.length, buffer, 0);
//        stream.write(buffer, 0, noBytesProcessed);

        byte[] decMsg = new byte[decCipher.getOutputSize(data.length)];
        int decLen = decCipher.processBytes(data, 0, data.length, decMsg, 0);
        try {
            decLen += decCipher.doFinal(decMsg, decLen);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        System.out.println("22222----->" + new String(decMsg));
        stream.write(decMsg, 0, decLen);
    }
}
