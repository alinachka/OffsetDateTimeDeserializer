package ru.sber.medicine.med_patient_profile.config.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    private static final Pattern offsetDateTimePattern = Pattern.compile("^(?:[1-9]\\d{3}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1\\d|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[1-9]\\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00)-02-29)T(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d(?:\\.\\d{1,9})?(?:Z|[+-][01]\\d:[0-5]\\d)$");

    private final ObjectMapper offsetDateTimeMapper;

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var inputJsonText = p.getText();
        if (inputJsonText.isEmpty()) {
            throw new JsonParseException(p, p.getCurrentName() + " не может быть пустым");
        }
        if (!offsetDateTimePattern.matcher(inputJsonText).matches()) {
            throw new JsonParseException(p, p.getCurrentName() + ": невереный формат даты");
        }

        return offsetDateTimeMapper.readValue(p, OffsetDateTime.class);
    }
}