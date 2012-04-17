/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.mrunit.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mrunit.Serialization;
import org.apache.hadoop.mrunit.types.Pair;

/**
 * OutputCollector to use in the test framework for Mapper and Reducer classes.
 * Accepts a set of output (k, v) pairs and returns them to the framework for
 * validation.
 */
public class MockOutputCollector<K, V> implements OutputCollector<K, V> {

  private final ArrayList<Pair<K, V>> collectedOutputs;
  private final Serialization serialization;

  public MockOutputCollector(final Configuration conf) {
    collectedOutputs = new ArrayList<Pair<K, V>>();
    serialization = new Serialization(conf);
  }

  /**
   * Accepts another (key, value) pair as an output of this mapper/reducer.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void collect(final K key, final V value) throws IOException {
    collectedOutputs.add(new Pair<K, V>((K) serialization.copy(key),
        (V) serialization.copy(value)));
  }

  /**
   * @return The outputs generated by the mapper/reducer being tested
   */
  public List<Pair<K, V>> getOutputs() {
    return collectedOutputs;
  }
}
