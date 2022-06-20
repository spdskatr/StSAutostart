package com.stsai;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.splash.SplashScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlwaysSkipSplashScreenPatch {

    private static final Logger logger = LogManager.getLogger(AlwaysSkipSplashScreenPatch.class.getName());
    public static SpireReturn<Void> Prefix(SplashScreen __instance) {
        // CardCrawlGame.mode = CardCrawlGame.GameMode.CHAR_SELECT;
        // __instance.isDone = true;
        logger.info("Got here");
        return SpireReturn.Continue();
    }
}