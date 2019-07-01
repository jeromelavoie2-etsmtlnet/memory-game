package com.snatik.matches.engine;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.snatik.matches.R;
import com.snatik.matches.common.ScreenEnum;
import com.snatik.matches.common.Shared;
import com.snatik.matches.events.ui.ResetBackgroundEvent;
import com.snatik.matches.model.Game;

import static com.snatik.matches.fragments.FragmentChooser.getFragment;

public class ScreenController {

	public static Game mPlayingGame = null;
	private static ScreenController mInstance = null;
	private static List<ScreenEnum.Screen> openedScreens = new ArrayList<ScreenEnum.Screen>();
	private FragmentManager mFragmentManager;

	private ScreenController() {
	}

	public static ScreenController getInstance() {
		if (mInstance == null) {
			mInstance = new ScreenController();
		}
		return mInstance;
	}

	public static ScreenEnum.Screen getLastScreen() {
		return openedScreens.get(openedScreens.size() - 1);
	}

	public void openScreen(ScreenEnum.Screen screen) {
		mFragmentManager = Shared.activity.getSupportFragmentManager();
		
		if (screen == ScreenEnum.Screen.GAME && openedScreens.get(openedScreens.size() - 1) == ScreenEnum.Screen.GAME) {
			openedScreens.remove(openedScreens.size() - 1);
		} else if (screen == ScreenEnum.Screen.DIFFICULTY && openedScreens.get(openedScreens.size() - 1) == ScreenEnum.Screen.GAME) {
			openedScreens.remove(openedScreens.size() - 1);
			openedScreens.remove(openedScreens.size() - 1);
		}

		Fragment fragment = getFragment(screen, mPlayingGame);
		FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
		openedScreens.add(screen);
	}

	public boolean onBack() {
		if (openedScreens.size() > 0) {
			ScreenEnum.Screen screenToRemove = openedScreens.get(openedScreens.size() - 1);
			openedScreens.remove(openedScreens.size() - 1);
			if (openedScreens.size() == 0) {
				return true;
			}
			ScreenEnum.Screen screen = openedScreens.get(openedScreens.size() - 1);
			openedScreens.remove(openedScreens.size() - 1);
			openScreen(screen);
			if ((screen == ScreenEnum.Screen.THEME_SELECT || screen == ScreenEnum.Screen.MENU) &&
					(screenToRemove == ScreenEnum.Screen.DIFFICULTY || screenToRemove == ScreenEnum.Screen.GAME)) {
				Shared.eventBus.notify(new ResetBackgroundEvent());
			}
			return false;
		}
		return true;
	}
}
