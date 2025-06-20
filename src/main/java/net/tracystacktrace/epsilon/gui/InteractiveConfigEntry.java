package net.tracystacktrace.epsilon.gui;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public abstract class InteractiveConfigEntry<T> {

    protected final int id;
    protected final Field field;
    protected final Object instance;

    public InteractiveConfigEntry(
            int id,
            @NotNull Field field,
            @NotNull Object instance
    ) {
        this.id = id;
        this.field = field;
        this.instance = instance;
    }

    public abstract void initGui(GuiEpsilonConfig config, int x, int y, int w);

    public abstract void render(int mouseX, int mouseY);

    public abstract void finish();

    public int getHeight() {
        return 20;
    }

    public int getID() {
        return this.id;
    }

    public void putValue(@NotNull T newValue) {
        try {
            this.field.setAccessible(true);
            this.field.set(this.instance, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        try {
            this.field.setAccessible(true);
            return (T) this.field.get(this.instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}