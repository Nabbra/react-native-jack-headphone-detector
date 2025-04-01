package nabbra.reactnative.audiojack;

public enum EventType {
    AUDIO_JACK_HEADPHONE_STATE_CHANGED("audioJackHeadphoneStateChanged");

    public final String name;

    EventType(String name) {
        this.name = name;
    }
}
