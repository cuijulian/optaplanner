/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.impl.constructionheuristic.decider.forager;

import org.optaplanner.core.config.constructionheuristic.decider.forager.ConstructionHeuristicForagerConfig;
import org.optaplanner.core.config.constructionheuristic.decider.forager.ConstructionHeuristicPickEarlyType;
import org.optaplanner.core.impl.heuristic.HeuristicConfigPolicy;

public class ConstructionHeuristicForagerFactory {

    public static ConstructionHeuristicForagerFactory create(ConstructionHeuristicForagerConfig foragerConfig) {
        return new ConstructionHeuristicForagerFactory(foragerConfig);
    }

    private final ConstructionHeuristicForagerConfig foragerConfig;

    public ConstructionHeuristicForagerFactory(ConstructionHeuristicForagerConfig foragerConfig) {
        this.foragerConfig = foragerConfig;
    }

    public ConstructionHeuristicForager buildForager(HeuristicConfigPolicy configPolicy) {
        ConstructionHeuristicPickEarlyType pickEarlyType_;
        if (foragerConfig.getPickEarlyType() == null) {
            pickEarlyType_ = configPolicy.getScoreDirectorFactory().getInitializingScoreTrend().isOnlyDown()
                    ? ConstructionHeuristicPickEarlyType.FIRST_NON_DETERIORATING_SCORE
                    : ConstructionHeuristicPickEarlyType.NEVER;
        } else {
            pickEarlyType_ = foragerConfig.getPickEarlyType();
        }
        return new DefaultConstructionHeuristicForager(pickEarlyType_);
    }
}
