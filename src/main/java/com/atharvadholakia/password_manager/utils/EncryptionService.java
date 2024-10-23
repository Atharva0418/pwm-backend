package com.atharvadholakia.password_manager.utils;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
  private final int shift = 3;

  public String encrypt(String text) {
    return CaesarCipher.encrypt(text, shift);
  }

  public String decrypt(String text) {
    return CaesarCipher.decrypt(text, shift);
  }
}
