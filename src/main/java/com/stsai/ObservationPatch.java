package com.stsai;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

@SpirePatch(clz=AbstractDungeon.class, method="update")
public class ObservationPatch {
    private static final Logger logger = LogManager.getLogger(StraightToGamePatch.class.getName());

    private static AbstractDungeon.CurrentScreen lastScreen = null;
    private static AbstractRoom.RoomPhase lastPhase = null;
    private static HashSet<AbstractCard> lastHand = new HashSet<>();

    public static SpireReturn<Void> Prefix(AbstractDungeon __instance) {
        AbstractDungeon.CurrentScreen currentScreen = AbstractDungeon.screen;
        AbstractRoom.RoomPhase currentPhase = AbstractDungeon.getCurrRoom().phase;
        CardGroup hand = AbstractDungeon.player.hand;
        HashSet<AbstractCard> currentHand = new HashSet<>(hand.group);

        if (lastScreen != currentScreen || lastPhase != currentPhase) {
            logger.info(currentScreen);
            logger.info(currentPhase);

            lastScreen = currentScreen;
            lastPhase = currentPhase;
        }

        if (!currentHand.equals(lastHand)) {
            logger.info("\n" + AbstractDungeon.player.hand);
            lastHand = currentHand;
        }

        return SpireReturn.Continue();
    }
}