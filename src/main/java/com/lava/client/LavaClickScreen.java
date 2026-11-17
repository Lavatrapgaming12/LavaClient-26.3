package com.lava.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashMap;
import java.util.Map;

public class LavaClickScreen extends Screen {

    private static final String[] CATEGORIES = {
        "Combat", "Visual", "Movement", "Player",
        "World", "Render", "Misc", "Settings"
    };

    private static final Map<String, Boolean> MODULES =
        new LinkedHashMap<>();

    private String selectedCategory = "Visual";

    static {
        MODULES.put("Fullbright", false);
        MODULES.put("FPS Boost", false);
        MODULES.put("FPS Display", false);
        MODULES.put("Keystrokes", false);
        MODULES.put("CPS Counter", false);
        MODULES.put("Coordinates", false);
        MODULES.put("Custom HUD", false);
    }

    public LavaClickScreen() {
        super(Component.literal("Lava Client"));
    }

    @Override
    protected void init() {
        int left = width / 2 - 190;
        int top = height / 2 - 115;

        for (int i = 0; i < CATEGORIES.length; i++) {
            String category = CATEGORIES[i];

            addRenderableWidget(
                Button.builder(Component.literal(category), button -> {
                    selectedCategory = category;
                    rebuildWidgets();
                }).bounds(left + 12, top + 42 + i * 25, 105, 22).build()
            );
        }

        String[] modules = getModules(selectedCategory);

        for (int i = 0; i < modules.length; i++) {
            String module = modules[i];

            MODULES.putIfAbsent(module, false);

            addRenderableWidget(
                Button.builder(
                    Component.literal(
                        module + ": " +
                        (MODULES.get(module) ? "ON" : "OFF")
                    ),
                    button -> {
                        MODULES.put(module, !MODULES.get(module));
                        rebuildWidgets();
                    }
                ).bounds(left + 140, top + 42 + i * 30, 190, 24).build()
            );
        }
    }

    private String[] getModules(String category) {
        return switch (category) {
            case "Visual" -> new String[]{
                "Fullbright", "FPS Boost", "FPS Display",
                "Keystrokes", "CPS Counter", "Coordinates",
                "Custom HUD"
            };
            case "Render" -> new String[]{
                "FPS Display", "Custom HUD"
            };
            case "Combat" -> new String[]{
                "AutoClicker", "Reach", "Criticals"
            };
            case "Movement" -> new String[]{
                "Toggle Sprint", "Auto Sprint"
            };
            case "Player" -> new String[]{
                "Armor HUD", "Potion HUD"
            };
            case "World" -> new String[]{
                "World Info", "Time Display"
            };
            case "Misc" -> new String[]{
                "Auto GG", "Client Sounds"
            };
            default -> new String[]{
                "Client Settings"
            };
        };
    }

    @Override
    public void extractRenderState(
        GuiGraphicsExtractor graphics,
        int mouseX,
        int mouseY,
        float delta
    ) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);

        int left = width / 2 - 190;
        int top = height / 2 - 115;

        graphics.fill(
            left, top, left + 390, top + 240,
            0xEE100808
        );

        graphics.fill(
            left, top, left + 390, top + 3,
            0xFFFF2020
        );

        graphics.text(
            font,
            "LAVA CLIENT",
            left + 12, top + 12,
            0xFFFF3030,
            true
        );

        graphics.text(
            font,
            "v26.3  |  " + selectedCategory,
            left + 140, top + 17,
            0xFFFFFFFF,
            true
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
  }
      
