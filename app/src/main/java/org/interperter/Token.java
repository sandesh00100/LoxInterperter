package org.interperter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter()
public class Token {
  private final TokenType type;
  private final String lexeme;
  private final Object literal;
  private final int line; 

  @Override
  public String toString() {
    return type + " " + lexeme + " " + literal;
  } 
}
