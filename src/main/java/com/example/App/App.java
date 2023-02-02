package com.example.App;
import com.launchdarkly.sdk.*;
import com.launchdarkly.sdk.server.*;

public class App {

  // Set SDK_KEY to your LaunchDarkly SDK key.
  static final String SDK_KEY = "sdk-d4de20ae-3478-4967-94ce-1d4cc2e04116"; // ***----->>>>> PLEASE UPDATE to Match your Environment ID

  // Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate.
  static final String FEATURE_FLAG_KEY = "chatbox"; // ***----->>>>> PLEASE UPDATE to Match your Feature Flag Key that you created in your LD project/environment

  private static void showMessage(String s) {
    System.out.println("*** " + s);
    System.out.println();
  }

  public static void main(String... args) throws Exception {
    LDConfig config = new LDConfig.Builder()
      .events(Components.noEvents())
      .build();

    LDClient client = new LDClient(SDK_KEY, config);

    if (client.isInitialized()) {
      showMessage("SDK successfully initialized!");
    } else {
      showMessage("SDK failed to initialize");
      System.exit(1);
    }

    // Set up the evaluation context. This context should appear on your LaunchDarkly contexts
    // dashboard soon after you run the demo.
    LDContext context = LDContext.builder("example-user-key")  // ***----->>>>> PLEASE UPDATE with the your app user information you want to share with LD 
                            .name("Sandy")
                            .build();

    boolean flagValue = client.boolVariation(FEATURE_FLAG_KEY, context, false);

    showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + flagValue + " for this context");

    // Here we ensure that the SDK shuts down cleanly and has a chance to deliver analytics
    // events to LaunchDarkly before the program exits. If analytics events are not delivered,
    // the context attributes and flag usage statistics will not appear on your dashboard. In
    // a normal long-running application, the SDK would continue running and events would be
    // delivered automatically in the background.
    client.close();
  }
}