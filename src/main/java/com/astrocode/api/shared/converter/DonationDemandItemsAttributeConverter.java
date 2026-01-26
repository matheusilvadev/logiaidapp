package com.astrocode.api.shared.converter;

import com.astrocode.api.shared.vo.DonationDemandItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Collections;
import java.util.List;

@Converter
public class DonationDemandItemsAttributeConverter implements AttributeConverter<List<DonationDemandItem>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<DonationDemandItem> attribute) {
        try {
            if (attribute == null || attribute.isEmpty()) {
                return "[]";
            }
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to convert items to JSON", e);
        }
    }

    @Override
    public List<DonationDemandItem> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isBlank()) {
                return Collections.emptyList();
            }
            return objectMapper.readValue(
                    dbData,
                    new TypeReference<List<DonationDemandItem>>() {}
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to convert JSON to items", e);
        }
    }
}
