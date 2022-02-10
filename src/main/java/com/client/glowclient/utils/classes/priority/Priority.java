/*
 * Decompiled with CFR 0.150.
 */
package com.client.glowclient.utils.classes.priority;

import com.client.glowclient.utils.classes.priority.PriorityEnum;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Priority {
    public PriorityEnum value() default PriorityEnum.DEFAULT;
}

