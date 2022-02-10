/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package com.client.glowclient.utils.classes.tasks;

import com.client.glowclient.utils.classes.tasks.Task;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class TaskManager {
    private static final List<Task> tasks = Lists.newCopyOnWriteArrayList();

    public static void register(Task task) {
        tasks.add(task);
    }

    public static void unregister(Task task) {
        tasks.remove(task);
    }

    @Nullable
    public static Task.TaskProcessing getTop(Task.Type type) {
        Optional<Task> t = tasks.stream().filter(Task::isActivated).filter(task -> task.hasTask(type)).sorted().findFirst();
        return t.isPresent() ? t.get().newTaskProcessing(type) : null;
    }
}

