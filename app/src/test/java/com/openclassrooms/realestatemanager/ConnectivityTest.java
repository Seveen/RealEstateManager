package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class ConnectivityTest {

	private ConnectivityManager connectivityManager ;
	private ShadowConnectivityManager shadowConnectivityManager;
	private ShadowNetworkInfo shadowOfActiveNetworkInfo;

	@Before
	public void setUp() {
		connectivityManager = getConnectivityManager();
		shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
		shadowOfActiveNetworkInfo = Shadows.shadowOf(connectivityManager.getActiveNetworkInfo());
	}


	@Test
	public void connectivityTest_shouldWork() {
		NetworkInfo networkInfo =  ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
		shadowConnectivityManager.setActiveNetworkInfo(networkInfo);

		assertTrue(Utils.isInternetAvailable(RuntimeEnvironment.systemContext));

		networkInfo =  ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, false);
		shadowConnectivityManager.setActiveNetworkInfo(networkInfo);

		assertFalse(Utils.isInternetAvailable(RuntimeEnvironment.systemContext));
	}

	private ConnectivityManager getConnectivityManager() {
		return (ConnectivityManager) RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
}
