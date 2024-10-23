package com.atharvadholakia.password_manager.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CaesarCipherTests {

  @Test
  void testEncrypt() {
    String plaintext = "TestPassword@1";
    String encryptedText = "WhvwSdvvzrug@1";
    assertEquals(encryptedText, CaesarCipher.encrypt(plaintext, 3));
  }

  @Test
  void testDecrypt() {
    String encryptedText = "WhvwSdvvzrug@1";
    String plaintext = "TestPassword@1";
    assertEquals(plaintext, CaesarCipher.decrypt(encryptedText, 3));
  }
}
