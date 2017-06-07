package org.nmdp.hmlfhir.mapping.object;

/**
 * Created by Andrew S. Brown, Ph.D., <andrew@nmdp.org>, on 6/7/17.
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

import org.nmdp.hmlfhirconvertermodels.HmlMessage;
import org.nmdp.hmlfhirconvertermodels.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HmlMessageToHml {

    public Hml toDto(HmlMessage hmlMessage) {
        Hml hml = new Hml();
        hml = hmlMessage.getPatientHml();
        List<Sample> observationSamples = hmlMessage.getObservationSamples().getSamples();

        for (Sample observationSample : observationSamples) {
            String sampleId = observationSample.getSampleId();
            for (Typing observationTyping : getSampleTypings(observationSamples, sampleId)) {
                observationTyping.setAlleleAssignment(getTypingAlleleAssignments(
                        hmlMessage.getHaploidSamples().getSamples(), hmlMessage.getGlStringSamples().getSamples(),
                        hmlMessage.getAlleleDatabaseSamples().getSamples(), hmlMessage.getAlleleNameSamples().getSamples(),
                        hmlMessage.getGenotypingResultsHaploidSamples().getSamples(), sampleId));
                observationTyping.setTypingMethod(getTypingTypingMethod(
                        hmlMessage.getSsps().getSsps(), hmlMessage.getSsos().getSsos(),
                        hmlMessage.getSbtNgss().getSbtNgss(), hmlMessage.getGenotypingResultsMethodSamples().getSamples(),
                        sampleId));
                observationTyping.setConsensusSequence(getTypingConsensusSequence(
                        hmlMessage.getGeneticsPhaseSetSamples().getSamples(), hmlMessage.getSequenceSamples().getSamples(),
                        sampleId);
            }
        }

    }

    private List<Typing> getSampleTypings(List<Sample> samples, String sampleId) {
        return samples.stream()
                .filter(Objects::nonNull)
                .filter(sample -> sample.getSampleId().equals(sampleId))
                .collect(Collectors.toList()).stream()
                        .filter(Objects::nonNull)
                        .map(sample -> sample.getTyping())
                        .flatMap(typing -> typing.stream())
                        .collect(Collectors.toList());
    }

    private List<AlleleAssignment> getTypingAlleleAssignments(List<Sample> haploidSamples, List<Sample> glStringSamples,
        List<Sample> alleleDatabaseSamples, List<Sample> alleleNameSamples, List<Sample> genotypingResultsHaploidSamples,
        String sampleId) {
        List<AlleleAssignment> alleleAssignments = new ArrayList<>();
        AlleleAssignment alleleAssignment = new AlleleAssignment();
        List<Sample> filteredHaploidSamples = haploidSamples.stream()
                .filter(Objects::nonNull)
                .filter(sample -> sample.getSampleId().equals(sampleId))
                .collect(Collectors.toList());
        List<Sample> filteredGlStringSamples = glStringSamples.stream()
                .filter(Objects::nonNull)
                .filter(sample -> sample.getSampleId().equals(sampleId))
                .collect(Collectors.toList());
        List<Sample> filteredAlleleDatabaseSamples = alleleDatabaseSamples.stream()
                .filter(Objects::nonNull)
                .filter(sample -> sample.getSampleId().equals(sampleId))
                .collect(Collectors.toList());
        List<Sample> filteredAlleleNameSamples = alleleNameSamples.stream()
                .filter(Objects::nonNull)
                .filter(sample -> sample.getSampleId().equals(sampleId))
                .collect(Collectors.toList());
        List<Sample> filteredGenotypingResultsHaploidSamples = genotypingResultsHaploidSamples.stream()
                .filter(Objects::nonNull)
                .filter(sample -> sample.getSampleId().equals(sampleId))
                .collect(Collectors.toList());

        alleleAssignment.setHaploid(getAlleleAssignmentHaploids(filteredHaploidSamples));
        alleleAssignment.setGlString(getAlleleAssignmentGlStrings(filteredGlStringSamples));

    }

    private List<Haploid> getAlleleAssignmentHaploids(List<Sample> samples) {
        return samples.stream()
                .filter(Objects::nonNull)
                .map(sample -> sample.getTyping())
                .flatMap(typings -> typings.stream())
                .collect(Collectors.toList()).stream()
                    .filter(Objects::nonNull)
                    .map(typing -> typing.getAlleleAssignment())
                    .flatMap(alleleAssignments -> alleleAssignments.stream())
                    .collect(Collectors.toList()).stream()
                        .filter(Objects::nonNull)
                        .map(alleleAssignment -> alleleAssignment.getHaploid())
                        .flatMap(haploids -> haploids.stream())
                        .collect(Collectors.toList());
    }

    private List<GlString> getAlleleAssignmentGlStrings(List<Sample> samples) {
        return samples.stream()
                .filter(Objects::nonNull)
                .map(sample -> sample.getTyping())
                .flatMap(typings -> typings.stream())
                .collect(Collectors.toList()).stream()
                    .filter(Objects::nonNull)
                    .map(typing -> typing.getAlleleAssignment())
                    .flatMap(alleleAssignments -> alleleAssignments.stream())
                    .collect(Collectors.toList()).stream()
                        .filter(Objects::nonNull)
                        .map(alleleAssignment -> alleleAssignment.getGlString())
                        .collect(Collectors.toList());
    }

    private TypingMethod getTypingTypingMethod(List<Ssp> ssps, List<Sso> ssos, List<SbtNgs> sbtNgss,
        List<Sample> gentoypingResultsMethodSamples, String sampleId) {
        sbtNgss = getTypingTypingMethodSbtNgss(sbtNgss, gentoypingResultsMethodSamples, sampleId);
    }

    private List<SbtNgs> getTypingTypingMethodSbtNgss(List<SbtNgs> sbtNgss, List<Sample> gentoypingResultsMethodSamples, String sampleId) {


        return sbtNgss;
    }

    private ConsensusSequence getTypingConsensusSequence(List<Sample> geneticsPhaseSetSamples, List<Sample> sequenceSamples, String sampleId) {

    }
}
