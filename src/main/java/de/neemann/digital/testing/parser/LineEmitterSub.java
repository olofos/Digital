/*
 * Copyright (c) 2024 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.testing.parser;

import java.util.ArrayList;

/**
 * Subroutine.
 */
public class LineEmitterSub implements LineEmitter {
    private final ArrayList<String> argNames;
    private final ArrayList<Expression> argValues;
    private final LineEmitter inner;

    /**
     * Creates a new loop
     *
     * @param argNames  name of the arguments
     * @param argValues values of the arguments
     * @param inner     the body of the subroutine
     * @throws ParserException ParserException
     */
    public LineEmitterSub(ArrayList<String> argNames, ArrayList<Expression> argValues, LineEmitter inner) {
        this.argNames = argNames;
        this.argValues = argValues;
        this.inner = inner;
    }

    @Override
    public void emitLines(LineListener listener, Context context) throws ParserException {
        Context c = new Context(context);
        for (int i = 0; i < argNames.size(); i++) {
            c.setVar(argNames.get(i), argValues.get(i).value(c));
        }
        inner.emitLines(listener, c);
    }
}
