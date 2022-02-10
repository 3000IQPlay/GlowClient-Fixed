package com.client.glowclient.utils.mc.math;

import java.util.*;

public class AngleN
{
    public static final AngleN ZERO;
    private final boolean radians;
    private final double pitch;
    private final double yaw;
    private final double roll;
    private AngleN twin;
    private AngleN normal;

    public static AngleN radians(final double pitch, final double yaw, final double roll) {
        return new AngleN(true, pitch, yaw, roll);
    }

    public static AngleN radians(final float pitch, final float yaw, final float roll) {
        return radians(pitch, yaw, (double)roll);
    }

    public static AngleN radians(final int pitch, final int yaw, final int roll) {
        return radians(pitch, yaw, (double)roll);
    }

    public static AngleN radians(final double pitch, final double yaw) {
        return radians(pitch, yaw, 0.0);
    }

    public static AngleN radians(final float pitch, final float yaw) {
        return radians(pitch, yaw, 0.0f);
    }

    public static AngleN radians(final int pitch, final int yaw) {
        return radians(pitch, yaw, 0);
    }

    public static AngleN degrees(final double pitch, final double yaw, final double roll) {
        return new AngleN(false, pitch, yaw, roll);
    }

    public static AngleN degrees(final float pitch, final float yaw, final float roll) {
        return degrees(pitch, yaw, (double)roll);
    }

    public static AngleN degrees(final int pitch, final int yaw, final int roll) {
        return degrees(pitch, yaw, (double)roll);
    }

    public static AngleN degrees(final double pitch, final double yaw) {
        return degrees(pitch, yaw, 0.0);
    }

    public static AngleN degrees(final float pitch, final float yaw) {
        return degrees(pitch, yaw, 0.0f);
    }

    public static AngleN degrees(final int pitch, final int yaw) {
        return degrees(pitch, yaw, 0);
    }

    public static AngleN copy(final AngleN ang) {
        return new AngleN(ang);
    }

    protected AngleN(final boolean radians, final double pitch, final double yaw, final double roll) {
        this.twin = null;
        this.normal = null;
        this.radians = radians;
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    protected AngleN(final AngleN ang) {
        this(ang.isRadians(), ang.pitch(), ang.yaw(), ang.roll());
    }

    public double pitch() {
        return this.pitch;
    }

    public double yaw() {
        return this.yaw;
    }

    public double roll() {
        return this.roll;
    }

    public boolean isRadians() {
        return this.radians;
    }

    public boolean isDegrees() {
        return !this.radians;
    }

    public AngleN toRadians() {
        if (this.isRadians()) {
            return this;
        }
        if (this.twin == null) {
            this.twin = radians(Math.toRadians(this.pitch()), Math.toRadians(this.yaw()), Math.toRadians(this.roll()));
            this.twin.twin = this;
        }
        return this.twin;
    }

    public AngleN toDegrees() {
        if (this.isDegrees()) {
            return this;
        }
        if (this.twin == null) {
            this.twin = degrees(Math.toDegrees(this.pitch()), Math.toDegrees(this.yaw()), Math.toDegrees(this.roll()));
            this.twin.twin = this;
        }
        return this.twin;
    }

    public AngleN add(final double p, final double y, final double r) {
        return this.create(this.pitch() + p, this.yaw() + y, this.roll() + r);
    }

    public AngleN add(final double p, final double y) {
        return this.add(p, y, 0.0);
    }

    public AngleN add(AngleN ang) {
        if (this.isRadians() != ang.isRadians()) {
            ang = (this.isRadians() ? ang.toRadians() : ang.toDegrees());
        }
        return this.add(ang.pitch(), ang.yaw(), ang.roll());
    }

    public AngleN sub(final double p, final double y, final double r) {
        return this.add(-p, -y, -r);
    }

    public AngleN sub(final double p, final double y) {
        return this.sub(p, y, 0.0);
    }

    public AngleN sub(AngleN ang) {
        if (this.isRadians() != ang.isRadians()) {
            ang = (this.isRadians() ? ang.toRadians() : ang.toDegrees());
        }
        return this.sub(ang.pitch(), ang.yaw(), ang.roll());
    }

    public AngleN scale(final double factor) {
        return this.create(this.pitch() * factor, this.yaw() * factor, this.roll() * factor);
    }

    public AngleN normalize() {
        if (this.normal == null) {
            double np;
            double ny;
            double nr;
            if (this.isRadians()) {
                np = AngleHelper.normalizeInRadians(this.pitch());
                ny = AngleHelper.normalizeInRadians(this.yaw());
                nr = AngleHelper.normalizeInRadians(this.roll());
            }
            else {
                np = AngleHelper.normalizeInDegrees(this.pitch());
                ny = AngleHelper.normalizeInDegrees(this.yaw());
                nr = AngleHelper.normalizeInDegrees(this.roll());
            }
            if (AngleHelper.isAngleEqual(this.pitch(), np) && AngleHelper.isAngleEqual(this.yaw(), ny) && AngleHelper.isAngleEqual(this.roll(), nr)) {
                this.normal = this;
            }
            else {
                final AngleN norm = this.create(np, ny, nr);
                norm.normal = norm;
                this.normal = norm;
            }
        }
        return this.normal;
    }

    public double[] forward() {
        final double kps = Math.sin(this.toRadians().pitch());
        final double kpc = Math.cos(this.toRadians().pitch());
        final double kys = Math.sin(this.toRadians().yaw());
        final double kyc = Math.cos(this.toRadians().yaw());
        return new double[] { kpc * kyc, kps, kpc * kys };
    }

    public double[] toArray() {
        return new double[] { this.pitch(), this.yaw(), this.roll() };
    }

    protected AngleN create(final double p, final double y, final double r) {
        return new AngleN(this.isRadians(), p, y, r);
    }

    protected AngleN create(final double p, final double y) {
        return this.create(p, y, 0.0);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AngleN) {
            final AngleN self = this.normalize();
            AngleN ang = ((AngleN)obj).normalize();
            if (self.isRadians() != ang.isRadians()) {
                ang = (self.isRadians() ? ang.toRadians() : ang.toDegrees());
            }
            return AngleHelper.isAngleEqual(self.pitch(), ang.pitch()) && AngleHelper.isAngleEqual(self.yaw(), ang.yaw()) && AngleHelper.isAngleEqual(self.roll(), ang.roll());
        }
        return false;
    }

    @Override
    public int hashCode() {
        final AngleN a = this.normalize().toRadians();
        return Arrays.hashCode(new double[] { AngleHelper.roundAngle(a.pitch()), AngleHelper.roundAngle(a.yaw()), AngleHelper.roundAngle(a.roll()) });
    }

    @Override
    public String toString() {
        return String.format("(%.15f, %.15f, %.15f)[%s]", this.pitch(), this.yaw(), this.roll(), this.isRadians() ? "rad" : "deg");
    }

    static {
        ZERO = new AngleN(true, 0.0, 0.0, 0.0);
    }
}
