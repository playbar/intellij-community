// "Replace 'stream().toArray()' with 'toArray()'" "true"

import java.util.*;

class Test {
  public void testToArray(List<String> data) {
    Object[] array = data.toArray(new String[0]);
  }
}