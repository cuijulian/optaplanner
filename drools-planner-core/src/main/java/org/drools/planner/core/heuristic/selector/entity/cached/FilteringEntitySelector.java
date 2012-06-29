/*
 * Copyright 2012 JBoss Inc
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

package org.drools.planner.core.heuristic.selector.entity.cached;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.drools.planner.core.heuristic.selector.cached.SelectionCacheType;
import org.drools.planner.core.heuristic.selector.cached.SelectionFilter;
import org.drools.planner.core.heuristic.selector.entity.EntitySelector;
import org.drools.planner.core.solution.Solution;
import org.drools.planner.core.solver.DefaultSolverScope;

public class FilteringEntitySelector extends CachingEntitySelector {

    protected final SelectionFilter entityFilter;

    protected List<Object> cachedEntityList = null;

    public FilteringEntitySelector(EntitySelector childEntitySelector, SelectionCacheType cacheType,
            SelectionFilter entityFilter) {
        super(childEntitySelector, cacheType);
        this.entityFilter = entityFilter;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    public void constructCache(DefaultSolverScope solverScope) {
        Solution workingSolution = solverScope.getWorkingSolution();
        long childSize = childEntitySelector.getSize();
        if (childSize > (long) Integer.MAX_VALUE) {
            throw new IllegalStateException("The moveSelector (" + this + ") has a childEntitySelector ("
                    + childEntitySelector + ") with childSize (" + childSize
                    + ") which is higher then Integer.MAX_VALUE.");
        }
        cachedEntityList = new ArrayList<Object>((int) childSize);
        for (Object entity : childEntitySelector) {
            if (entityFilter.accept(workingSolution, entity)) {
                cachedEntityList.add(entity);
            }
        }
    }

    public void disposeCache(DefaultSolverScope solverScope) {
        cachedEntityList = null;
    }

    public long getSize() {
        return cachedEntityList.size();
    }

    public Iterator<Object> iterator() {
        return cachedEntityList.iterator();
    }

    @Override
    public String toString() {
        return "Filtering(" + childEntitySelector + ")";
    }

}
