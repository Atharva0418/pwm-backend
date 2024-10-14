package com.atharvadholakia.password_manager.utils;

public class CaesarCipher {

  public static String encrypt(String plaintext, int shift) {
    StringBuilder encryptedText = new StringBuilder();

    for (char c : plaintext.toCharArray()) {

      if (Character.isUpperCase(c)) {
        char encryptedChar = (char) ((c + shift - 'A') % 26 + 'A');
        encryptedText.append(encryptedChar);
      } else if (Character.isLowerCase(c)) {
        char encryptedChar = (char) ((c + shift - 'a') % 26 + 'a');
        encryptedText.append(encryptedChar);
      } else {
        encryptedText.append(c);
      }
    }

    return encryptedText.toString();
  }

  public static String decrypt(String plaintext, int shift) {

    return encrypt(plaintext, 26 - (shift % 26));
  }
}
