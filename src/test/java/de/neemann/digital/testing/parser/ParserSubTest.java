/*
 * Copyright (c) 2024 Helmut Neemann
 * Use of this source code is governed by the GPL v3 license
 * that can be found in the LICENSE file.
 */
package de.neemann.digital.testing.parser;

import junit.framework.TestCase;

import java.io.IOException;

/**
 */
public class ParserSubTest extends TestCase {

    public void testDefSub() throws IOException, ParserException {
        Parser parser = new Parser("A B\ndef f(a,b)\n(a) (b)\n(b) (a)\nend def\ncall f(1,2)\ncall f(3,4)").parse();
        LineCollector td = new LineCollector(parser);

        assertEquals(2, td.getNames().size());
        assertEquals(4, td.getLines().size());

        assertEquals(1, td.getLines().get(0).getValue(0).getValue());
        assertEquals(2, td.getLines().get(0).getValue(1).getValue());
        assertEquals(2, td.getLines().get(1).getValue(0).getValue());
        assertEquals(1, td.getLines().get(1).getValue(1).getValue());
        assertEquals(3, td.getLines().get(2).getValue(0).getValue());
        assertEquals(4, td.getLines().get(2).getValue(1).getValue());
        assertEquals(4, td.getLines().get(3).getValue(0).getValue());
        assertEquals(3, td.getLines().get(3).getValue(1).getValue());
    }

    public void testDefSubWithNoArgs() throws IOException, ParserException {
        Parser parser = new Parser("A B\ndef f()\n0 1\n1 0\nend def\ncall f()").parse();
        LineCollector td = new LineCollector(parser);

        assertEquals(2, td.getNames().size());
        assertEquals(2, td.getLines().size());

        assertEquals(0, td.getLines().get(0).getValue(0).getValue());
        assertEquals(1, td.getLines().get(0).getValue(1).getValue());
        assertEquals(1, td.getLines().get(1).getValue(0).getValue());
        assertEquals(0, td.getLines().get(1).getValue(1).getValue());
    }

    public void testIncompleteEndDef() throws IOException, ParserException {
        try {
            new Parser("A B\ndef f(a)1 1\nend").parse();
            fail();
        } catch (ParserException e) {
        }
    }

    public void testCallOfUnknownSub() throws IOException, ParserException {
        try {
            new Parser("A B\ncall f(1)").parse();
            fail();
        } catch (ParserException e) {
        }
    }

    public void testDefShouldComeBeforeCall() throws IOException, ParserException {
        try {
            new Parser("A B\ncall f(1,1)\ndef f(a,b)\n1 1\nend def").parse();
            fail();
        } catch (ParserException e) {
        }
    }

    public void testNoRecursiveSub() throws IOException, ParserException {
        try {
            new Parser("A B\ndef f(a,b)\ncall f(1,1)\nend def").parse();
            fail();
        } catch (ParserException e) {
        }
    }

    public void testWrongNumberOfArguments() throws IOException, ParserException {
        try {
            new Parser("A B\ndef f(a,b)1 1\nend def\ncall f(1,2,3)").parse();
            fail();
        } catch (ParserException e) {
        }
    }

    public void testSubCallsSub() throws IOException, ParserException {
        Parser parser = new Parser("A B\ndef f(a,b)\n(a) (b)\nend def\ndef g(a,b)\ncall f(a,b)\nend def\ncall g(1,2)")
                .parse();
        LineCollector td = new LineCollector(parser);

        assertEquals(2, td.getNames().size());
        assertEquals(1, td.getLines().size());

        assertEquals(1, td.getLines().get(0).getValue(0).getValue());
        assertEquals(2, td.getLines().get(0).getValue(1).getValue());
    }

    public void testDefInLoop() throws IOException, ParserException {
        try {
            new Parser("A B\nloop(i,2)\ndef f(a,b)1 1\nend def\nend loop\ncall f(1,2,3)").parse();
            fail();
        } catch (ParserException e) {
        }
    }

}
