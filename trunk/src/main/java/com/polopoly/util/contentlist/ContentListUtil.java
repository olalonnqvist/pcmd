package com.polopoly.util.contentlist;

import java.util.List;

import com.polopoly.cm.policy.Policy;
import com.polopoly.pcmd.tool.ContentReferenceUtil;
import com.polopoly.util.content.ContentUtil;
import com.polopoly.util.contentid.ContentIdUtil;
import com.polopoly.util.contentlist.ContentListUtilImpl.ContentListContentIds;
import com.polopoly.util.exception.PolicyGetException;
import com.polopoly.util.policy.PolicyUtil;

public interface ContentListUtil extends RuntimeExceptionContentList, Iterable<Policy> {
    Iterable<ContentUtil> contents();
    <T> Iterable<T> policies(final Class<T> policyClass);
    Iterable<PolicyUtil> policyUtils();
    <T> List<T> policyList(final Class<T> policyClass);
    ContentListContentIds contentIds();
    void add(int index, Policy policy);
    void add(Policy policy);
    void remove(Policy policy);
    ContentIdUtil get(int i);
    <T> T get(int i, Class<T> klass) throws PolicyGetException;
    boolean contains(Policy policy);
    Iterable<ContentReferenceUtil> references();
    void clear();
}
