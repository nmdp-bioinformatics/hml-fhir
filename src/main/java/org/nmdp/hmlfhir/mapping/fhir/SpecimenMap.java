package org.nmdp.hmlfhir.mapping.fhir;

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

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.Collection;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.Identifier;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.Specimen;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.lists.Specimens;
import org.nmdp.hmlfhirconvertermodels.dto.Hml;
import org.nmdp.hmlfhirconvertermodels.dto.Sample;

import java.util.List;
import java.util.ArrayList;

public class SpecimenMap implements Converter<Hml, Specimens> {

    @Override
    public Specimens convert(MappingContext<Hml, Specimens> context) {
        if (context.getSource() == null) {
            return null;
        }

        Specimens specimens = new Specimens();
        List<Specimen> specimenList = new ArrayList<>();
        Hml hml = context.getSource();

        for (Sample sample : hml.getSamples()) {
            Specimen specimen = new Specimen();
            Identifier identifier = new Identifier();
            Collection collection = new Collection();

            if (sample.getCollectionMethods().size() > 0) {
                collection.setMethod(sample.getCollectionMethods().get(0).getName());
            }

            identifier.setValue(sample.getId());
            identifier.setSystem(sample.getCenterCode());
            specimen.setIdentifier(identifier);

            specimenList.add(specimen);
        }

        specimens.setSpecimens(specimenList);

        return specimens;
    }
}
