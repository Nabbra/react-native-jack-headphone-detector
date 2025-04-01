module.exports = {
  "transform": {
    '^.+\\.tsx?$': ['ts-jest', {
      tsconfig: 'tsconfig.jest.json',
    }],
  },
  "testRegex": "(/__tests__/.*|(\\.|/)(test|spec))\\.(jsx?|tsx?)$",
  "moduleFileExtensions": [
    "ts",
    "tsx",
    "js",
    "jsx",
    "json",
    "node"
  ],
  "modulePaths": [
    "<rootDir>"
  ],
  "modulePathIgnorePatterns": [
    "<rootDir>/node_modules/react-native/",
  ],
};