import { View } from 'react-native'
import { useJackHeadphoneDetector } from '../hooks'
import React from 'react'

type Props = {
  children: React.ReactNode;
}

export const JackHeadphoneState = {
  Connected: ({ children }: Props) => {
    const isHeadphoneConnected = useJackHeadphoneDetector();

    if (isHeadphoneConnected) {
      return <View>{children}</View>;
    }

    return null;
  },
  Disconnected: ({ children }: Props) => {
    const isHeadphoneConnected = useJackHeadphoneDetector();

    if (!isHeadphoneConnected) {
      return <View>{children}</View>;
    }

    return null;
  }
}
