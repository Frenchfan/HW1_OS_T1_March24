package com.sumkin.hw1_openschool.domain.entities.product;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sumkin.hw1_openschool.domain.entities.category.Category;

import java.io.IOException;

public class CategorySerializer extends JsonSerializer<Category> {
    @Override
    public void serialize(Category category, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(category.getName());
    }
}
