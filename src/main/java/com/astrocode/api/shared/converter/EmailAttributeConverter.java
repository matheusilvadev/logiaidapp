package com.astrocode.api.shared.converter;

import com.astrocode.api.shared.vo.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {


    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Email.of(dbData);
    }
}
