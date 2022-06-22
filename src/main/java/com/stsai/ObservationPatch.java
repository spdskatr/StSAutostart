package com.stsai;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch(clz= AbstractDungeon.class, method="update")
public class ObservationPatch {
    private static final Logger logger = LogManager.getLogger(StraightToGamePatch.class.getName());
    private static AbstractDungeon.CurrentScreen lastScreen = null;
    private static AbstractRoom.RoomPhase lastPhase = null;

    public static SpireReturn<Void> Prefix(AbstractDungeon __instance) {
        AbstractDungeon.CurrentScreen currentScreen = __instance.screen;
        AbstractRoom.RoomPhase currentPhase = __instance.getCurrRoom().phase;

        if (lastScreen != currentScreen || lastPhase != currentPhase) {
            logger.info(currentScreen);
            logger.info(currentPhase);

            lastScreen = currentScreen;
            lastPhase = currentPhase;
        }

        return SpireReturn.Continue();
    }
}