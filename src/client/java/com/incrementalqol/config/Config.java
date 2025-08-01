package com.incrementalqol.config;

import com.incrementalqol.common.data.TaskCollection;
import com.incrementalqol.common.data.Skills.*;
import com.incrementalqol.common.data.TaskCollection;
import com.incrementalqol.common.data.ToolType;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {
    @SerialEntry
    private boolean loggingEnabled;

    @SerialEntry
    private boolean balloonRopeEnabled;

    @SerialEntry
    private boolean hudBackground;
    @SerialEntry
    private int hudPosX;
    @SerialEntry
    private int hudPosY;
    @SerialEntry
    private double hudScale = 1.0;
    @SerialEntry
    private boolean isSortedByType;
    @SerialEntry
    private boolean autoSwapWardrobe;
    @SerialEntry
    private boolean autoSwapTools;
    @SerialEntry
    private boolean autoLevelUp;

    @SerialEntry
    private String combatWardrobeName = "1";
    @SerialEntry
    private int meleeWeaponSlot = 0;
    @SerialEntry
    private int rangedWeaponSlot = 5;

    @SerialEntry
    private String miningWardrobeName = "2";
    @SerialEntry
    private int miningWeaponSlot = 1;
    @SerialEntry
    private String foragingWardrobeName = "3";
    @SerialEntry
    private int foragingWeaponSlot = 2;
    @SerialEntry
    private String farmingWardrobeName = "4";
    @SerialEntry
    private int farmingWeaponSlot = 3;
    @SerialEntry
    private String fishingWardrobeName = "5";
    @SerialEntry
    private String bossWardrobeName = "6";
    @SerialEntry
    private String combatFishingWardrobeName = "7";
    @SerialEntry
    private String dreamFarmingWardrobeName = "8";
    @SerialEntry
    private int fishingWeaponSlot = 4;

    @SerialEntry
    private boolean autoSkillLeveling;
    @SerialEntry
    public List<NormalCombatSkill> normalCombatSkills = new ArrayList<>();
    @SerialEntry
    public List<NormalMiningSkill> normalMiningSkills = new ArrayList<>();
    @SerialEntry
    public List<NormalForagingSkill> normalForagingSkills = new ArrayList<>();
    @SerialEntry
    public List<NormalFarmingSkill> normalFarmingSkills = new ArrayList<>();
    @SerialEntry
    public List<NormalSpearFishingSkill> normalSpearFishingSkills = new ArrayList<>();
    @SerialEntry
    public List<NormalSharpshootingSkill> normalSharpshootingSkills = new ArrayList<>();

    @SerialEntry
    public List<NightmareCombatSkill> nightmareCombatSkills = new ArrayList<>();
    @SerialEntry
    public List<NightmareMiningSkill> nightmareMiningSkills = new ArrayList<>();
    @SerialEntry
    public List<NightmareForagingSkill> nightmareForagingSkills = new ArrayList<>();
    @SerialEntry
    public List<NightmareFarmingSkill> nightmareFarmingSkills = new ArrayList<>();
    @SerialEntry
    public List<NightmareSpearFishingSkill> nightmareSpearFishingSkills = new ArrayList<>();
    @SerialEntry
    public List<NightmareSharpshootingSkill> nightmareSharpshootingSkills = new ArrayList<>();

    public String getWardrobeNameToDefault(TaskCollection.TaskType defaultWardrobe) {
        return switch (defaultWardrobe) {
            case TaskCollection.TaskType.Combat -> combatWardrobeName;
            case TaskCollection.TaskType.Mining -> miningWardrobeName;
            case TaskCollection.TaskType.Foraging -> foragingWardrobeName;
            case TaskCollection.TaskType.Farming -> farmingWardrobeName;
            case TaskCollection.TaskType.Fishing -> fishingWardrobeName;
            case TaskCollection.TaskType.Boss -> bossWardrobeName;
            case TaskCollection.TaskType.CombatFishing -> combatFishingWardrobeName;
            case TaskCollection.TaskType.DreamFarming -> dreamFarmingWardrobeName;
            default -> defaultWardrobe.getString();
        };
    }

    public int getSlotToDefault(ToolType defaultToolType) {
        return switch (defaultToolType) {
            case ToolType.Melee -> meleeWeaponSlot;
            case ToolType.Pickaxe -> miningWeaponSlot;
            case ToolType.Axe -> foragingWeaponSlot;
            case ToolType.Hoe -> farmingWeaponSlot;
            case ToolType.FishingRod -> fishingWeaponSlot;
            case ToolType.Bow -> rangedWeaponSlot;
        };
    }

    public boolean getBalloonRopeEnabled() {
        return balloonRopeEnabled;
    }

    public boolean getLoggingEnabled() {
        return loggingEnabled;
    }
    public int getHudPosX() {
        return hudPosX;
    }

    public int getHudPosY() {
        return hudPosY;
    }

    public boolean getHudBackground() {
        return hudBackground;
    }

    public double getHudScale() {
        return hudScale;
    }

    public boolean getSortedByType() {
        return isSortedByType;
    }

    public boolean getAutoSwapWardrobe() {
        return autoSwapWardrobe;
    }

    public boolean getAutoSwapTools() {
        return autoSwapTools;
    }

    public boolean getAutoSkillLeveling() {
        return autoSkillLeveling;
    }

    public boolean getAutoLevelUp() {
        return autoLevelUp;
    }

    public void setHudPosX(int hudPosX) {
        this.hudPosX = hudPosX;
    }

    public void setHudPosY(int hudPosY) {
        this.hudPosY = hudPosY;
    }

    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("incremental-qol", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("incremental-qol.json5"))
                    .setJson5(true)
                    .build())
            .build();

    public Screen createScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .save(HANDLER::save)
                .title(Text.of("Incremental Qol"))
                .categories(Arrays.asList(
                        TaskCategory(),
                        SkillLeveling(),
                        Others(),
                        Debug())
                )
                .build()
                .generateScreen(parent);
    }

    public ConfigCategory TaskCategory() {
        return ConfigCategory.createBuilder()
                .name(Text.of("Task"))
                .tooltip(Text.of("This category is about configuring the Task related settings, like warp customization and Hud."))
                .group(OptionGroup.createBuilder()
                        .name(Text.of("Task HUD configuration"))
                        .description(OptionDescription.of(Text.of("These are the basic overrides of task categories.")))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Toggle HUD background"))
                                .description(OptionDescription.of(Text.of("Turn on and off the gray background of the task HUD.")))
                                .binding(true, () -> this.hudBackground, newVal -> this.hudBackground = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Order Tasks by Type"))
                                .description(OptionDescription.of(Text.of("Order Tasks on Hud by Type, not by default order.")))
                                .binding(false, () -> this.isSortedByType, newVal -> this.isSortedByType = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Double>createBuilder()
                                .name(Text.of("Resize the HUD."))
                                .description(OptionDescription.of(Text.of("Setting scale ration from 0.5x-2x with 0.1 steps.")))
                                .binding(1.0, () -> this.hudScale, newVal -> this.hudScale = newVal)
                                .controller(o -> DoubleSliderControllerBuilder.create(o).step(0.1).range(0.5, 2.0))
                                .build())
                        .option(ButtonOption.createBuilder()
                                .name(Text.of("Task HUD Position"))
                                .description(OptionDescription.of(Text.of("Activate the function to move the task HUD. Press ESC to return here.")))
                                .action((t, o) -> MinecraftClient.getInstance().setScreen(new DraggableScreen(t)))
                                .build())
                        .build())
                .group(OptionGroup.createBuilder()
                        .name(Text.of("Task Warp extras"))
                        .description(OptionDescription.of(Text.of("These are some extra functions for Task Warp.")))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Toggle Auto LevelUp"))
                                .description(OptionDescription.of(Text.of("Turn on and off auto LevelUp when no task remained when using Next Warp.")))
                                .binding(false, () -> this.autoLevelUp, newVal -> this.autoLevelUp = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .group(OptionGroup.createBuilder()
                        .name(Text.of("Task Category Auto Swap of Wardrobes"))
                        .description(OptionDescription.of(Text.of("These are the basic settings of task category based auto wardrobe swap.")))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Toggle the Auto Swap of Wardrobe"))
                                .description(OptionDescription.of(Text.of("Turning on/off the auto swap of wardrobes functionality")))
                                .binding(true, () -> this.autoSwapWardrobe, newVal -> this.autoSwapWardrobe = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Combat Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for Combat tasks.")))
                                .binding("1", () -> this.combatWardrobeName, newVal -> this.combatWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Mining Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for Mining tasks.")))
                                .binding("2", () -> this.miningWardrobeName, newVal -> this.miningWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Foraging Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for Foraging tasks.")))
                                .binding("3", () -> this.foragingWardrobeName, newVal -> this.foragingWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Farming Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for Farming tasks.")))
                                .binding("4", () -> this.farmingWardrobeName, newVal -> this.farmingWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Fishing Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for Fishing tasks.")))
                                .binding("5", () -> this.fishingWardrobeName, newVal -> this.fishingWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Boss Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for Boss tasks (e.g.: Hoglin)")))
                                .binding("6", () -> this.bossWardrobeName, newVal -> this.bossWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Combat Fishing Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for Combat fishing tasks (e.g.: Crabs)")))
                                .binding("7", () -> this.combatFishingWardrobeName, newVal -> this.combatFishingWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .option(Option.<String>createBuilder()
                                .name(Text.of("Dream Farming Wardrobe Name"))
                                .description(OptionDescription.of(Text.of("The name of your wardrobe slot for dream farming tasks (e.g.: Oinky)")))
                                .binding("8", () -> this.dreamFarmingWardrobeName, newVal -> this.dreamFarmingWardrobeName = newVal)
                                .controller(StringControllerBuilder::create)
                                .build())
                        .build())
                .group(OptionGroup.createBuilder()
                        .name(Text.of("Task Category Auto Swap of Tools"))
                        .description(OptionDescription.of(Text.of("These are the basic settings of task category based auto tool swap.")))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Toggle the Auto Swap of Tools"))
                                .description(OptionDescription.of(Text.of("Turning on/off the auto swap of tools functionality")))
                                .binding(true, () -> this.autoSwapTools, newVal -> this.autoSwapTools = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.of("Melee Weapon HotBar Slot"))
                                .description(OptionDescription.of(Text.of("The slot on the HotBar for your melee weapon. (1-8)")))
                                .binding(1, () -> this.meleeWeaponSlot + 1, newVal -> this.meleeWeaponSlot = newVal - 1)
                                .controller(o -> IntegerSliderControllerBuilder.create(o).step(1).range(1, 8))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.of("Ranged Weapon HotBar Slot"))
                                .description(OptionDescription.of(Text.of("The slot on the HotBar for your ranged weapon. (1-8)")))
                                .binding(6, () -> this.rangedWeaponSlot + 1, newVal -> this.rangedWeaponSlot = newVal - 1)
                                .controller(o -> IntegerSliderControllerBuilder.create(o).step(1).range(1, 8))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.of("Pickaxe HotBar Slot"))
                                .description(OptionDescription.of(Text.of("The slot on the HotBar for your pickaxe. (1-8)")))
                                .binding(2, () -> this.miningWeaponSlot + 1, newVal -> this.miningWeaponSlot = newVal - 1)
                                .controller(o -> IntegerSliderControllerBuilder.create(o).step(1).range(1, 8))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.of("Axe HotBar Slot"))
                                .description(OptionDescription.of(Text.of("The slot on the HotBar for your axe. (1-8)")))
                                .binding(3, () -> this.foragingWeaponSlot + 1, newVal -> this.foragingWeaponSlot = newVal - 1)
                                .controller(o -> IntegerSliderControllerBuilder.create(o).step(1).range(1, 8))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.of("Hoe HotBar Slot"))
                                .description(OptionDescription.of(Text.of("The slot on the HotBar for your hoe. (1-8)")))
                                .binding(4, () -> this.farmingWeaponSlot + 1, newVal -> this.farmingWeaponSlot = newVal - 1)
                                .controller(o -> IntegerSliderControllerBuilder.create(o).step(1).range(1, 8))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Text.of("Fishing Rod HotBar Slot"))
                                .description(OptionDescription.of(Text.of("The slot on the HotBar for your fishing rod. (1-8)")))
                                .binding(5, () -> this.fishingWeaponSlot + 1, newVal -> this.fishingWeaponSlot = newVal - 1)
                                .controller(o -> IntegerSliderControllerBuilder.create(o).step(1).range(1, 8))
                                .build())
                        .build())
                .build();
    }

    public ConfigCategory SkillLeveling() {
        return ConfigCategory.createBuilder()
                .name(Text.of("Skill Auto Leveling"))
                .tooltip(Text.of("This category is about configuring the order in which the skills should auto unlock on level up. The skills will expected to be leveled to the level of the number of their appearance from top to bottom."))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.of("Toggle Auto Skill Leveling"))
                        .description(OptionDescription.of(Text.of("Turn on and off the auto leveling function.")))
                        .binding(false, () -> this.autoSkillLeveling, newVal -> this.autoSkillLeveling = newVal)
                        .controller(BooleanControllerBuilder::create)
                        .build())
                // Normal section
                .group(ListOption.<NormalCombatSkill>createBuilder()
                        .name(Text.of("Combat [Normal]"))
                        .binding(this.normalCombatSkills, () -> this.normalCombatSkills, newVal -> this.normalCombatSkills = newVal)
                        .description(OptionDescription.of(Text.of("The combat skill level up order in Normal World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NormalCombatSkill::getDisplayName)
                        )
                        .initial(NormalCombatSkill.SweepingStrike)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NormalMiningSkill>createBuilder()
                        .name(Text.of("Mining [Normal]"))
                        .binding(this.normalMiningSkills, () -> this.normalMiningSkills, newVal -> this.normalMiningSkills = newVal)
                        .description(OptionDescription.of(Text.of("The mining skill level up order in Normal World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NormalMiningSkill::getDisplayName)
                        )
                        .initial(NormalMiningSkill.Ricochet)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NormalForagingSkill>createBuilder()
                        .name(Text.of("Foraging [Normal]"))
                        .binding(this.normalForagingSkills, () -> this.normalForagingSkills, newVal -> this.normalForagingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The foraging skill level up order in Normal World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NormalForagingSkill::getDisplayName)
                        )
                        .initial(NormalForagingSkill.Timberstrike)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NormalFarmingSkill>createBuilder()
                        .name(Text.of("Farming [Normal]"))
                        .binding(this.normalFarmingSkills, () -> this.normalFarmingSkills, newVal -> this.normalFarmingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The farming skill level up order in Normal World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NormalFarmingSkill::getDisplayName)
                        )
                        .initial(NormalFarmingSkill.Harvester)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NormalSpearFishingSkill>createBuilder()
                        .name(Text.of("Spear Fishing [Normal]"))
                        .binding(this.normalSpearFishingSkills, () -> this.normalSpearFishingSkills, newVal -> this.normalSpearFishingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The spear fishing skill level up order in Normal World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NormalSpearFishingSkill::getDisplayName)
                        )
                        .initial(NormalSpearFishingSkill.SpoonBender)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NormalSharpshootingSkill>createBuilder()
                        .name(Text.of("Sharpshooting [Normal]"))
                        .binding(this.normalSharpshootingSkills, () -> this.normalSharpshootingSkills, newVal -> this.normalSharpshootingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The sharpshooting skill level up order in Normal World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NormalSharpshootingSkill::getDisplayName)
                        )
                        .initial(NormalSharpshootingSkill.ExplosiveArrow)
                        .collapsed(true)
                        .build())
                // Nightmare section
                .group(ListOption.<NightmareCombatSkill>createBuilder()
                        .name(Text.of("Combat [Nightmare]"))
                        .binding(this.nightmareCombatSkills, () -> this.nightmareCombatSkills, newVal -> this.nightmareCombatSkills = newVal)
                        .description(OptionDescription.of(Text.of("The combat skill level up order in Nightmare World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NightmareCombatSkill::getDisplayName)
                        )
                        .initial(NightmareCombatSkill.DevilsGambit)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NightmareMiningSkill>createBuilder()
                        .name(Text.of("Mining [Nightmare]"))
                        .binding(this.nightmareMiningSkills, () -> this.nightmareMiningSkills, newVal -> this.nightmareMiningSkills = newVal)
                        .description(OptionDescription.of(Text.of("The mining skill level up order in Nightmare World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NightmareMiningSkill::getDisplayName)
                        )
                        .initial(NightmareMiningSkill.Shatterpoint)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NightmareForagingSkill>createBuilder()
                        .name(Text.of("Foraging [Nightmare]"))
                        .binding(this.nightmareForagingSkills, () -> this.nightmareForagingSkills, newVal -> this.nightmareForagingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The foraging skill level up order in Nightmare World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NightmareForagingSkill::getDisplayName)
                        )
                        .initial(NightmareForagingSkill.AxeJuggling)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NightmareFarmingSkill>createBuilder()
                        .name(Text.of("Farming [Nightmare]"))
                        .binding(this.nightmareFarmingSkills, () -> this.nightmareFarmingSkills, newVal -> this.nightmareFarmingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The farming skill level up order in Nightmare World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NightmareFarmingSkill::getDisplayName)
                        )
                        .initial(NightmareFarmingSkill.Landscaper)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NightmareSpearFishingSkill>createBuilder()
                        .name(Text.of("Spear Fishing [Nightmare]"))
                        .binding(this.nightmareSpearFishingSkills, () -> this.nightmareSpearFishingSkills, newVal -> this.nightmareSpearFishingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The spear fishing skill level up order in Nightmare World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NightmareSpearFishingSkill::getDisplayName)
                        )
                        .initial(NightmareSpearFishingSkill.FishSenses)
                        .collapsed(true)
                        .build())
                .group(ListOption.<NightmareSharpshootingSkill>createBuilder()
                        .name(Text.of("Sharpshooting [Nightmare]"))
                        .binding(this.nightmareSharpshootingSkills, () -> this.nightmareSharpshootingSkills, newVal -> this.nightmareSharpshootingSkills = newVal)
                        .description(OptionDescription.of(Text.of("The sharpshooting skill level up order in Nightmare World.")))
                        .insertEntriesAtEnd(true)
                        .controller(o -> EnumDropdownControllerBuilder.create(o)
                                .formatValue(NightmareSharpshootingSkill::getDisplayName)
                        )
                        .initial(NightmareSharpshootingSkill.PeaShooter)
                        .collapsed(true)
                        .build())
                .build();
    }

    public ConfigCategory Others() {
        return ConfigCategory.createBuilder()
                .name(Text.of("Others"))
                .tooltip(Text.of("This category is smaller extra options not connected to any big category."))
                .option(Option.<Boolean>createBuilder()
                        .name(Text.of("Toggle balloon ropes for self."))
                        .description(OptionDescription.of(Text.of("Turn on and off the balloon rope attached to the player.")))
                        .binding(false, () -> this.balloonRopeEnabled, newVal -> this.balloonRopeEnabled = newVal)
                        .controller(BooleanControllerBuilder::create)
                        .build())
                .build();
    }

    public ConfigCategory Debug() {
        return ConfigCategory.createBuilder()
                .name(Text.of("Development"))
                .tooltip(Text.of("This category is about configurations to help development and debug."))
                .group(OptionGroup.createBuilder()
                        .name(Text.of("Logging settings"))
                        .description(OptionDescription.of(Text.of("These are the Logging related settings.")))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Toggle global logging"))
                                .description(OptionDescription.of(Text.of("Turn on and off the logging in the mod on all feature.")))
                                .binding(true, () -> this.loggingEnabled, newVal -> this.loggingEnabled = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .build();
    }
}
