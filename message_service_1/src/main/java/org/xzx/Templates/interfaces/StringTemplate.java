package org.xzx.Templates.interfaces;

import org.xzx.bean.Jx3.Jx3Response.Jx3TiebaItemResponse;

public interface StringTemplate<T> {
    String getResultWithTemplate(T object);
}
