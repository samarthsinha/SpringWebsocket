package com.smapp.filter;

import com.smapp.model.OutputMessage;

/**
 * Created by samarth on 17/02/17.
 */
public interface OutBoundMessageFilter {
    OutputMessage filter(OutputMessage message);
}
