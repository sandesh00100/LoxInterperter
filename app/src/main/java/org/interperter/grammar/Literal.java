package org.interperter.grammar;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Literal extends Expr {
    public final Object value;
}
