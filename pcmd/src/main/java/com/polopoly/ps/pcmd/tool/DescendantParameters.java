package com.polopoly.ps.pcmd.tool;

import com.polopoly.ps.pcmd.argument.ArgumentException;
import com.polopoly.ps.pcmd.argument.Arguments;
import com.polopoly.ps.pcmd.argument.ContentIdListParameters;
import com.polopoly.ps.pcmd.parser.IntegerParser;
import com.polopoly.util.client.PolopolyContext;

public class DescendantParameters extends ContentIdListParameters {

    private int depth;
    private int maxSize;

    @Override
    public void parseParameters(Arguments args, PolopolyContext context) throws ArgumentException {
        super.parseParameters(args, context);
        depth = args.getOption("depth", new IntegerParser(), "2");
        maxSize = args.getOption("maxSize", new IntegerParser(), "10000");
    }

    public int getDepth() {
        return depth;
    }

    public int getMaxSize() {
        return maxSize;
    }
}
