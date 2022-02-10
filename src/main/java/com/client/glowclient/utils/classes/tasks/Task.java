//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Multimaps
 */
package com.client.glowclient.utils.classes.tasks;

import com.client.glowclient.utils.classes.priority.PriorityEnum;
import com.client.glowclient.utils.classes.tasks.IProcess;
import com.client.glowclient.utils.classes.tasks.TaskManager;
import com.client.glowclient.utils.client.Globals;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.util.Map;
import java.util.function.Consumer;

public class Task
implements Comparable<Task> {
    private final Multimap<Type, Consumer<IProcess.DataEntry>> pre = Multimaps.newListMultimap((Map)Maps.newHashMap(), Lists::newArrayList);
    private final Multimap<Type, Consumer<IProcess.DataEntry>> post = Multimaps.newListMultimap((Map)Maps.newHashMap(), Lists::newArrayList);
    private boolean active = false;
    private PriorityEnum priority = PriorityEnum.DEFAULT;

    public Task(Multimap<Type, Consumer<IProcess.DataEntry>> pre, Multimap<Type, Consumer<IProcess.DataEntry>> post) {
        this.pre.putAll(pre);
        this.post.putAll(post);
    }

    public void start() {
        this.active = true;
        TaskManager.register(this);
    }

    public void stop() {
        TaskManager.unregister(this);
        this.active = false;
    }

    public boolean isActivated() {
        return this.active;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public boolean hasTask(Type type) {
        return !this.pre.get(type).isEmpty() || !this.post.get(type).isEmpty();
    }

    public TaskProcessing newTaskProcessing(Type type) {
        return new TaskProcessing(this, type);
    }

    @Override
    public int compareTo(Task o) {
        return this.priority.compareTo(o.priority);
    }

    public static class TaskProcessing {
        private final Task task;
        private final Type type;
        private final IProcess.DataEntry data = new IProcess.DataEntry();

        public TaskProcessing(Task task, Type type) {
            this.task = task;
            this.type = type;
            type.process(this.data);
        }

        public void preProcessing() {
            this.task.pre.get(this.type).forEach(consumer -> consumer.accept(this.data));
        }

        public void postProcessing() {
            this.task.post.get(this.type).forEach(consumer -> consumer.accept(this.data));
        }
    }

    public static enum Type implements IProcess
    {
        LOOK{

            @Override
            public void process(IProcess.DataEntry data) {
                data.set("previousPitch", Float.valueOf(Globals.MC.player.rotationPitch));
                data.set("previousYaw", Float.valueOf(Globals.MC.player.rotationYaw));
            }
        };

    }
}

