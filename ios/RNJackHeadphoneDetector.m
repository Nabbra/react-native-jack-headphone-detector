#import "RNJackHeadphoneDetector.h"
#import <AVFoundation/AVFoundation.h>

#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>
#elif __has_include("RCTBridge.h")
#import "RCTBridge.h"
#import "RCTEventDispatcher.h"
#else
#import "React/RCTBridge.h"
#import "React/RCTEventDispatcher.h"
#endif

@implementation RNJackHeadphoneDetectorModule {
  BOOL hasListeners;
}

RCT_EXPORT_MODULE(RNJackHeadphoneDetectorModule);

- (NSArray<NSString *> *)supportedEvents {
  return @[@"AUDIO_JACK_HEADPHONE_STATE_CHANGED"];
}

- (instancetype)init {
  if (self = [super init]) {
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(audioRouteChanged:)
                                                 name:AVAudioSessionRouteChangeNotification
                                               object:nil];
  }
  return self;
}

- (void)dealloc {
  [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)audioRouteChanged:(NSNotification *)notification {
  BOOL isConnected = [self isJackHeadphoneConnected];

  if (hasListeners) {
    [self sendEventWithName:@"AUDIO_JACK_HEADPHONE_STATE_CHANGED" body:@(isConnected)];
  }
}

-(void)startObserving {
  hasListeners = YES;
}
-(void)stopObserving {
  hasListeners = NO;
}

RCT_EXPORT_METHOD(isJackHeadphoneConnected:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  BOOL connected = [self isJackHeadphoneConnected];
  resolve(@(connected));
}

- (BOOL)isJackHeadphoneConnected {
  AVAudioSessionRouteDescription *route = [[AVAudioSession sharedInstance] currentRoute];
  for (AVAudioSessionPortDescription *desc in route.outputs) {
    if ([desc.portType isEqualToString:AVAudioSessionPortHeadphones]) {
      return YES;
    }
  }
  return NO;
}

@end
