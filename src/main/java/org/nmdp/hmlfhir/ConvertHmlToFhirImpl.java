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

import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import org.modelmapper.ModelMapper;

import org.nmdp.hmlfhir.deserialization.Deserializer;
import org.nmdp.hmlfhir.mapping.fhir.*;
import org.nmdp.hmlfhirconvertermodels.domain.Hml;
import org.nmdp.hmlfhirconvertermodels.domain.base.SwaggerConverter;
import org.nmdp.hmlfhirconvertermodels.domain.fhir.*;

import org.apache.log4j.Logger;

import org.nmdp.hmlfhirconvertermodels.domain.fhir.lists.*;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.StringReader;
import java.util.Iterator;

public class ConvertHmlToFhirImpl implements ConvertHmlToFhir {

    private static final Logger LOG = Logger.getLogger(ConvertHmlToFhir.class);
    private Deserializer deserializer;

    public ConvertHmlToFhirImpl(Deserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public FhirMessage convert(Hml hml) throws Exception {
        try {
            return toFhir(hml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public FhirMessage convert(org.nmdp.hmlfhirconvertermodels.dto.Hml hml) throws Exception {
        try {
            return toFhir(hml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public FhirMessage convert(JsonObject hml) throws Exception {
        try {
            org.nmdp.hmlfhirconvertermodels.dto.Hml javaHml = convertToDto(hml);
            return toFhir(javaHml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public FhirMessage convert(JSONObject hml, String prefix) throws Exception {
        try {
            org.nmdp.hmlfhirconvertermodels.dto.Hml javaHml = convertToDto(hml, prefix);
            return toFhir(javaHml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public FhirMessage convert(String hml, String prefix) throws Exception {
        try {
            org.nmdp.hmlfhirconvertermodels.dto.Hml javaHml = convertToDto(hml, prefix);
            return toFhir(javaHml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public Hml convertToDo(org.nmdp.hmlfhirconvertermodels.dto.Hml hml) throws Exception {
        try {
            return toDomain(hml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public Hml convertToDo(JsonObject hml) throws Exception {
        try {
            Gson gson = getGsonConverter();
            org.nmdp.hmlfhirconvertermodels.dto.Hml javaHml =
                    gson.fromJson(hml, org.nmdp.hmlfhirconvertermodels.dto.Hml.class);

            return toDomain(javaHml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public Hml convertToDo(JSONObject hml, String prefix) throws Exception {
        try {
            JsonParser parser = new JsonParser();
            Gson gson = getGsonConverter();

            if (prefix != null) {
                hml = mutatePropertyNames(hml, prefix);
            }

            Object obj = parser.parse(hml.toString());
            JsonObject json = (JsonObject) obj;
            org.nmdp.hmlfhirconvertermodels.dto.Hml javaHml =
                    gson.fromJson(json, org.nmdp.hmlfhirconvertermodels.dto.Hml.class);

            return toDomain(javaHml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public Hml convertToDo(String hml, String prefix) throws Exception {
        try {
            JSONObject jsonObj = convertXmlStringToJson(hml);
            JsonParser parser = new JsonParser();
            Gson gson = getGsonConverter();

            if (prefix != null) {
                jsonObj = mutatePropertyNames(jsonObj, prefix);
            }

            Object obj = parser.parse(jsonObj.toString());
            JsonObject json = (JsonObject) obj;
            org.nmdp.hmlfhirconvertermodels.dto.Hml javaHml =
                    gson.fromJson(json, org.nmdp.hmlfhirconvertermodels.dto.Hml.class);

            return toDomain(javaHml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public org.nmdp.hmlfhirconvertermodels.dto.Hml convertToDto(Hml hml) throws Exception {
        try {
            return toDto(hml);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public org.nmdp.hmlfhirconvertermodels.dto.Hml convertToDto(JsonObject hml) throws Exception {
        try {
            Gson gson = getGsonConverter();
            return gson.fromJson(hml, org.nmdp.hmlfhirconvertermodels.dto.Hml.class);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public org.nmdp.hmlfhirconvertermodels.dto.Hml convertToDto(JSONObject hml, String prefix) throws Exception {
        try {
            JsonParser parser = new JsonParser();
            Gson gson = getGsonConverter();

            if (prefix != null) {
                hml = mutatePropertyNames(hml, prefix);
            }

            Object obj = parser.parse(hml.toString());
            JsonObject json = (JsonObject) obj;

            return gson.fromJson(json, org.nmdp.hmlfhirconvertermodels.dto.Hml.class);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    @Override
    public org.nmdp.hmlfhirconvertermodels.dto.Hml convertToDto(String hml, String prefix) throws Exception {
        try {
            JSONObject jsonObj = convertXmlStringToJson(hml);
            JsonParser parser = new JsonParser();
            Gson gson = getGsonConverter();

            if (prefix != null) {
                jsonObj = mutatePropertyNames(jsonObj, prefix);
            }

            Object obj = parser.parse(jsonObj.toString());
            JsonObject json = (JsonObject) obj;

            return gson.fromJson(json, org.nmdp.hmlfhirconvertermodels.dto.Hml.class);
        } catch (Exception ex) {
            LOG.error(ex);
            throw (Exception) ex;
        }
    }

    private FhirMessage toFhir(org.nmdp.hmlfhirconvertermodels.dto.Hml hml) {
        ModelMapper mapper = createMapper();
        FhirMessage message = new FhirMessage();

        message.setAlleleDatabases(mapper.map(hml, AlleleDatabases.class));
        message.setAlleleNames(mapper.map(hml, AlleleNames.class));
        message.setDiagnosticReport(mapper.map(hml, DiagnosticReport.class));
        message.setGeneticsPhaseSets(mapper.map(hml, GeneticsPhaseSets.class));
        message.setGenotypingResultsHaploids(mapper.map(hml, GenotypingResultsHaploids.class));
        message.setGenotypingResultsMethods(mapper.map(hml, GenotypingResultsMethods.class));
        message.setGlstrings(mapper.map(hml, Glstrings.class));
        message.setHaploids(mapper.map(hml, Haploids.class));
        message.setObservations(mapper.map(hml, Observations.class));
        message.setOrganization(mapper.map(hml, Organization.class));
        message.setPatient(mapper.map(hml, Patient.class));
        message.setSbtNgss(mapper.map(hml, SbtNgss.class));
        message.setSequences(mapper.map(hml, Sequences.class));
        message.setSpecimens(mapper.map(hml, Specimens.class));
        message.setSsos(mapper.map(hml, Ssos.class));
        message.setSsps(mapper.map(hml, Ssps.class));

        return message;
    }

    private ModelMapper createMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addConverter(new AlleleDatabaseMap());
        mapper.addConverter(new AlleleNameMap());
        mapper.addConverter(new DiagnosticReportMap());
        mapper.addConverter(new GeneticsPhaseSetMap());
        mapper.addConverter(new GenotypingResultsHaploidMap());
        mapper.addConverter(new GenotypingResultsMethodMap());
        mapper.addConverter(new GlStringMap());
        mapper.addConverter(new HaploidMap());
        mapper.addConverter(new ObservationMap());
        mapper.addConverter(new OrganizationMap());
        mapper.addConverter(new PatientMap());
        mapper.addConverter(new SbtNgsMap());
        mapper.addConverter(new SequenceMap());
        mapper.addConverter(new SpecimenMap());
        mapper.addConverter(new SsoMap());
        mapper.addConverter(new SspMap());

        return  mapper;
    }

    private FhirMessage toFhir(Hml hml) {
        org.nmdp.hmlfhirconvertermodels.dto.Hml dtoHml = toDto(hml);
        return toFhir(dtoHml);
    }

    private Hml toDomain(org.nmdp.hmlfhirconvertermodels.dto.Hml hml) {
        SwaggerConverter<Hml, org.nmdp.hmlfhirconvertermodels.dto.Hml> converter =
                new SwaggerConverter<>(Hml.class, org.nmdp.hmlfhirconvertermodels.dto.Hml.class);
        return converter.convertFromSwagger(hml);
    }

    private org.nmdp.hmlfhirconvertermodels.dto.Hml toDto(Hml hml) {
        return hml.toDto(hml);
    }

    private Gson getGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(org.nmdp.hmlfhirconvertermodels.dto.Hml.class, deserializer);
        return gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create();
    }

    private JSONObject convertXmlStringToJson(String xml) throws Exception {
        try {
            return XML.toJSONObject(xml);
        } catch (Exception ex) {
            LOG.error("Error parsing Xml to Json.", ex);
            throw ex;
        }
    }

    private JSONObject mutatePropertyNames(JSONObject json, String prefix) {
        JSONObject mutatedJson = new JSONObject();
        Iterator<String> jsonIterator = json.keys();

        while (jsonIterator.hasNext()) {
            String key = jsonIterator.next();
            Object property = json.get(key);

            if (property instanceof JSONObject) {
                JSONObject mutatedProperty = mutatePropertyNames((JSONObject) property, prefix);
                mutatedJson.put(key.replace(prefix, ""), mutatedProperty);
                continue;
            } else if (property instanceof JSONArray) {
                JSONArray arrayProperty = (JSONArray)property;
                JSONArray mutatedJsonArray = new JSONArray();

                for (int i = 0; i < arrayProperty.length(); i++) {
                    JSONObject obj = arrayProperty.getJSONObject(i);
                    JSONObject mutatedObj = mutatePropertyNames(obj, prefix);

                    mutatedJsonArray.put(mutatedObj);
                }

                mutatedJson.put(key.replace(prefix, ""), mutatedJsonArray);
                continue;
            }

            mutatedJson.put(key.replace(prefix, ""), property);
        }

        return mutatedJson;
    }

    private Boolean isValidXml(String xml) {
        Boolean valid = false;

        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            xmlReader.parse(new InputSource(new StringReader(xml)));
            valid = true;
        } catch (Exception ex) {
            LOG.error("General exception validating XML.", ex);
        } finally {
            return valid;
        }
    }
}
