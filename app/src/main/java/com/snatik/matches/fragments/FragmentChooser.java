package com.snatik.matches.fragments;

import android.support.v4.app.Fragment;
import com.snatik.matches.common.ScreenEnum;
import com.snatik.matches.model.Game;

public class FragmentChooser {
    public static Fragment getFragment(ScreenEnum.Screen screen, Game game) {
        switch (screen) {
            case MENU:
                return new MenuFragment();
            case DIFFICULTY:
                DifficultySelectFragment difficultySelectFragment = new DifficultySelectFragment();
                difficultySelectFragment.theme = game.theme;
                return difficultySelectFragment;
            case GAME:
                GameFragment gameFragment = new GameFragment();
                gameFragment.mPlayingGame = game;
                return gameFragment;
            case THEME_SELECT:
                return new ThemeSelectFragment();
            default:
                break;
        }
        return null;
    }
}
