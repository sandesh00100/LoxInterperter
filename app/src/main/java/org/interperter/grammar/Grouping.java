package org.interperter.grammar;

import org.interperter.visitors.Visitor;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Grouping extends Expr {
  public final Expr expression;

  @Override
  <R> R accept(Visitor<R> visitor) {
    return visitor.visitGroupingExpr(this);
  }
}
