package org.interperter.grammar;

import org.interperter.visitors.Visitor;

public abstract class Expr {
  abstract <R> R accept(Visitor<R> visitor);
}
