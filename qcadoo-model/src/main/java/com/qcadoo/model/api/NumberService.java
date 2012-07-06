/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.6
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.model.api;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Service to provide utilities for numbers
 * 
 * @version 1.1.3
 */
public interface NumberService {

    /**
     * Provide global MathContext.
     * 
     * @return {@link MathContext}
     */
    MathContext getMathContext();

    /**
     * Formats an object with DecimalFormat to produce a String.
     * 
     * @param obj
     * 
     * @return Formatted string.
     */
    String format(Object obj);

    /**
     * Set scale 5 to {@link BigDecimal} with RoundingMode HALF_EVEN.
     * 
     * @param decimal
     * 
     * @return BigDecimal with scale 5.
     */
    BigDecimal setScale(BigDecimal decimal);

}
