package com.atharvadholakia.password_manager.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CaesarCipherTests {

  @Test
  void testEncrypt() {
    String plaintext = "HELLO";
    String encryptedText = "KHOOR";
    assertEquals(encryptedText, CaesarCipher.encrypt(plaintext, 3));
  }

  @Test
  void testDecrypt() {
    String encryptedText = "KHOOR";
    String plaintext = "HELLO";
    assertEquals(plaintext, CaesarCipher.decrypt(encryptedText, 3));
  }
}
