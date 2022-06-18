package com.stsai;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.splash.SplashScreen;

@SpirePatch(clz= SplashScreen.class, method="update")
public class AlwaysSkipSplashScreenPatch {
    public static SpireReturn<Void> Prefix(SplashScreen __instance) {
        __instance.isDone = true;
        return SpireReturn.Return();
    }
}
