# React Native Jack Headphone Detector

A React Native module for detecting wired headphone (audio jack) connection state on (Android or iOS) devices.

## Installation

Install the package using NPM, YARN or PNPM:

```bash
npm install @nabbra/rn-jack-headphone-detector
# or
yarn add @nabbra/rn-jack-headphone-detector
# or
pnpm add @nabbra/rn-jack-headphone-detector
```

Ensure that your React Native project is correctly linked the module.

```bash
npx react-native link @nabbra/rn-jack-headphone-detector
```

## Usage

### Checking Connection State

You can check whether a wired headset is currently connected using:

```javascript
import JackHeadphoneDetector from '@nabbra/rn-jack-headphone-detector';

JackHeadphoneDetector.isJackHeadphoneConnected()
  .then(isConnected => {
    console.log('Headphones connected:', isConnected);
  });
```

### Listening for Connection Changes

You can subscribe to headphone connection state changes using event listeners:

```javascript
import { useJackHeadphoneDetector } from '@nabbra/rn-jack-headphone-detector';

const isConnected = useJackHeadphoneDetector();
```

### Conditional Rendering based on Connection State

You can use the provided React components to conditionally render UI elements based on headphone connection status:

```javascript
import { JackHeadphoneState } from '@nabbra/rn-jack-headphone-detector';
import { Text, View } from 'react-native';

const MyComponent = () => {
  return (
    <View>
      <JackHeadphoneState.Connected>
        <Text>🎧 Headphones are connected</Text>
      </JackHeadphoneState.Connected>

      <JackHeadphoneState.Disconnected>
        <Text>🔊 No headphones connected</Text>
      </JackHeadphoneState.Disconnected>
    </View>
  );
};
```

## API Reference

### Methods

`isJackHeadphoneConnected(): Promise<boolean>`

Checks if wired headphones are currently connected.

**Returns**: `Promise<boolean>` - `true` if connected, `false` otherwise.

### Events

`audioJackHeadphoneStateChanged`

Triggered whenever the wired headphone connection state changes.

```javascript
JackHeadphoneDetector.addListener('audioJackHeadphoneStateChanged', (isConnected: boolean) => {
  console.log('Headphone state changed:', isConnected);
});
```

## Notes

- This package currently **only supports Android**.
- The module uses Android's AudioManager API to detect headphone connections.
- Inspiration of [This Module](https://github.com/Tintef/react-native-headphone-detection)

## Contributing

Pull requests are welcome! If you encounter any issues, feel free to open an issue in the repository.

## License

This project is licensed under the MIT License.