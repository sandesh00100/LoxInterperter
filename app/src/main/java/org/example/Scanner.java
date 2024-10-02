package org.example;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Scanner
 */
@AllArgsConstructor
public class Scanner {
  private final String codeLine;
  public List<Token> scanTokens() {
    return new ArrayList<>();
  }
}
