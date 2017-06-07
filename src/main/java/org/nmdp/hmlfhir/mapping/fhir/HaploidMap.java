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

import org.nmdp.hmlfhir.mapping.Distinct;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.Haploid;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.Identifier;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.lists.Haploids;
import org.nmdp.hmlfhirconvertermodels.dto.AlleleAssignment;
import org.nmdp.hmlfhirconvertermodels.dto.Hml;
import org.nmdp.hmlfhirconvertermodels.dto.Sample;
import org.nmdp.hmlfhirconvertermodels.dto.Typing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HaploidMap implements Converter<Hml, Haploids> {

    @Override
    public Haploids convert(MappingContext<Hml, Haploids> context) {
        if (context.getSource() == null) {
            return null;
        }

        Haploids haploids = new Haploids();
        List<Haploid> haploidList = new ArrayList<>();
        Hml hml = context.getSource();

        for (Sample sample : hml.getSamples()) {
            List<Typing> typings = sample.getTyping();

            for (Typing typing : typings) {
                List<AlleleAssignment> alleleAssignments = typing.getAlleleAssignment();
                for (AlleleAssignment alleleAssignment : alleleAssignments) {
                    List<org.nmdp.hmlfhirconvertermodels.dto.Haploid> nmdpHaploids = alleleAssignment.getHaploid();
                    for (org.nmdp.hmlfhirconvertermodels.dto.Haploid haploid : nmdpHaploids) {
                        Haploid fhirHaploid = new Haploid();

                        fhirHaploid.setLocus(haploid.getLocus());
                        fhirHaploid.setMethod(haploid.getMethod());
                        fhirHaploid.setHaploidType(haploid.getType());
                        fhirHaploid.setIdentifier(sample.getSampleId());
                        haploidList.add(fhirHaploid);
                    }
                }
            }
        }

        final List<Haploid> distinctHaploidList = Distinct.distinctByKeys(haploidList,
                Haploid::getLocus,
                Haploid::getMethod,
                Haploid::getHaploidType);

        haploids.setHaploids(distinctHaploidList.stream()
            .filter(Objects::nonNull)
            .filter(haploid -> haploid.hasValue())
            .collect(Collectors.toList()));

        return haploids;
    }
}
