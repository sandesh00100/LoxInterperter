package org.interperter.grammar;

import org.interperter.visitors.Visitor;

public class AstPrinter implements Visitor<String>{

  public String print(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public String visitBinaryExpr(Binary expr) {
    return parenthesize(expr.getOperator().getLexeme(), expr.getLeft(), expr.getRight());
  }

  @Override
  public String visitGroupingExpr(Grouping expr) {
    return parenthesize("group", expr.getExpression());
  }

  @Override
  public String visitLiteralExpr(Literal expr) {
    if (expr.getValue() == null) return "nil";
    return expr.getValue().toString();
  }

  @Override
  public String visitUnaryExpr(Unary expr) {
    return parenthesize(expr.getOperator().getLexeme(), expr.getRight());
  }
  
  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();
    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder
        .append(" ")
        .append(expr.accept(this));
    }
    builder.append(")");
    return builder.toString();
  }
}
