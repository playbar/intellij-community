/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.util.indexing.containers;

import com.intellij.util.containers.EmptyIntHashSet;
import com.intellij.util.indexing.ValueContainer;
import gnu.trove.TIntHashSet;
import gnu.trove.TIntIterator;
import org.jetbrains.annotations.NotNull;

public class TroveSetIntIterator implements ValueContainer.IntIterator {
  @NotNull public static final TroveSetIntIterator EMPTY = new TroveSetIntIterator(EmptyIntHashSet.INSTANCE);

  @NotNull private final TIntHashSet mySet;
  @NotNull private final TIntIterator mySetIterator;
  @NotNull private final int mySize;

  public TroveSetIntIterator(@NotNull TIntHashSet set) {
    mySet = set;
    mySetIterator = set.iterator();
    mySize = set.size();
  }

  @Override
  public boolean hasNext() {
    return mySetIterator.hasNext();
  }

  @Override
  public int next() {
    return mySetIterator.next();
  }

  @Override
  public int size() {
    return mySize;
  }

  @Override
  public boolean hasAscendingOrder() {
    return false;
  }

  @Override
  public ValueContainer.IntIterator createCopyInInitialState() {
    return new TroveSetIntIterator(mySet);
  }
}
