package net.tracystacktrace.epsilon.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.tracystacktrace.epsilon.helper.IActionTrigger;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

public class GuiEpsilonConfig extends GuiScreen {

    protected final List<InteractiveConfigEntry<?>> entries = new ArrayList<>();

    public <T> void add(InteractiveConfigEntry<T> trigger) {
        entries.add(trigger);
    }

    @ApiStatus.Internal
    public void addButton(GuiButton button) {
        this.controlList.add(button);
    }

    @Override
    public void initGui() {
        this.controlList.clear();

        final int offsetY = this.getOffsetY();
        final int widthElement = 200;

        for(int i = 0; i < entries.size(); i++) {
            final InteractiveConfigEntry<?> entry = entries.get(i);
            entry.initGui(this, this.width / 2 - widthElement / 2, offsetY + this.getJumpFor(i), widthElement);
        }
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if(guiButton.enabled) {
            InteractiveConfigEntry<?> entry = this.searchByID(guiButton.id);
            if(entry instanceof IActionTrigger actionTrigger) {
                actionTrigger.onAction(this, guiButton);
                return;
            }


        }
    }

    private int getOffsetY() {
        int dh = 0;
        for (InteractiveConfigEntry<?> entry : entries) {
            dh += entry.getHeight();
        }
        return this.height / 2 - dh / 2;
    }

    private int getJumpFor(int till) {
        int collected = 0;
        for(int i = 0; i < till; i++) {
            collected += entries.get(i).getHeight();
        }
        return collected;
    }

    private InteractiveConfigEntry<?> searchByID(int id) {
        for(InteractiveConfigEntry<?> entry : entries) {
            if(entry.getID() == id) {
                return entry;
            }
        }
        return null;
    }
}
