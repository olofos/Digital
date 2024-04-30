/*
 * Copyright (c) 2024 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.testing.parser;

import java.util.ArrayList;

/**
 * A subroutine
 */
public class Sub {

    private final ArrayList<String> args;
    private LineEmitter body;

    /**
     * Creates a new function
     *
     * @param args the arguments
     * @param body the body
     */
    public Sub(ArrayList<String> args, LineEmitter body) {
        this.args = args;
        this.body = body;
    }

    /**
     * @param argValues argument values
     * @return line emitter
     * @throws ParserException ParserException
     */
    public LineEmitter getCall(ArrayList<Expression> argValues) throws ParserException {
        return new LineEmitterSub(args, argValues, body);
    }

    /**
     * @return number of arguments
     */
    public int getArgCount() {
        return args.size();
    }
}
