import { EventSubscription, NativeEventEmitter, NativeModules } from 'react-native';

export type AudioJackConnectionResult = boolean;

const { RNJackHeadphoneDetector } = NativeModules;
const eventEmitter = new NativeEventEmitter(RNJackHeadphoneDetector);

export type IJackHeadphoneDetector = {
  isJackHeadphoneConnected: () => Promise<AudioJackConnectionResult>;
  addListener: (eventName: string, callback: (result: AudioJackConnectionResult) => void) => EventSubscription;
}

const JackHeadphoneDetector: IJackHeadphoneDetector = {
  ...RNJackHeadphoneDetector,
  addListener(eventName, callback) {
    return eventEmitter.addListener(eventName, callback)
  }
}

export default JackHeadphoneDetector;