package org.interperter.grammar;

import org.interperter.Token;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Unary extends Expr {
    public final Token operator;
    public final Expr right;
}
