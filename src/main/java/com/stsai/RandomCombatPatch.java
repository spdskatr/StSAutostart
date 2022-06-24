package com.stsai;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

@SpirePatch(clz=AbstractRoom.class, method="update")
public class RandomCombatPatch {
    private static double selectionTimer = 5.0;

    public static SpireReturn<Void> Prefix(AbstractRoom __instance) {
        AbstractRoom.RoomPhase currentPhase = __instance.phase;

        if (currentPhase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.actionManager.hasControl) {
            CardGroup hand = AbstractDungeon.player.hand;
            selectionTimer -= Gdx.graphics.getDeltaTime();
            if (selectionTimer <= 0) {
                Iterator<AbstractCard> cards = hand.group.iterator();
                ArrayList<AbstractCard> validCards = new ArrayList<>();

                while (cards.hasNext()) {
                    AbstractCard c = cards.next();
                    if (c.hasEnoughEnergy()) {
                        validCards.add(c);
                    }
                }

                int numValid = validCards.size();
                if (numValid > 0) {
                    AbstractCard cardToPlay = validCards.get(new Random().nextInt(numValid));
                    AbstractMonster target = null;

                    if (cardToPlay.target == AbstractCard.CardTarget.ENEMY || cardToPlay.target == AbstractCard.CardTarget.SELF_AND_ENEMY) {
                        target = __instance.monsters.getRandomMonster(true);
                    }
                    AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardToPlay, target));
                }
                else {
                    AbstractDungeon.actionManager.callEndTurnEarlySequence();
                }

                selectionTimer = 5.0;
            }
        }

        return SpireReturn.Continue();
    }
}