package com.astrocode.api.shared.converter;

import com.astrocode.api.shared.vo.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Map;


@Converter
public class LocationAttributeConverter implements AttributeConverter<Location, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Location attribute) {
        if (attribute == null) {
            return null;
        }

        try {
            Map<String, String> json = Map.of(
                    "street", attribute.getStreet(),
                    "number", attribute.getNumber(),
                    "district", attribute.getDistrict(),
                    "city", attribute.getCity(),
                    "state", attribute.getState()
            );

            return mapper.writeValueAsString(json);
        } catch (Exception e) {
            throw new IllegalStateException("Error converting Location to JSON", e);
        }
    }

    @Override
    public Location convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        try {
            Map<String, String> json = mapper.readValue(
                    dbData,
                    new TypeReference<Map<String, String>>() {}
            );

            return Location.of(
                    json.get("street"),
                    json.get("number"),
                    json.get("district"),
                    json.get("city"),
                    json.get("state")
            );

        } catch (Exception e) {
            throw new IllegalStateException("Error converting JSON to Location", e);
        }
    }
}
