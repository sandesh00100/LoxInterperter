package org.interperter.grammar;

import org.interperter.Token;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Binary extends Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;
}
