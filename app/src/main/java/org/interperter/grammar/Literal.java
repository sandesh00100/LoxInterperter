package org.interperter.grammar;

import org.interperter.visitors.Visitor;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Literal extends Expr {
    public final Object value;

  @Override
  <R> R accept(Visitor<R> visitor) {
    return visitor.visitLiteralExpr(this);
  }
}
