package org.interperter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

// Thought to be a bad style
import static org.interperter.TokenType.*;

/**
 * Scanner
 */
@RequiredArgsConstructor
public class Scanner {
  private final String source;
  private final List<Token> tokens = new ArrayList<>();
  private static final Map<String, TokenType> keywords;

  static {
    keywords = new HashMap<>();
    keywords.put("and",    AND);
    keywords.put("class",  CLASS);
    keywords.put("else",   ELSE);
    keywords.put("false",  FALSE);
    keywords.put("for",    FOR);
    keywords.put("fun",    FUN);
    keywords.put("if",     IF);
    keywords.put("nil",    NIL);
    keywords.put("or",     OR);
    keywords.put("print",  PRINT);
    keywords.put("return", RETURN);
    keywords.put("super",  SUPER);
    keywords.put("this",   THIS);
    keywords.put("true",   TRUE);
    keywords.put("var",    VAR);
    keywords.put("while",  WHILE);
  }
  // start and current indicate the indices of the current token we're working on
  private int start = 0;
  private int current = 0;
  private int line = 1;

  public List<Token> scanTokens() {
    while (!isAtEnd()) {
      // We are at the beginning of the next lexeme.
      start = current;
      scanToken();
    }

    tokens.add(new Token(EOF, "", null, line));
    return tokens;
  }

  private boolean isAtEnd() {
    return current >= source.length();
  }

  private void scanToken() {
    char c = advance();
    switch (c) {
      case '(': addToken(LEFT_PAREN); break;
      case ')': addToken(RIGHT_PAREN); break;
      case '{': addToken(LEFT_BRACE); break;
      case '}': addToken(RIGHT_BRACE); break;
      case ',': addToken(COMMA); break;
      case '.': addToken(DOT); break;
      case '-': addToken(MINUS); break;
      case '+': addToken(PLUS); break;
      case ';': addToken(SEMICOLON); break;
      case '*': addToken(STAR); break; 
      case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
      case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
      case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
      case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
      case '/':
                if (match('/')) {
                  // Keep advincing until the end of the line. A comment goes until the end of the line.
                  // There is no point of the parser tokenizing the comments
                  while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                  addToken(SLASH);
                }
                break;
      case ' ':
      case '\r':
      case '\t':
                // Ignore whitespace.
                break;

      case '\n':
                // Increment line numbers whenever you see a new line character 
                line++;
                break;          
      case '"': parseString(); break;
      default:
                if (isDigit(c)){
                  parseNumber();
                } else if (isAlpha(c)) {
                  parseIdentifier();
                } 
                else {
                  Lox.error(line, "Unexpected character.");
                }
                break;
    }
  }

  private void addToken(TokenType tokenType) {
    addToken(tokenType, null);
  }

  private void addToken(TokenType type, Object literal) {
    String text = source.substring(start, current);
    System.out.println("Token Type: " + type + ", Literal: " + literal + ", Text: " + text + ", Line: " + line);
    tokens.add(new Token(type, text, literal, line));
  }

  /**
   * Gets the next character from the source code and increments the current index
   * 
   * @return the character at the current position in the source string
   * @throws IndexOutOfBoundsException if the current position is out of bounds of the source string
   */
  private char advance() {
    return source.charAt(current++);
  }

  /**
   * Checks if {expected} char matches the current index at the source code. Scanner moves to the next position if it matches
   * 
   * @param expected The character to match against the current character in the source string.
   * @return true if the current character matches the expected character, false otherwise.
   */
  private boolean match(char expected) {
    if (isAtEnd()) return false;
    if (source.charAt(current) != expected) return false;

    current++;
    return true;
  }

  /**
   * If there is a next char to peek at. Return the next character
   * 
   */
  private char peekNext() {
    if (current + 1 >= source.length()) return '\0';
    return source.charAt(current + 1);
  } 


  /**
   * Checks and returns the next character without advancing the scanner
   *
   */
  private char peek() {
    if (isAtEnd()) return '\0';
    return source.charAt(current);
  }

  private void parseIdentifier() {
    while (isAlphaNumeric(peek())) advance();
    String text = source.substring(start, current);
    // Check if it's a keyword
    TokenType type = keywords.get(text);
    // If it's not a keyword assume it's an identifier
    if (type == null) type = IDENTIFIER;
    addToken(type);
  }

  /**
   * Parse the contents inside the string
   *
   */
  private void parseString() {
    while(peek() != '"' && !isAtEnd()) {
      if (peek() == '\n') line ++;
      advance();
    }

    if (isAtEnd()) {
      Lox.error(line, "Unterminated string.");
    }

    // Consume the '"' and advance
    advance();

    String value = source.substring(start + 1, current - 1);
    addToken(STRING, value);
  }


  /**
   * Parse integers and floating point numbers
   *
   */
  private void parseNumber() {
    while(isDigit(peek())) advance();
    if (peek() == '.' && isDigit(peekNext())){
      // Consume the "."
      advance();
      while(isDigit(peek())) advance();
    }
    addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
  }


  /**
   * Checks if a given character is a digit using ascii values. The characters get converted to integers
   * 
   */
  private boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }


  /**
   * Checks if a given character is an alphabet or has a '_' using ascii values. The characters get converted to integers
   * 
   */
  private boolean isAlpha(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_');
  }

  private boolean isAlphaNumeric(char c) {
    return isAlpha(c) || isDigit(c);
  }



}
