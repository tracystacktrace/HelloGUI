package net.tracystacktrace.epsilon.gui;

import net.minecraft.client.gui.GuiButton;
import net.tracystacktrace.epsilon.EnumToggleType;
import net.tracystacktrace.epsilon.Epsilon;
import net.tracystacktrace.epsilon.helper.IActionTrigger;
import net.tracystacktrace.hellogui.Translation;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class InteractiveToggleEntry extends InteractiveConfigEntry<Boolean> implements IActionTrigger {

    protected final String title;
    protected final EnumToggleType toggleType;

    protected GuiButton button;
    protected boolean value;

    public InteractiveToggleEntry(
            int id,
            @NotNull Field field,
            @NotNull Object instance,
            @NotNull Epsilon.ToggleElement element
    ) {
        super(id, field, instance);
        this.title = Translation.quickTranslate(element.title());
        this.toggleType = element.type();
    }

    @Override
    public void initGui(GuiEpsilonConfig config, int x, int y, int w) {
        this.value = getValue();
        this.button = new GuiButton(this.id, x, y, w, 20, "");
        this.updateDisplayString();
        config.addButton(button);
    }

    @Override
    public void render(float mouseX, float mouseY) {
        //no need
    }

    @Override
    public void finish() {
        this.putValue(this.value);
    }

    @Override
    public void onAction(GuiEpsilonConfig menu, GuiButton button) {
        this.value = !this.value;
        this.updateDisplayString();
    }

    private void updateDisplayString() {
        this.button.displayString = String.format(this.title, this.toggleType.getTranslationKey(this.value));
    }

}
