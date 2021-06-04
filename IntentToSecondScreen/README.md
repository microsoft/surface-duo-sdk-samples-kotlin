# Intent to second screen sample for Surface Duo

This sample demonstrates how to cause an activity to open on the second screen (as long as it's empty, otherwise the activity will launch over the current one).

In the main activity, choose an option to start: another activity from the current app or a URL in a browser window:

![Intent to second screen menu](Screenshots/intent-second-screen-menu-500.png)

If the launcher is still visible on the other screen, the new activity will appear there:

![Intent opened on second screen](Screenshots/intent-second-screen-500.png)

The sample uses these functions to set the intent flags required to start on the second screen if available:

```java
private fun startIntentSecondActivity() {
    val intent = Intent(this, SecondActivity::class.java)
    // Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT is required to launch a second activity
    // on a second display while still keeping the first activity on the first display
    // (not pausing/stopping it)
    intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

private fun startIntentBrowserApp(urlString: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
    intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}
```

## Related links

- [Launch intent to second screen docs](https://docs.microsoft.com/dual-screen/android/sample-code/launch-to-second-screen/)
- [Introduction to dual-screen devices](https://docs.microsoft.com/dual-screen/introduction)
- [Get the Surface Duo emulator](https://docs.microsoft.com/dual-screen/android/emulator/)

This sample is also available in [Java](https://github.com/microsoft/surface-duo-sdk-samples/tree/master/IntentToSecondScreen).
