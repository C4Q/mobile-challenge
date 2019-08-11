package org.pursuit.demo.mobile;

import java.util.HashMap;
import java.util.Map;
import org.pursuit.demo.mobile.ResourceManager;

public class FakeResourceManager implements ResourceManager {
  private Map<Integer, String> resourceIdsToStrings;

  public FakeResourceManager() {
    resourceIdsToStrings = new HashMap<>();
  }

  public void putString(int resourceId, String string) {
    resourceIdsToStrings.put(resourceId, string);
  }

  @Override public String getString(int resourceId) {
    return resourceIdsToStrings.get(resourceId);
  }
}
