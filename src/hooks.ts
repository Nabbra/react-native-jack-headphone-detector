import { useEffect, useState } from 'react'
import JackHeadphoneDetector, { AudioJackConnectionResult } from './module'

export const useJackHeadphoneDetector = () => {
  const [result, setResult] = useState<AudioJackConnectionResult>(false);

  useEffect(() => {
    JackHeadphoneDetector.isJackHeadphoneConnected().then(setResult);
    const subscription = JackHeadphoneDetector.addListener('audioJackHeadphoneStateChanged', setResult);
    return () => {
      subscription.remove();
    }
  }, []);

  return result;
}