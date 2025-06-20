package net.tracystacktrace.epsilon.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.tracystacktrace.epsilon.EnumStringType;
import net.tracystacktrace.epsilon.Epsilon;
import net.tracystacktrace.epsilon.helper.IKeyTrigger;
import net.tracystacktrace.epsilon.helper.IMouseClick;
import net.tracystacktrace.hellogui.Translation;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class InteractiveStringEntry extends InteractiveConfigEntry<String> implements IKeyTrigger, IMouseClick {

    protected final String title;
    protected final EnumStringType stringType;
    protected final int maxLength;

    protected GuiTextField field;
    protected int stringX;
    protected int stringY;

    public InteractiveStringEntry(
            int id,
            @NotNull Field field,
            @NotNull Object instance,
            @NotNull Epsilon.StringElement element
    ) {
        super(id, field, instance);
        this.title = Translation.quickTranslate(element.title());
        this.stringType = element.type();
        this.maxLength = element.maxLength();
    }

    //height 34
    @Override
    public void initGui(GuiEpsilonConfig config, int x, int y, int w) {
        this.stringX = x;
        this.stringY = y;
        this.field = new GuiTextField(x, y + 14, w, 20, this.getValue());
        this.field.isFocused = false;
        this.field.setMaxStringLength(this.maxLength);
    }

    @Override
    public void keyPressed(char c, int i) {
        if (this.field.isFocused) {
            this.field.textboxKeyTyped(c, i);
        }
    }

    @Override
    public void mouseClicked(float mX, float mY, int click) {
        this.field.mouseClicked(mX, mY, click);
    }

    @Override
    public void finish() {
        this.putValue(this.field.getText());
    }

    @Override
    public void render(float mouseX, float mouseY) {
        Minecraft.getInstance().fontRenderer.drawStringWithShadow(this.title, this.stringX, this.stringY, 0xFFFFFFFF);
        this.field.drawTextBox();
    }

    @Override
    public int getHeight() {
        return 34;
    }
}
