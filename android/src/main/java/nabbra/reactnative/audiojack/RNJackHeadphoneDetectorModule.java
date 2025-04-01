package nabbra.reactnative.audiojack;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNJackHeadphoneDetectorModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  /**
   * Name of the module when provided to React Native {@code NativeModules}.
   */
  public static final String MODULE_NAME = "RNJackHeadphoneDetectorModule";

  /**
   * Logging definition.
   */
  private static final String TAG = RNJackHeadphoneDetectorModule.class.getSimpleName();

  /**
   * A broadcast receiver to accept intents.
   */
  private BroadcastReceiver mReceiver;

  public RNJackHeadphoneDetectorModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @SuppressWarnings("deprecation")
  private Boolean isJackDeviceConnected() {
    AudioManager audioManager = (AudioManager) getReactApplicationContext().getSystemService(Context.AUDIO_SERVICE);

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return audioManager.isWiredHeadsetOn();
    }

    return isHeadphoneConnectedForPreAndroidM(audioManager);
  }

  @SuppressLint("InlinedApi")
  private Boolean isHeadphoneConnectedForPreAndroidM(AudioManager audioManager) {
    AudioDeviceInfo[] devices = getAudioDevicesFromManager(audioManager);

      for (AudioDeviceInfo device : devices) {
          return (
                  device.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES
                          || device.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET
                          || device.getType() == AudioDeviceInfo.TYPE_USB_HEADSET
          );
      }

    return false;
  }

  @SuppressLint("InlinedApi")
  private AudioDeviceInfo[] getAudioDevicesFromManager(AudioManager audioManager) {
    return audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
  }

  private void sendEvent(ReactApplicationContext reactContext, EventType event, @Nullable Boolean value) {
    reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(event.name, value);
  }

  private void registerReceiver() {
    final ReactApplicationContext reactContext = getReactApplicationContext();

    if (mReceiver != null) {
      return;
    }

    mReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Boolean result = isJackDeviceConnected();

        sendEvent(reactContext, EventType.AUDIO_JACK_HEADPHONE_STATE_CHANGED, result);
      }
    };

    reactContext.registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
  }

  private void unregisterReceiver() {
    if (mReceiver == null) {
      return;
    }
    getReactApplicationContext().unregisterReceiver(mReceiver);
    mReceiver = null;
  }

  @NonNull
  @Override
  public String getName() {
    return MODULE_NAME;
  }

  @Override
  public void initialize() {
    getReactApplicationContext().addLifecycleEventListener(this);
    registerReceiver();
  }

  @Override
  public void onHostResume() {
    registerReceiver();
  }

  @Override
  public void onHostPause() {
    unregisterReceiver();
  }

  @Override
  public void onHostDestroy() {
    unregisterReceiver();
  }

  @ReactMethod
  public void isJackHeadphoneConnected(final Promise promise) {
    promise.resolve(isJackDeviceConnected());
  }
}
