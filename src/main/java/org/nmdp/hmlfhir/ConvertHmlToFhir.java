package org.nmdp.hmlfhir;

/**
 * Created by Andrew S. Brown, Ph.D., <andrew@nmdp.org>, on 5/31/17.
 * <p>
 * hml-fhir
 * Copyright (c) 2012-2017 National Marrow Donor Program (NMDP)
 * <p>
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or (at
 * your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library;  if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.
 * <p>
 * > http://www.fsf.org/licensing/licenses/lgpl.html
 * > http://www.opensource.org/licenses/lgpl-license.php
 */

import com.google.gson.JsonObject;

import org.json.JSONObject;

import org.nmdp.hmlfhirconvertermodels.domain.hml.Hml;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.FhirMessage;

public interface ConvertHmlToFhir {
    FhirMessage convert(Hml hml) throws Exception;
    FhirMessage convert(org.nmdp.hmlfhirconvertermodels.dto.hml.Hml hml) throws Exception;
    FhirMessage convert(JsonObject hml) throws Exception;
    FhirMessage convert(JSONObject hml, String prefix) throws Exception;
    FhirMessage convert(String hml, String prefix) throws Exception;
    Hml convertToDo(org.nmdp.hmlfhirconvertermodels.dto.hml.Hml hml) throws Exception;
    Hml convertToDo(JsonObject hml) throws Exception;
    Hml convertToDo(JSONObject hml, String prefix) throws Exception;
    Hml convertToDo(String hml, String prefix) throws Exception;
    org.nmdp.hmlfhirconvertermodels.dto.hml.Hml convertToDto(Hml hml) throws Exception;
    org.nmdp.hmlfhirconvertermodels.dto.hml.Hml convertToDto(JsonObject hml) throws Exception;
    org.nmdp.hmlfhirconvertermodels.dto.hml.Hml convertToDto(JSONObject hml, String prefix) throws Exception;
    org.nmdp.hmlfhirconvertermodels.dto.hml.Hml convertToDto(String hml, String prefix) throws Exception;
}
