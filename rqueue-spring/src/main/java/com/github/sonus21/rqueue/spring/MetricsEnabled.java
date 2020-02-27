/*
 * Copyright 2020 Sonu Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.sonus21.rqueue.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Checks whether MeterRegistry bean is present it or not. if MeterRegistry bean is founds then it's
 * assumed that Metric feature is enables. It's essential to restrict the bean creation otherwise
 * can lead to error in bootstrap process.
 */
public class MetricsEnabled implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    try {
      Class.forName("io.micrometer.core.instrument.MeterRegistry");
      ConfigurableListableBeanFactory factory = context.getBeanFactory();
      if (factory != null) {
        factory.getBean(io.micrometer.core.instrument.MeterRegistry.class);
        return true;
      }
    } catch (ClassNotFoundException | BeansException e) {
      return false;
    }
    return false;
  }
}
