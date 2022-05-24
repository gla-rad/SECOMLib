/*
 * Copyright (c) 2022 GLA Research and Development Directorate
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

package org.grad.secom.models;

import org.grad.secom.models.enums.AreaNameEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Area Name Enum List Class.
 *
 * A comma seperated list of AreaNameEnum codes.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class AreaNameEnumList {

    // Class Variables
    private List<AreaNameEnum> sortParamList;

    /**
     * Empty Constructor.
     */
    public AreaNameEnumList() {
        this.sortParamList = Collections.emptyList();
    }

    /**
     * Constructor of the AreaNameEnumList based on a comma separated list.
     *
     * @param commaSeparatedString the comma separated list
     */
    public AreaNameEnumList(String commaSeparatedString) {
        sortParamList = Arrays.stream(commaSeparatedString.split(","))
                .map(p -> AreaNameEnum.valueOf(p))
                .collect(Collectors.toList());
    }

    /**
     * Get individual AreaNameEnum entries.
     *
     * @return the individual AreaNameEnum entries
     */
    public List<AreaNameEnum> getAreaNameEnums() {
        return this.sortParamList;
    }
}
