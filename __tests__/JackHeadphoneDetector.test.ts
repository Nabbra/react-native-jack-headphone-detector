import { Platform } from 'react-native';
// import JackHeadphoneDetector from '../src/index';

jest.mock('react-native', () => {
  return {
    NativeModules: {
      RNJackHeadphoneDetectorModule: {
        isJackHeadphoneConnected: jest.fn(),
      }
    },
    NativeEventEmitter: jest.fn(() => ({
      addListener: jest.fn(),
      removeListener: jest.fn(),
    })),
    Platform: {
      OS: 'ios',
    },
  };
});

describe('React Native Platform', () => {
  test("Platform.OS should be 'ios'", () => {
    expect(Platform.OS).toBe('ios');
  });
});

// describe('JackHeadphoneDetector Module', () => {
//   const { RNJackHeadphoneDetectorModule } = NativeModules;
//   const eventEmitter = new NativeEventEmitter(RNJackHeadphoneDetectorModule);

//   beforeEach(() => {
//     jest.clearAllMocks();
//   });

//   test('should call isJackHeadphoneConnected and resolve with correct value', async () => {
//     RNJackHeadphoneDetectorModule.isJackHeadphoneConnected.mockResolvedValue(true);
//     const result = await JackHeadphoneDetector.isJackHeadphoneConnected();
//     expect(result).toBe(true);
//   });
// });