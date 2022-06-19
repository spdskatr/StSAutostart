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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@SpirePatch(clz= CardCrawlGame.class, method="update")
public class StraightToGamePatch {

    private static final Logger logger = LogManager.getLogger(StraightToGamePatch.class.getName());
    private static boolean run = false;



    public static SpireReturn<Void> Prefix(CardCrawlGame __instance) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!run) {
            //logger.info("LOUD TEST TEST LOUD TEST TEST");

            CardCrawlGame.splashScreen = null;
            CardCrawlGame.mode = CardCrawlGame.GameMode.GAMEPLAY;
            CardCrawlGame.mainMenuScreen = new MainMenuScreen();

            CardCrawlGame.nextDungeon = "Exordium";
            CardCrawlGame.dungeonTransitionScreen = new DungeonTransitionScreen("Exordium");
            CardCrawlGame.monstersSlain = CardCrawlGame.elites1Slain = CardCrawlGame.elites2Slain = CardCrawlGame.elites3Slain = 0;
            CardCrawlGame.chosenCharacter = AbstractPlayer.PlayerClass.IRONCLAD;
            Constructor<Ironclad> constructor = Ironclad.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            AbstractDungeon.player = constructor.newInstance("test");
            Settings.seed = 12345L;
            AbstractDungeon.generateSeeds();

            //logger.info("We hacked the state machine!!!!!!! REALLY LOUD AND OBNOXIOUS LOG MESSAGE SO I NOTICE IT");

            run = true;
        }

        return SpireReturn.Continue();
    }
}