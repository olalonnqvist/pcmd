package com.polopoly.ps.pcmd.tool;

import com.polopoly.cm.ContentId;
import com.polopoly.cm.VersionedContentId;
import com.polopoly.cm.client.CMException;
import com.polopoly.cm.search.db.ComponentValue;
import com.polopoly.cm.search.db.ReferringTo;
import com.polopoly.cm.search.db.SearchExpression;
import com.polopoly.pcmd.tool.Tool;
import com.polopoly.ps.pcmd.FatalToolException;
import com.polopoly.util.client.PolopolyContext;

public class DescendantTool implements Tool<DescendantParameters> {

    @Override
    public void execute(PolopolyContext context, DescendantParameters parameters) throws FatalToolException {
        try {
            unsafeExecute(context, parameters);
        } catch (CMException e) {
            throw new FatalToolException(e);
        }
    }

    public void unsafeExecute(PolopolyContext context, DescendantParameters parameters) throws CMException {

        final int depth = parameters.getDepth();
        final int maxSize = parameters.getMaxSize();

        final ContentId theRootContentId = parameters.getContentIds().next();

        printDescendants(context, theRootContentId, depth, maxSize);
    }

    protected int printDescendants(PolopolyContext context, ContentId ancestorContentId, int remainingDepth, int remainingSize) throws CMException {
        int counter = 0;

        if (remainingSize < 1) {
            return counter;
        }

        print(ancestorContentId);
        counter++;

        if (remainingSize < 1) {
            return counter;
        }

        if (remainingDepth < 1) {
            return counter;
        }

        final SearchExpression searchExpression = new ReferringTo("polopoly.ContentMetaData", "securityParentId", ancestorContentId)
                .and(new ComponentValue("polopoly.ContentMetaData", "created", null, SearchExpression.NOT_ISNULL));

        VersionedContentId[] ids = context.getPolicyCMServer()
                .findContentIdsBySearchExpression(searchExpression, remainingSize, 0);

        for (ContentId descendentId : ids) {
            counter += printDescendants(context, descendentId.getContentId(), remainingDepth - 1, remainingSize - counter);
        }

        return counter;
    }

    protected void print(ContentId contentId) {
        System.out.println(contentId.getContentId().getContentIdString());
    }

    @Override
    public DescendantParameters createParameters() {
        return new DescendantParameters();
    }

    @Override
    public String getHelp() {
        // TODO Improve help
        return null;
    }
}
