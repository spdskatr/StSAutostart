package com.stsai;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SpirePatch(clz= CardCrawlGame.class, method="update")
public class StraightToGamePatch {

    private static final Logger logger = LogManager.getLogger(StraightToGamePatch.class.getName());
    private static boolean run = false;



    public static SpireReturn<Void> Prefix(CardCrawlGame __instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        if (!run) {
            logger.info("Preparing to skip splash screen and character select.");
            __instance.splashScreen = null;
            __instance.mode = CardCrawlGame.GameMode.GAMEPLAY;
            __instance.mainMenuScreen = new MainMenuScreen();

            __instance.nextDungeon = "Exordium";
            __instance.dungeonTransitionScreen = new DungeonTransitionScreen("Exordium");
            __instance.monstersSlain = __instance.elites1Slain = __instance.elites2Slain = __instance.elites3Slain = 0;
            __instance.chosenCharacter = AbstractPlayer.PlayerClass.IRONCLAD;
            Constructor constructor = Ironclad.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            AbstractDungeon.player = (Ironclad)constructor.newInstance("test");
            Settings.seed = 12345L;
            AbstractDungeon.generateSeeds();

            logger.info("We hacked the state machine!!!!!!! REALLY LOUD AND OBNOXIOUS LOG MESSAGE SO I NOTICE IT");

            run = true;
        }

        return SpireReturn.Continue();
    }
}