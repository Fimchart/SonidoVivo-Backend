package cl.duoc.msProductos.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPARADOR = "\\|\\|";
    private static final String UNION = "||";

    @Override
    public String convertToDatabaseColumn(List<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return "";
        }
        return String.join(UNION, lista);
    }

    @Override
    public List<String> convertToEntityAttribute(String dato) {
        if (dato == null || dato.isBlank()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(dato.split(SEPARADOR)));
    }
}