package org.interperter.visitors;

import org.interperter.grammar.Binary;
import org.interperter.grammar.Grouping;
import org.interperter.grammar.Literal;
import org.interperter.grammar.Unary;

public interface Visitor<R> {
  public R visitBinaryExpr(Binary expr);

  public R visitGroupingExpr(Grouping expr);

  public R visitLiteralExpr(Literal expr);

  public R visitUnaryExpr(Unary expr);
}
