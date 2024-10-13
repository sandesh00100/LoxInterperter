package org.interperter.grammar;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Grouping extends Expr {
 public final Expr expression;
}
