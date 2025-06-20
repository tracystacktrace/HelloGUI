package net.tracystacktrace.epsilon;

import net.minecraft.client.gui.GuiScreen;
import net.tracystacktrace.epsilon.gui.GuiEpsilonConfig;
import net.tracystacktrace.epsilon.gui.InteractiveStringEntry;
import net.tracystacktrace.epsilon.gui.InteractiveToggleEntry;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Advanced decoration based API for creating automated config GUIs
 * <br>
 * Recommended to be used with FoxLoader's in-built config system
 * <br>
 * This does not provide any I/O or save options, it relies on external APIs
 */
public final class Epsilon {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface StringElement {
        String groupID();

        EnumStringType type() default EnumStringType.DEFAULT;

        String title();

        int maxLength() default 128;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ToggleElement {
        String groupID();

        EnumToggleType type() default EnumToggleType.ENABLE_DISABLE;

        String title();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ColorElement {
        String groupID();

        EnumColorType type() default EnumColorType.ARGB;

        String title();

        boolean advancedInterface() default true;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ChoiceElement {
        String groupID();

        String title();

        String[] options();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface SliderElement {
        String groupID();

        EnumSliderType type() default EnumSliderType.STANDARD;

        String title();
    }

    /* inner + cougar generate code */

    public static @Nullable GuiScreen generateConfigScreen(
            @Nullable Object object,
            @Nullable String id
    ) {
        if (object == null || id == null || id.isEmpty()) {
            return null;
        }

        final GuiEpsilonConfig configMenu = new GuiEpsilonConfig();
        final Field[] fields = object.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            final Field field = fields[i];

            if (field.isAnnotationPresent(StringElement.class)) {
                final StringElement element = field.getAnnotation(StringElement.class);
                if (!id.equals(element.groupID())) {
                    continue;
                }
                configMenu.add(new InteractiveStringEntry(i, field, object, element));
                continue;
            }

            if (field.isAnnotationPresent(ToggleElement.class)) {
                final ToggleElement element = field.getAnnotation(ToggleElement.class);
                if (!id.equals(element.groupID())) {
                    continue;
                }
                configMenu.add(new InteractiveToggleEntry(i, field, object, element));
                continue;
            }

        }

        return configMenu;
    }
}
