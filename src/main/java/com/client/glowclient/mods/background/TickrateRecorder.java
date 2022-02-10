//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.EvictingQueue
 *  com.google.common.collect.Lists
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package com.client.glowclient.mods.background;

import com.client.glowclient.sponge.events.PacketEvent;
import com.client.glowclient.utils.base.mod.branches.ServiceMod;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TickrateRecorder
extends ServiceMod {
    public static final double MAX_TICKRATE = 20.0;
    public static final double MIN_TICKRATE = 0.0;
    public static final int MAXIMUM_SAMPLE_SIZE = 100;
    private static final TickrateRecorder INSTANCE = new TickrateRecorder();
    private static final TickRateData TICK_DATA = new TickRateData(100);
    private long timeLastTimeUpdate = -1L;
    private static double tr = 0.0;

    public static TickrateRecorder getInstance() {
        return INSTANCE;
    }

    public static TickRateData getTickData() {
        return TICK_DATA;
    }

    public TickrateRecorder() {
        super("TickrateRecorder", "Records the average tick rate");
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        this.timeLastTimeUpdate = -1L;
        TickrateRecorder.TICK_DATA.onWorldLoaded();
    }

    @SubscribeEvent
    public void onPacketPreceived(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            if (this.timeLastTimeUpdate != -1L) {
                TickrateRecorder.TICK_DATA.onTimePacketIncoming(System.currentTimeMillis() - this.timeLastTimeUpdate);
            }
            this.timeLastTimeUpdate = System.currentTimeMillis();
        }
    }

    public static double getTickrate() {
        return 0.0;
    }

    public static void setTickrate(double tickrate) {
        tr = tickrate;
    }

    public static class TickRateData {
        private final CalculationData EMPTY_DATA = new CalculationData();
        private final Queue<Double> rates;
        private final List<CalculationData> data = Lists.newArrayList();

        private TickRateData(int maxSampleSize) {
            this.rates = EvictingQueue.create((int)maxSampleSize);
            for (int i = 0; i < maxSampleSize; ++i) {
                this.data.add(new CalculationData());
            }
        }

        private void resetData() {
            for (CalculationData d : this.data) {
                d.reset();
            }
        }

        private void recalculate() {
            this.resetData();
            int size = 0;
            double total = 0.0;
            final List<Double> in = Lists.newArrayList(this.rates);
            Collections.reverse(in);
            for (final Double rate : in) {
                ++size;
                total += rate;
                final CalculationData d = this.data.get(size - 1);
                if (d != null) {
                    d.average = MathHelper.clamp(total / size, 0.0, 20.0);
                    if (size < 40) {
                        continue;
                    }
                    this.rates.clear();
                }
            }
        }

        public CalculationData getPoint(int point) {
            CalculationData d = this.data.get(Math.max(Math.min(this.getSampleSize() - 1, point - 1), 0));
            return d != null ? d : this.EMPTY_DATA;
        }

        public CalculationData getPoint() {
            return this.getPoint(this.getSampleSize() - 1);
        }

        public int getSampleSize() {
            return this.rates.size();
        }

        private void onTimePacketIncoming(long difference) {
            double timeElapsed = (double)difference / 1000.0;
            this.rates.offer(MathHelper.clamp((double)(20.0 / timeElapsed), (double)0.0, (double)20.0));
            this.recalculate();
        }

        private void onWorldLoaded() {
            this.rates.clear();
            this.resetData();
        }

        public class CalculationData {
            private double average = 0.0;

            public double getAverage() {
                return this.average;
            }

            public void reset() {
                this.average = 0.0;
            }
        }
    }
}

